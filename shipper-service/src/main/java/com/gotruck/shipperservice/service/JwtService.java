package com.gotruck.shipperservice.service;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {

    String extractUserName(String token);

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String generateResetToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

    Long extractUserId(String token);
}
