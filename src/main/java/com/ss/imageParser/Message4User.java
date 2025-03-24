package com.ss.imageParser;

import com.ss.Message4User_I;

class Message4User implements Message4User_I
{
    private String message;

    public Message4User(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }


}
