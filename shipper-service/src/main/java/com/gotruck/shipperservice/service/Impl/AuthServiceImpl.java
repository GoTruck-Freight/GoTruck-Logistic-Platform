package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.dto.JwtAuthResponse;
import com.gotruck.shipperservice.dto.LoginRequest;
import com.gotruck.shipperservice.dto.RegisterRequest;
import com.gotruck.shipperservice.dto.ResetPasswordRequest;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.AuthService;
import com.gotruck.shipperservice.service.EmailService;
import com.gotruck.shipperservice.service.ImageService;
import com.gotruck.shipperservice.service.JwtService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private ImageService imageService;

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

        var jwt = jwtService.generateToken(user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        return jwtAuthResponse;
    }


    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        String resetToken = jwtService.generateResetToken(user);
//        String resetLink = "http://gotruck.com/reset-password?token=" + resetToken;
        String resetLink = "http://localhost:9091/reset-password?token=" + resetToken;
        String emailBody = "To reset your password, click on the link below:\n" + resetLink;
        emailService.sendEmail(email, "Password Reset", emailBody);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        String email = request.getEmail();
        String resetToken = request.getToken();
        // E-posta adresinden kullanıcıyı bulma işlemi
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        // Token'in doğruluğunu ve geçerliliğini kontrol etme işlemi
        if (jwtService.validationToken(resetToken, user)) {
            // Yeni şifreyi ayarlama işlemi
            String newPassword = request.getNewPassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            // Token geçerli değilse veya kullanıcı eşleşmiyorsa hata işleme
            throw new RuntimeException("Invalid or expired token");
        }
    }
}