package com.example.LendBuddy.advices;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
@Data
public class APIResponse<T> {
    private T data;
    private APIError error;
    private LocalDateTime timeStamp;

    public APIResponse() {
        timeStamp=LocalDateTime.now();
    }
    public APIResponse(T data){
        this();
        this.data=data;

    }

    public APIResponse(APIError apiError){
        this();
        this.error=apiError;
    }

}
