package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.model.dto.request.LoginRequest;
import com.gotruck.shipperservice.model.dto.request.RegisterRequest;
import com.gotruck.shipperservice.model.dto.request.ResetPasswordRequest;
import com.gotruck.shipperservice.model.dto.response.JwtAuthResponse;


public interface AuthService {

    void register (RegisterRequest registerRequest);

    JwtAuthResponse login(LoginRequest loginRequest);

    void logout();

    void forgotPassword(String email);

    void resetPassword(String token, ResetPasswordRequest request);

    JwtAuthResponse refreshAccessToken(String refreshTokenRequest);
}
