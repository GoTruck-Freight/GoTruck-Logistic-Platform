package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.dto.*;
import com.gotruck.shipperservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok("Password reset instructions have been sent to your email.");
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token,@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(token, resetPasswordRequest);
        return ResponseEntity.ok("Password reset successfully");
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build(); // 200 OK
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshTokenRequest.getRefreshToken()));
    }

//    @GetMapping("/verify")
//    public ResponseEntity<Void> verify(@RequestHeader("Authorization") @NotBlank String authorizationHeader) {
//        authService.verify(authorizationHeader);
//        return ResponseEntity.ok().build();
//    }
}
