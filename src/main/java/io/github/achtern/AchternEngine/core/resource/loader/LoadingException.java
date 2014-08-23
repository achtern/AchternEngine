package io.github.achtern.AchternEngine.core.resource.loader;

import java.io.IOException;

public class LoadingException extends IOException {

    /**
     * Constructs an {@code LoadingException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public LoadingException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code LoadingException} with the specified detail message
     * and cause.
     * <p/>
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     * @param cause   The cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     * @since 1.6
     */
    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
