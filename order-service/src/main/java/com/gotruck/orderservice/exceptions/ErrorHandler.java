package com.gotruck.orderservice.exceptions;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Log
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(OrderNotFoundException ex) {
        log.throwing("OrderNotFoundException", "Order not found: {}", ex);
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponse handle(ServiceUnavailableException ex) {
        log.throwing("ServiceUnavailableException", "Service is unavailable: {}", ex);
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }

}
