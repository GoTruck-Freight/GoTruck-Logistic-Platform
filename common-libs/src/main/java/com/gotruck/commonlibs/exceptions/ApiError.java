package com.gotruck.commonlibs.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
public class ApiError {

    private HttpStatus status;

    private Date timestamp;

    private String message;

    private List<String> errors;

    private String path;

    private ApiError() {
        timestamp = new Date();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message) {
        this(status);
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, String error) {
        this(status, message);
        this.errors = Collections.singletonList(error);
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this(status, message);
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, List<String> errors, String path) {
        this(status, message, errors);
        this.path = path;
    }

    public ApiError(HttpStatus status, String message, String error, String path) {
        this(status, message, Collections.singletonList(error), path);
    }
}