package com.gotruck.truckcategoryservice.exceptions;

public class TruckNameNotFoundException extends RuntimeException{

    public TruckNameNotFoundException(String message) {
        super(message);
    }

    public TruckNameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
