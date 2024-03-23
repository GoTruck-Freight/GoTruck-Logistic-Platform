package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.dto.*;
import com.gotruck.shipperservice.model.User;


public interface AuthService {

    User register (RegisterRequest registerRequest);

    public JwtAuthResponse login(LoginRequest loginRequest);

    void forgotPassword(String email);

    void resetPassword(String token, ResetPasswordRequest request);

    JwtAuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest);
}
