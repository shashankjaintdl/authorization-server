package com.commerxo.authserver.authserver.exception.handler;

import com.commerxo.authserver.authserver.common.APIError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestGlobalException extends ResponseEntityExceptionHandler  {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestGlobalException.class);

    protected APIError apiError;
    protected List<StackTraceElement> stackTraceElements;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @Nullable HttpHeaders headers,
                                                                  @Nullable HttpStatusCode status,
                                                                  @Nullable WebRequest request) {
        String msg = "";
        if(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()!=null)
            msg = ex.getFieldError().getDefaultMessage();

        LOGGER.info("{}",ex);

        stackTraceElements =  Arrays
                .stream(ex.getStackTrace())
                .limit(10)
                .collect(Collectors.toList());

        apiError = new APIError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                Collections.singletonList(msg),
                stackTraceElements,
                request);

        return  ResponseEntity.status(apiError.getStatusCode())
                .body(apiError);
    }



}
