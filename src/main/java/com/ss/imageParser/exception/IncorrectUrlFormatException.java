package com.ss.imageParser.exception;

public class IncorrectUrlFormatException extends RuntimeException {
    public IncorrectUrlFormatException() {
    }

    public IncorrectUrlFormatException(String message) {
        super(message);
    }

    public IncorrectUrlFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectUrlFormatException(Throwable cause) {
        super(cause);
    }

    public IncorrectUrlFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
