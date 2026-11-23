package com.azim.carsales.commons.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse <T>{

    private boolean status;
    private String Message;
    private T data;
    private int statusCode;
    private LocalDateTime timeStamp;

    public ApiResponse(boolean status, String message, T data, int statusCode) {
        this.status = status;
        Message = message;
        this.data = data;
        this.statusCode = statusCode;
        this.timeStamp = LocalDateTime.now();
    }
}
