package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.dto.*;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.AuthService;
import com.gotruck.shipperservice.service.EmailService;
import com.gotruck.shipperservice.service.ImageService;
import com.gotruck.shipperservice.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  AuthenticationManager authenticationManager;
    private  JwtService jwtService;
    private  EmailService emailService;
    private ImageService imageService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, EmailService emailService, ImageService imageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.imageService = imageService;
    }
    @Override
    public User register(RegisterRequest registerRequest) {
        // E-posta adresinin daha önceden kullanılıp kullanılmadığını kontrol et
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email address is already in use");
        }

        // Parola gücünü kontrol et
        String password = registerRequest.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one digit");
        }

        User user = new User();
        user.setCompanyName(registerRequest.getCompanyName());
        user.setContactName(registerRequest.getContactName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setEmail(registerRequest.getEmail());
        user.setImage(imageService.getDefaultImageUrl());
        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(Role.USER); user.setRole(registerRequest.Role.role);
        return userRepository.save(user);
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid email or password", e);
        }

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwt = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        String resetToken = jwtService.generateResetToken(user);
//        String resetLink = "http://gotruck.com/reset-password?token=" + resetToken;
        String resetLink = "http://localhost:9091/api/v1/auth/reset-password/token/" + resetToken;
        String emailBody = "To reset your password, click on the link below:\n" + resetLink;
        emailService.sendEmail(email, "Password Reset", emailBody);
    }

    @Override
    public void resetPassword(String token, ResetPasswordRequest request) {
        try {
            // Extract user ID from the token
            Long userId = jwtService.extractUserId(token);

            // Fetch user from the database
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            // Update the user's password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            // Save the updated user
            userRepository.save(user);
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        } catch (NotFoundException e) {
            throw e; // Bu durumda özel olarak ele alınacak bir hata değil, doğrudan fırlatılıyor
        } catch (Exception e) {
            throw new InternalServerErrorException("An error occurred while resetting the password");
        }
    }

    public JwtAuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String userEmail = jwtService.extractUserName(refreshTokenRequest.getRefreshToken());
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + userEmail));

            if (jwtService.validationToken(refreshTokenRequest.getRefreshToken(), user)) {
                // Generate a new access token and refresh token
                var newAccessToken = jwtService.generateAccessToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);

                JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
                jwtAuthResponse.setAccessToken(newAccessToken);
                jwtAuthResponse.setRefreshToken(newRefreshToken);

                return jwtAuthResponse;
            } else {
                throw new IllegalArgumentException("Invalid refresh token");
            }
        } catch (Exception e) {
            // Handle any exceptions and return an appropriate response
            return new JwtAuthResponse(); // Or handle specific error cases
        }
    }
}





