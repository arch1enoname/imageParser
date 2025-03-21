package com.ss.imageParser.exception.support;

import com.ss.Except4Support;

public class CreatingFolderException extends Except4Support {

    public CreatingFolderException(String errorCode, String message, String extendedMessage, Throwable cause) {
        super(errorCode, message, extendedMessage, cause);
    }

    public CreatingFolderException(String errorCode, String message, String extendedMessage) {
        super(errorCode, message, extendedMessage);
    }

    public CreatingFolderException(String errorCode, String extendedMessage, Throwable cause) {
        super(errorCode, extendedMessage, cause);
    }

    public CreatingFolderException(String errorCode, String extendedMessage) {
        super(errorCode, extendedMessage);
    }
}
