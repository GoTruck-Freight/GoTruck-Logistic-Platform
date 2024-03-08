package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.dto.JwtAuthResponse;
import com.gotruck.shipperservice.dto.LoginRequest;
import com.gotruck.shipperservice.dto.RegisterRequest;
import com.gotruck.shipperservice.dto.ResetPasswordRequest;
import com.gotruck.shipperservice.model.User;

public interface AuthService {
    User register (RegisterRequest registerRequest);
    public JwtAuthResponse login(LoginRequest loginRequest);
    void forgotPassword(String email);
//    void resetPassword(ResetPasswordRequest request);

    void resetPassword(String token, ResetPasswordRequest request);
}
