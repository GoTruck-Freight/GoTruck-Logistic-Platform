//package com.gotruck.truckcategoryservice.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//public class TruckCategoryExceptionHandler {
//    @ExceptionHandler(TruckCategoryNotFoundException.class)
//    public ResponseEntity<Object> handleTruckCategoryNotFoundExcepption(Exception ex){
//        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
//        return buildResponseEntity(apiError);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleGlobalException(Exception ex){
//        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//        return buildResponseEntity(apiError);
//    }
//
//    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//
//}
