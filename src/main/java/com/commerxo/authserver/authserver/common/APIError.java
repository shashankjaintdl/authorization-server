package com.commerxo.authserver.authserver.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

public class APIError {

    private Date timestamp;
    private Integer statusCode;
    private String status;
    private String requestMethod;
    private String errorMessage;
    private String path;
    private List<StackTraceElement> stackTraceElement;

    public APIError(HttpStatus statusCode, @Nullable String errorMessage, @Nullable HttpServletRequest httpServletRequest){
        this(httpServletRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode.value();
        this.status = statusCode.toString();
        this.errorMessage = errorMessage;
    }

    public APIError(HttpStatus statusCode, @Nullable String errorMessage, @Nullable StackTraceElement[] stackTraceElement, @Nullable HttpServletRequest httpServletRequest) {
        this(statusCode, errorMessage, httpServletRequest);
        this.stackTraceElement = stackTraceElement != null ? Arrays.stream(stackTraceElement).limit(10).collect(Collectors.toList()) : Collections.emptyList(); }

    public APIError(@Nullable HttpServletRequest httpServletRequest) {
        if(httpServletRequest != null) {
            this.path = httpServletRequest.getRequestURI();
            this.requestMethod = httpServletRequest.getMethod();
        }
    }

    public APIError(HttpStatus statusCode, @Nullable String errorMessage, @Nullable WebRequest request){
        this(request);
        this.timestamp = new Date();
        this.statusCode = statusCode.value();
        this.status = statusCode.toString();
        this.errorMessage = errorMessage;
    }

    public APIError(HttpStatus statusCode, @Nullable String errorMessage, @Nullable StackTraceElement[] stackTraceElement, @Nullable WebRequest request) {
        this(statusCode, errorMessage, request);
        this.stackTraceElement = stackTraceElement != null ? Arrays.stream(stackTraceElement).limit(10).collect(Collectors.toList()) : Collections.emptyList();
    }

    public APIError(@Nullable WebRequest request) {
        if(request != null) {
            this.path = request.getDescription(true).substring(4).split(";")[0];
            this.requestMethod = ((ServletWebRequest) request).getHttpMethod().toString();
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    @JsonIgnore
    public HttpStatus getHttpStatus(){
        return HttpStatus.resolve(statusCode);
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getPath() {
        return path;
    }

    public List<StackTraceElement> getStackTraceElement() {
        return stackTraceElement;
    }
}