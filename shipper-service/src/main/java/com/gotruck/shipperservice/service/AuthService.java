package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.dto.*;


public interface AuthService {

    void register (RegisterRequest registerRequest);

    JwtAuthResponse login(LoginRequest loginRequest);

    void logout();

    void forgotPassword(String email);

    void resetPassword(String token, ResetPasswordRequest request);

    JwtAuthResponse refreshAccessToken(String refreshTokenRequest);
}
