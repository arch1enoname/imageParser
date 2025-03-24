package com.ss.imageParser.exception;

import com.ss.ExceptInfoUser;
import com.ss.Message4User_I;

import java.util.Map;

public class ForbiddenException extends ExceptInfoUser{
    public ForbiddenException(Message4User_I message) {
        super(message);
    }

    public ForbiddenException(Message4User_I message, String message4support) {
        super(message, message4support);
    }

    public ForbiddenException(Map<String, String> errors) {
        super(errors);
    }
}
