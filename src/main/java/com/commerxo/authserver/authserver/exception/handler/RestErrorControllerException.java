//package com.commerxo.authserver.authserver.exception.handler;
//
//import com.commerxo.authserver.authserver.common.APIError;
//import com.commerxo.authserver.authserver.common.APIResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Order(value = Ordered.HIGHEST_PRECEDENCE)
//@RestController
//public class RestErrorControllerException implements ErrorController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorControllerException.class);
//
//    private List<StackTraceElement> stackTraceElements;
//    private APIResponse<Object> apiResponse;
//    private final ErrorAttributes errorAttributes;
//    private static final String PATH = "/error";
//
//    public RestErrorControllerException(ErrorAttributes errorAttributes) {
//        this.errorAttributes = errorAttributes;
//    }
//
//    @RequestMapping(value = PATH)
//    public ResponseEntity<Object> error(Exception ex, WebRequest webRequest, HttpServletRequest httpServletRequest) {
//        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
//        Map<String, Object> attrs = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
//        LOGGER
//                .error("Forwarded Error Request: {} ", attrs.get("path"), (Throwable) httpServletRequest.getAttribute("javax.servlet.error.exception"));
//        String statusCode =  attrs.get("status").toString();
//        String status =  attrs.get("error").toString();
//        String message = "Forwarded Error Request: " + attrs.get("path").toString();
//        APIError apiError = new APIError(
//                HttpStatus.resolve(Integer.parseInt(status)),
//                message,
//                ex.getStackTrace(),
//                httpServletRequest
//        );
//        return ResponseEntity.status(apiError.getStatusCode()).body(apiError);
//    }
//
//}
