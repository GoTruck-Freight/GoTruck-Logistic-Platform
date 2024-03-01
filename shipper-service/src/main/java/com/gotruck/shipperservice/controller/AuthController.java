package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.dto.*;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.AuthService;
import com.gotruck.shipperservice.service.EmailService;
import com.gotruck.shipperservice.service.Impl.JWTServiceImpl;
import com.gotruck.shipperservice.service.UserService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTServiceImpl jwtService;

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

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        resetPasswordRequest.setToken(token);
        authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok("Password reset successfully");
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();
            userService.deleteUserByEmail(userEmail);
            return ResponseEntity.ok("Hesabınız başarıyla silindi.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Yetkisiz erişim.");
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteUser() {
//        // Mevcut oturumu al
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Eğer oturum varsa, kullanıcının kimliğini al ve hesabını sil
//        if (authentication != null) {
//            String userEmail = authentication.getName(); // Kullanıcının email adresini al
//
//            // UserService aracılığıyla hesabı sil
//            userService.deleteUserByEmail(userEmail);
//
//            return ResponseEntity.ok("Hesabınız uğurla silindi, yenidən görüşənədək!");
//        } else {
//            // Oturum açık değilse, yetkisiz erişim hatası döndür
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Yetkisiz erişim.");
//        }
//    }
}


//    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
//        // Tarayıcıda bulunan JWT token'ini veya cookie'yi sil
//        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", "")
//                .maxAge(0)
//                .httpOnly(true)
//                .path("/")
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        // Oturumu sonlandırma işlemleri (Spring Security ile ilgili)
//
//        // Oturumu sonlandırma işlemi tamamlandıktan sonra başarılı bir response dön
//        return ResponseEntity.ok().build();
//    }
//    @PostMapping("/logout")
//    public void logout() {
//        // Mevcut oturumu al
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Eğer oturum varsa, sonlandır
//        if (authentication != null) {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//    }

