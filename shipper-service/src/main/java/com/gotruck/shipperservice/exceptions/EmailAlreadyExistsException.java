package com.gotruck.shipperservice.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {
        super("Email already exists!");
    }
}
