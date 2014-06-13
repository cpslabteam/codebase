package codebase.streams;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * An input stream decorator that times out (instead of blocking) on read operations.
 * <p>
 * This stream decorator is especially useful to add timeout behavior to an existing
 * stream.
 */
public class TimeoutInputStream extends
        FilterInputStream {

    /**
     * Port default timeout in milliseconds for open, read and write operations.
     */
    private static final int TIMEOUT_MILLIS = 2000;

    /**
     * The timeout for open, message send and receive operations.
     */
    private final int streamTimeout;

    /**
     * Up when the sender has a message to send.
     */
    private final Semaphore dataFromDecorated = new Semaphore(0);

    /**
     * Up when the a new message can be written into the buffer.
     */
    private final Semaphore dataToClient = new Semaphore(0);

    /**
     * The last message read from the buffer.
     */
    private int message;

    /**
     * Last exception that occurred, if any. Can be <code>null</code>.
     */
    private IOException ioexception;

    /**
     * Reads data form the input stream and puts it in the queue.
     */
    private final class DataReader extends
            Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    dataFromDecorated.acquire();
                    try {
                        message = in.read();
                        ioexception = null;
                    } catch (IOException e) {
                        ioexception = e;
                    }
                    dataToClient.release();
                }
            } catch (InterruptedException ex) {
                /*
                 * If the thread is stopped its not a problem: we just ignore it since we are 
                 * not locking any resources. We are being stopped for shutdown.
                 */
            }
        }
    };

    private final DataReader dataReader = new DataReader();

    /**
     * Instantiates a new timeout stream decorator with default timeout.
     * 
     * @param in the input stream to be decorated from where the reading will take place.
     */
    public TimeoutInputStream(final InputStream in) {
        this(in, TIMEOUT_MILLIS);
    }

    /**
     * Instantiates a new timeout stream decorator with the given timeout.
     * 
     * @param in the input stream to be decorated from where the reading will take place.
     * @param timeout the driver timeout parameter for open, read and write operations in
     *            millis. Must be positive.
     */
    public TimeoutInputStream(final InputStream in, final int timeout) {
        super(in);

        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be positive.");
        }
        streamTimeout = timeout;
        dataReader.start();
    }

    @Override
    public void close() throws IOException {
        dataReader.interrupt();
        super.close();
    }

    @Override
    public int read() throws IOException {
        dataFromDecorated.release();
        Thread.yield();

        try {
            /*
             * Check that the reader thread is not blocked inside 'in.read()'
             */
            if (dataToClient.tryAcquire(this.streamTimeout, TimeUnit.MILLISECONDS)) {
                if (ioexception == null)
                    return message;
                else
                    throw ioexception;
            } else {
                /*
                 * Trouble: DTR is still down then we are locked somewhere!
                 */
                throw new TimeoutException("Could not read from decorated input stream after "
                        + streamTimeout + TimeUnit.MILLISECONDS.toString());
            }
        } catch (InterruptedException e) {
            throw new IOException("Interruped aquiring read semaphore in TimeoutInputStream");
        }
    }

    @Override
    public int read(byte b[]) throws IOException {
        int i = 0;
        while (i < b.length) {
            b[i] = (byte) this.read();
            i++;
        }
        return i;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int i = off;
        while (i < (off + len)) {
            b[i] = (byte) this.read();
            i++;
        }
        return i;
    }

    @Override
    public int available() throws IOException {
        int available = 0;
        if (dataToClient.availablePermits() > 0)
            available++;
        available += in.available();
        return available;
    }
}
