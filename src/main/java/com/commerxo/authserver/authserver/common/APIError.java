package com.commerxo.authserver.authserver.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
public class APIError {

    private Date timestamp;
    private Integer statusCode;
    private String status;
    private String requestMethod;
    @Nullable
    private List<String> errorMessage;
    private String path;
    @Nullable
    private List<StackTraceElement> stackTraceElement;

    public APIError() {

    }

    public APIError(Integer statusCode, String status, @Nullable List<String> errorMessage, @Nullable List<StackTraceElement> stackTraceElement, @Nullable HttpServletRequest httpServletRequest) {
        this(httpServletRequest);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status.substring(3).trim();
        this.errorMessage = errorMessage;
        this.stackTraceElement = stackTraceElement;
    }

    public APIError(@Nullable HttpServletRequest httpServletRequest) {
        super();
        this.path = httpServletRequest.getRequestURI();
        this.requestMethod = httpServletRequest.getMethod();
    }

    public APIError(Integer statusCode, String status, @Nullable List<String> errorMessage, @Nullable List<StackTraceElement> stackTraceElement, @Nullable WebRequest request) {
        this(request);
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.status = status.substring(3).trim();
        this.errorMessage = errorMessage;
        this.stackTraceElement = stackTraceElement;
    }

    public APIError(@Nullable WebRequest request) {
        super();
        this.path = request.getDescription(true).substring(4).split(";")[0];
        this.requestMethod = ((ServletWebRequest) request).getHttpMethod().toString();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Nullable
    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@Nullable List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Nullable
    public List<StackTraceElement> getStackTraceElement() {
        return stackTraceElement;
    }

    public void setStackTraceElement(@Nullable List<StackTraceElement> stackTraceElement) {
        this.stackTraceElement = stackTraceElement;
    }
}