package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {

    String extractUserName(String token);

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    Boolean validationToken(String token, UserDetails userDetails);

    String generateResetToken(UserDetails userDetails);

    Long extractUserId(String token);
}
