package io.github.achtern.AchternEngine.core.resource.fileparser;

public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
