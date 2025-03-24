package com.ss.imageParser.exception;

import com.ss.ExceptInfoUser;
import com.ss.Message4User_I;

import java.util.Map;

public class ResourceNotFoundException extends ExceptInfoUser {
    public ResourceNotFoundException(Message4User_I message) {
        super(message);
    }

    public ResourceNotFoundException(Message4User_I message, String message4support) {
        super(message, message4support);
    }

    public ResourceNotFoundException(Map<String, String> errors) {
        super(errors);
    }
}
