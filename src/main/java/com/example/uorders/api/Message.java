package com.example.uorders.api;

import lombok.Data;

@Data
public class Message {
    private int status;
    private String message;

    public Message(){
        this.status = StatusCode.BAD_REQUEST;
        this.message = null;
    }
}
