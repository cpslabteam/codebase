package codebase.streams;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import shared.properties.api.IProperty;
import shared.properties.base.DefaultPropertyValue;
import shared.properties.base.Property;
import shared.properties.base.datatypes.NumberDataType;

/**
 * An output stream decorator that times out (instead of blocking) on write operations.
 * <p>
 * This stream decorator is especially useful to add timeout behavior to an existing
 * stream. However, Timeout Output Stream only guarantees that the write operation does
 * not lock indefinitely. It does not guarantee that the write was successful.
 * <p>
 * <b>Note:</b> The Timeout Output Stream assumes that the write() operation on decorated
 * output stream can block. Otherwise, it is unknown if the decorated output stream does
 * not block on writing, there's no way the write operation can timeout.
 */
public class TimeoutOutputStream extends
        FilterOutputStream {

    /**
     * Port default timeout in milliseconds for open, read and write operations.
     */
    private static final int DEFAULT_TIMEOUT_MILLIS = 2000;

    /**
     * The timeout property.
     */
    private final IProperty timeoutProperty;

    /**
     * The timeout unit specified for {@link #driverTimeoutUnit}.
     */
    private final TimeUnit streamTimeoutUnit;

    /**
     * Up when the data can be written to the decorated stream.
     */
    private final Semaphore dataToDecorated = new Semaphore(1);

    /**
     * Up when the a new message can be written by the client.
     */
    private final Semaphore dataFromClient = new Semaphore(0);

    /**
     * Name of the message arrival thread.
     */
    private static final String DATA_WRITER_THREAD_NAME = "TimeoutOuputStream data writer thread";

    /**
     * Flag that indicates if the stream was successfully closed.
     */
    private boolean isClosed;

    /**
     * The last message read from the buffer.
     */
    private byte[] message;

    /**
     * Last exception that occurred, if any. Can be <code>null</code>.
     */
    private IOException ioexception;

    /**
     * Reads data form the input stream and puts it in the queue.
     */
    private final class DataWriter extends
            Thread {

        public DataWriter() {
            super(DATA_WRITER_THREAD_NAME);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    dataFromClient.acquire();
                    try {
                        out.write(message);
                        ioexception = null;
                        dataToDecorated.release();
                    } catch (IOException e) {
                        ioexception = e;
                    }
                }
            } catch (InterruptedException ex) {
                /*
                 * If the thread is stopped its not a problem: we just ignore it since we
                 * are not locking any resources. We are being stopped for shutdown.
                 */
            }
        }
    };

    private DataWriter dataWritter;

    /**
     * Instantiates a new timeout stream decorator with default timeout.
     * 
     * @param out the input stream to be decorated from where the reading will take place.
     */
    public TimeoutOutputStream(final OutputStream out) {
        this(out, DEFAULT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * Instantiates a new timeout stream decorator with the given timeout.
     * 
     * @param out the input stream to be decorated from where the reading will take place.
     * @param timeout the driver timeout parameter for open, read and write operations
     * @param timeoutUnit the units of the timeout parameter
     */
    public TimeoutOutputStream(final OutputStream out, final int timeout, final TimeUnit timeoutUnit) {
        super(out);
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be positive.");
        }
        timeoutProperty =
            new Property.PropertyBuilder("Timeout", NumberDataType.getInstance()).setCloneable(true).setName("Stream Timeout")
                    .setReadOnly(false).setTransient(true).setPropertyValue(new DefaultPropertyValue(timeout))
                    .setDescription("Timeout of this OutputStream in millisenconds.").build();
        streamTimeoutUnit = timeoutUnit;
    }

    /**
     * Instantiates a new timeout stream decorator with the given timeout.
     * 
     * @param in the output stream to be decorated from where the reading will take place.
     * @param timeout the driver timeout parameter for open, read and write operations in
     *            millis. Must be a {@link NumberPropertyType}. This stream will be
     *            attached to this property and each update in the property's value will
     *            instantly affect the stream
     * @param timeoutUnit the units of the timeout parameter
     */
    public TimeoutOutputStream(final OutputStream out, final IProperty timeoutProperty, TimeUnit timeoutUnit) {
        super(out);
        if (!timeoutProperty.getPropertyType().equals(NumberDataType.getInstance())) {
            throw new IllegalArgumentException("The property must be a " + NumberDataType.class.getSimpleName() + ".");
        }
        if ((Integer) timeoutProperty.getValue() <= 0) {
            throw new IllegalArgumentException("Timeout must be positive.");
        }
        this.timeoutProperty = timeoutProperty;
        streamTimeoutUnit = timeoutUnit;
    }

    /**
     * Open the stream.
     * <p>
     * Open can only be called once.
     */
    public void open() {
        if (dataWritter != null) {
            throw new IllegalStateException("TimeoutStream already open");
        }
        isClosed = false;

        dataWritter = new DataWriter();
        dataWritter.setDaemon(true);
        dataWritter.start();
    }

    /**
     * Checks if the stream is closed.
     * <p>
     * A TimeoutOuputStream is considered successfully closed if the last call to
     * <tt>close()</tt> terminated the writer thread and closed the underlying stream.
     * 
     * @return <code>true</code> the last call to close was successful; returns
     *         <code>false</code> otherwise.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Closes the timeout output stream and the decorated output stream.
     * 
     * @see java.io.FilterOutputStream#close()
     * @throws IOException if the decorated if the decorated stream throws one
     */
    @Override
    public synchronized void close() throws IOException {
        isClosed = false;

        if (dataWritter != null) {
            dataWritter.interrupt();
            try {
                dataWritter.join((Integer) this.timeoutProperty.getValue());
                if (!dataWritter.isAlive()) {
                    isClosed = true;
                    super.close();
                }
            } catch (InterruptedException e) {
                isClosed = false;
            }
        }
    }


    /**
     * Writes a byte into the decorated stream.
     * <p>
     * Note: Does not check if the decorated stream is closed. This behavior is consistent
     * with the behavior of {@link FilterOutputStream}.
     * 
     * @param b the byte to be written
     * @throws IOException if, after the period specified in the constructor the message
     *             has not been sent.
     */
    @Override
    public void write(int b) throws IOException {
        this.write(new byte[] { (byte) b });
    }

    /**
     * Writes a segment of a message buffer into the decorated stream.
     * <p>
     * Note: Does not check if the decorated stream is closed. This behavior is consistent
     * with the behavior of {@link FilterOutputStream}.
     * 
     * @param b the byte[] message to be written
     * @param off the offset to write on. Must be smaller that b.lenght
     * @param len the length the number of bytes to write in the buffer
     * @throws IOException if, after the period specified in the constructor the message
     *             has not been sent.
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        final byte[] segment = new byte[len];
        System.arraycopy(b, off, segment, 0, len);
        this.write(segment);
    }

    /**
     * Writes a message into the decorated stream.
     * <p>
     * Note: Does not check if the decorated stream is closed. This behavior is consistent
     * with the behavior of {@link FilterOutputStream}.
     * 
     * @param b the byte[] message to be written
     * @throws IOException if, after the period specified in the constructor the message
     *             has not been sent.
     */
    @Override
    public void write(byte[] b) throws IOException {
        if (b == null)
            return;

        try {
            /*
             * Checks if it the last message was sent. This way, there is no need for the
             * write() method to be synchronized.
             */
            if (!dataToDecorated.tryAcquire((Integer) this.timeoutProperty.getValue(), this.streamTimeoutUnit)) {
                throw new TimeoutException("Could not write to decorated output stream after "
                        + (Integer) this.timeoutProperty.getValue() + streamTimeoutUnit.toString());
            }

            /*
             * Put the message in the buffer and wait for the DataWritter thread to
             * consume it
             */
            message = b.clone();

            dataFromClient.release();
            Thread.yield();

            /*
             * Check that the writer thread has consumed the message and is not blocked
             * inside 'in.read()'
             */
            if (dataToDecorated.tryAcquire((Integer) this.timeoutProperty.getValue(), this.streamTimeoutUnit)) {
                dataToDecorated.release();

                if (ioexception != null)
                    throw ioexception;
            } else {
                /*
                 * Trouble: The writer thread is locked. The decorated output stream
                 * appears to be locked.
                 */
                throw new TimeoutException("Could not write to decorated output stream after "
                        + (Integer) this.timeoutProperty.getValue() + streamTimeoutUnit.toString());
            }
        } catch (InterruptedException e) {
            throw new IOException("Interruped aquiring write semaphore in TimeoutOutputStream");
        }
    }
}
