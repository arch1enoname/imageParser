package com.ss.imageParser.exception.support;

import com.ss.Except4Support;

public class ImageDownloadException extends Except4Support {
    public ImageDownloadException(String errorCode, String message, String extendedMessage, Throwable cause) {
        super(errorCode, message, extendedMessage, cause);
    }

    public ImageDownloadException(String errorCode, String message, String extendedMessage) {
        super(errorCode, message, extendedMessage);
    }

    public ImageDownloadException(String errorCode, String extendedMessage, Throwable cause) {
        super(errorCode, extendedMessage, cause);
    }

    public ImageDownloadException(String errorCode, String extendedMessage) {
        super(errorCode, extendedMessage);
    }
}
