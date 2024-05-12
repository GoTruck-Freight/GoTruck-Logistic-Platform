package com.gotruck.shipperservice.service;


public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
