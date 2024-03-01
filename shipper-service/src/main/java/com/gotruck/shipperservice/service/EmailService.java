package com.gotruck.shipperservice.service;

public interface EmailService {
    public abstract void sendEmail(String to, String subject, String body);
}
