package codebase.streams;

import java.io.IOException;

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
