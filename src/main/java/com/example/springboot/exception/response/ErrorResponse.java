package com.example.springboot.exception.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {
    private final String message;
    private final int status;
    private final long timestamp;
    private final Map<String, String> errors;

    public ErrorResponse(String message, int status){
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
        this.errors = null;
    }

    public ErrorResponse(String message, int status, Map<String, String> errors)  {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
        this.errors = errors;
    }
}
