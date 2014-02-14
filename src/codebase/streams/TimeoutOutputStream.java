package codebase.streams;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * An output stream decorator that times out (instead of blocking) on write operations.
 * <p>
 * This stream decorator is especially useful to add timeout behavior to an existing
 * stream.
 */
public class TimeoutOutputStream extends
        FilterOutputStream {

    /**
     * Port default timeout in milliseconds for open, read and write operations.
     */
    private static final int TIMEOUT_MILLIS = 2000;

    /**
     * The timeout for open, message send and receive operations.
     */
    private final int streamTimeout;

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
                        //System.out.println("DW:" + new String(message));
                        //long millis = System.currentTimeMillis();
                        out.write(message);
                        //System.out.println("Took " + (System.currentTimeMillis() - millis)
                        //        + " millis");
                        ioexception = null;
                        dataToDecorated.release();
                    } catch (IOException e) {
                        ioexception = e;
                    }
                }
            } catch (InterruptedException ex) {
                /*
                 * If the thread is stopped its not a problem: we just ignore it since we are 
                 * not locking any resources. We are being stopped for shutdown.
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
        this(out, TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
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
        streamTimeout = timeout;
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
     */
    @Override
    public synchronized void close() throws IOException {
        isClosed = false;
        dataWritter.interrupt();
        try {
            dataWritter.join(TimeoutOutputStream.this.streamTimeout);
            if (!dataWritter.isAlive()) {
                isClosed = true;
                super.close();
            }
        } catch (InterruptedException e) {
            isClosed = false;
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
             * Checks if it the last message was sent.
             * This way, there is no need for the write() method to be synchronized.
             */
            if (!dataToDecorated.tryAcquire(this.streamTimeout, this.streamTimeoutUnit)) {
                throw new TimeoutException("Could not write to decorated output stream after "
                        + streamTimeout + streamTimeoutUnit.toString());
            }

            /*
             * Put the message in the buffer and wait for the DataWritter thread to consume it
             */
            message = b.clone();

            dataFromClient.release();
            Thread.yield();

            /*
             * Check that the writer thread has consumed the message and is not blocked inside 'in.read()'
             */
            if (dataToDecorated.tryAcquire(this.streamTimeout, this.streamTimeoutUnit)) {
                dataToDecorated.release();
                //System.out.println("writen: '" + new String(message) + "'");

                if (ioexception != null)
                    throw ioexception;
            } else {
                /*
                 * Trouble: The writer thread is locked. The decorated output stream appears to be locked.  
                 */
                throw new TimeoutException("Could not write to decorated output stream after "
                        + streamTimeout + streamTimeoutUnit.toString());
            }
        } catch (InterruptedException e) {
            throw new IOException("Interruped aquiring write semaphore in TimeoutOutputStream");
        }
    }
}
