package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    Boolean validationToken(String token, UserDetails userDetails);
    String generateResetToken(User user);

    Long extractUserId(String token);
}
