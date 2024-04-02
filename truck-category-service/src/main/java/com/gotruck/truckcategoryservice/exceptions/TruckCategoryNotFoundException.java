package com.gotruck.truckcategoryservice.exceptions;

public class TruckCategoryNotFoundException extends RuntimeException {
    public TruckCategoryNotFoundException(String message){
        super(message);
    }
}
