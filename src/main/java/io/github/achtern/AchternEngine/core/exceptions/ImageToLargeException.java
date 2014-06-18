package io.github.achtern.AchternEngine.core.exceptions;

import java.io.IOException;

public class ImageToLargeException extends IOException {

    /**
     * Constructs an {@code ImageToLargeException} with the specified detail message
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
     */
    public ImageToLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code IOException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public ImageToLargeException(String message) {
        super(message);
    }
}
