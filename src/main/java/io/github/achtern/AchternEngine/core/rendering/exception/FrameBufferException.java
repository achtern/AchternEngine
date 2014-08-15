package io.github.achtern.AchternEngine.core.rendering.exception;

/**
 * Will be thrown when the FrameBuffer is not valid
 */
public class FrameBufferException extends Exception {

    /**
     * Calls super with message
     * @param message The message
     */
    public FrameBufferException(String message) {
        super(message);
    }

}
