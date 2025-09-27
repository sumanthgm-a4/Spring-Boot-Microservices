package com.example.question_service.exceptions;

import java.time.LocalDateTime;

public class ApiError {
    
    private int statusCode;
    private String message;
    private final LocalDateTime timeStamp;

    public ApiError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    // public void setTimeStamp(LocalDateTime timeStamp) {
    //     this.timeStamp = timeStamp;
    // }

    
}
