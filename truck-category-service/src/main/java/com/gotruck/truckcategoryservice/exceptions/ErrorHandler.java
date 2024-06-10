package com.gotruck.truckcategoryservice.exceptions;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Log
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(TruckNameNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(TruckNameNotFoundException ex) {
        log.throwing("TruckNameNotFoundException", "Truck name not found: {}", ex);
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(TruckCategoryNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(TruckCategoryNotFoundException ex) {
        log.throwing("TruckCategoryNotFoundException", "Truck category not found: {}", ex);
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .build();
    }
}

