package com.ss.imageParser.exception;

import com.ss.ExceptInfoUser;
import com.ss.Message4User_I;

import java.util.Map;

public class UnauthorizedException extends ExceptInfoUser {
    public UnauthorizedException(Message4User_I message) {
        super(message);
    }

    public UnauthorizedException(Message4User_I message, String message4support) {
        super(message, message4support);
    }

    public UnauthorizedException(Map<String, String> errors) {
        super(errors);
    }
}
