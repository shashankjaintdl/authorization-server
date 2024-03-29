package com.commerxo.authserver.authserver.exception;

import org.springframework.web.ErrorResponse;

public class ResourceAlreadyExistException extends RuntimeException  {
    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistException(Throwable cause) {
        super(cause);
    }


}
