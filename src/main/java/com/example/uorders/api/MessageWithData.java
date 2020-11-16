package com.example.uorders.api;

import lombok.Data;

@Data
public class MessageWithData extends Message {

    private int status;
    private String message;
    private Object data;

    public MessageWithData() {
        this.status = StatusCode.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }

}
