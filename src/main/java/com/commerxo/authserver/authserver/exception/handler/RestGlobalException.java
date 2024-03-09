package com.commerxo.authserver.authserver.exception.handler;

import com.commerxo.authserver.authserver.common.APIError;
import com.commerxo.authserver.authserver.exception.ResourceAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestGlobalException extends ResponseEntityExceptionHandler  {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestGlobalException.class);

    protected APIError apiError;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String errorMessage = "";
        if(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()!=null)
            errorMessage = ex.getFieldError().getDefaultMessage();

        LOGGER.error("Error ==> {0}",ex);

        apiError = new APIError(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                ex.getStackTrace(),
                request);

        return buildResponseEntity(apiError, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        LOGGER.error("Error ==> {0}",ex);

        apiError = new APIError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                ex.getStackTrace(),
                request);

        return  buildResponseEntity(apiError, request);
    }

    @ExceptionHandler(value = {ResourceAlreadyExistException.class})
    protected ResponseEntity<Object> handleResourceAlreadyExist(ResourceAlreadyExistException ex, WebRequest request){
        String message = ex.getMessage();
        APIError apiError = new APIError(
                HttpStatus.CONFLICT,
                message,
                ex.getStackTrace(),
                request
        );
        return buildResponseEntity(apiError, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        String message = ex.getMessage();
        APIError apiError = new APIError(
                HttpStatus.BAD_REQUEST,
                message,
                ex.getStackTrace(),
                request
        );
        return buildResponseEntity(apiError, request);
    }

    private ResponseEntity<Object> buildResponseEntity(APIError apiError, WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        return createResponseEntity(apiError, headers, apiError.getHttpStatus(), request);
    }

}