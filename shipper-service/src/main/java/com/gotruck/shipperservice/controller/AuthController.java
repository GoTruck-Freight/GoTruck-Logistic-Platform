package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.dto.*;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.AuthService;
import com.gotruck.shipperservice.service.EmailService;
import com.gotruck.shipperservice.service.Impl.JWTServiceImpl;
import com.gotruck.shipperservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok("Password reset instructions have been sent to your email.");
    }

    @PostMapping("/reset-password/token/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(token, resetPasswordRequest);
        return ResponseEntity.ok("Password reset successfully");
    }


//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestHeader("Authorization") String token, @RequestBody ResetPasswordRequest resetPasswordRequest) {
//        authService.resetPassword(token, resetPasswordRequest);
//        return ResponseEntity.ok("Password reset successfully");
//    }
}


//    @PostMapping("/logout")
////    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
//        // Tarayıcıda bulunan JWT token'ini veya cookie'yi sil
//        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", "")
//                .maxAge(0)
//                .httpOnly(true)
//                .path("/")
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        // Oturumu sonlandırma işlemi tamamlandıktan sonra başarılı bir response dön
//        return ResponseEntity.ok().build();
//    }
//}
