package com.example.springboot.exception.response;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final int Status;
    private final long timestamp;

    public ErrorResponse(String message, int status){
        this.message = message;
        this.Status = status;
        this.timestamp = System.currentTimeMillis();
    }
}
