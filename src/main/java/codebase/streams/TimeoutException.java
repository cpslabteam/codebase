package codebase.streams;

import java.io.IOException;

/**
 * The exception thrown to signal that the streams timeout.
 * <p>
 * This is a sub-type of {@link IOException} thrown by {@link TimeoutInputStream} and
 * {@link TimeoutOutputStream}.
 */
public class TimeoutException extends
        IOException {
    private static final long serialVersionUID = 1L;

    public TimeoutException(TimeoutInputStream timeoutInputStream) {
        super();
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }
}
