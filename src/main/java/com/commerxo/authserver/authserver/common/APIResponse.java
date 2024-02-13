package com.commerxo.authserver.authserver.common;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class APIResponse<T>  {

    private Date timestamp;
    private String message;
    private int status;
    private T data;

    public APIResponse(HttpStatus status, T data){
        this(status, data, null);
    }

    public APIResponse(HttpStatus status, String message){
        this(status, null, message);
    }

    public APIResponse(HttpStatus status, T data, String message){
        this.timestamp = new Date();
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
