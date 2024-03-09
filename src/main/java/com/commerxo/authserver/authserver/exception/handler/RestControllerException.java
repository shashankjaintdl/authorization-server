//package com.commerxo.authserver.authserver.exception.handler;
//
//import com.commerxo.authserver.authserver.common.APIError;
//import com.commerxo.authserver.authserver.exception.ResourceAlreadyExistException;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//
//public abstract class RestControllerException {
//
//    @ExceptionHandler(value = {ResourceAlreadyExistException.class})
//    protected ResponseEntity<Object> handleResourceAlreadyExist(RuntimeException ex, HttpHeaders headers, HttpServletRequest request){
//        String message = ex.getMessage();
//        APIError apiError = new APIError(
//                HttpStatus.CONFLICT,
//                message,
//                ex.getStackTrace(),
//                request
//        );
//        return ResponseEntity.status(apiError.getStatusCode())
//                .headers(headers)
//                .body(apiError);
//    }
//
//}