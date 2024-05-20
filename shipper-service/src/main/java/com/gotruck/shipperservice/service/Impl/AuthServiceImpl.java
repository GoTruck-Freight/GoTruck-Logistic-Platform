package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.dto.*;
import com.gotruck.shipperservice.exceptions.EmailAlreadyExistsException;
import com.gotruck.shipperservice.exceptions.UnauthorizedException;
import com.gotruck.shipperservice.exceptions.UserNotFoundException;
import com.gotruck.shipperservice.mapper.UserMapper;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.AuthService;
import com.gotruck.shipperservice.service.EmailService;
import com.gotruck.shipperservice.service.ImageService;
import com.gotruck.shipperservice.service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final ImageService imageService;
    private final UserMapper userMapper;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                           JwtService jwtService, EmailService emailService, ImageService imageService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.imageService = imageService;
        this.userMapper = userMapper;
    }
    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        UserDto userDto = UserDto.builder()
                .companyName(registerRequest.getCompanyName())
                .contactName(registerRequest.getContactName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .image(imageService.getDefaultImageUrl())
                .accountStatus(AccountStatus.ENABLED)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        // System.out.println("UserDto: " + userDto); // Debugging
        User user = userMapper.toUser(userDto);
        // System.out.println("User: " + user); // Debugging
        userRepository.save(user);
    }

    @Override
    public JwtAuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid email or password");}
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);
        var jwt = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    @Override
    public void logout() {
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Invalidate the authentication object (sign-out)
        authentication.setAuthenticated(false);
        // Clear the security context holder
        SecurityContextHolder.clearContext();
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        String resetToken = jwtService.generateResetToken(user);
//        String resetLink = "http://gotruck.com/reset-password?token=" + resetToken;
        String resetLink = "http://localhost:9091/api/v1/auth/reset-password/" + resetToken;
        String emailBody= "Hello,\n\n" +
                "You have requested to reset your password. Please click on the link below to reset your password. If you did not request this action, you can ignore this email.\n\n" +
                "Password Reset Link: " + resetLink + "\n\n" +
                "This link will expire in 1 hour for security reasons. If you encounter any issues, please contact support.\n\n" +
                "Best regards,\nGoTruck Team";
        String emailSubject = "Password Reset";
        emailService.sendEmail(email, emailSubject, emailBody);
    }

    @Override
    public void resetPassword(String token, ResetPasswordRequest request) {
        try {
            Long userId = jwtService.extractUserId(token);
            User user = userRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new);
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } catch (JwtException e) {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }

    @Override
    public JwtAuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUserName(refreshToken);

        // Find the user in the database using the username
        User user = userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);

        if (!jwtService.validateToken(refreshToken, user)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        String newAccessToken = jwtService.generateAccessToken(user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(newAccessToken);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }
}





