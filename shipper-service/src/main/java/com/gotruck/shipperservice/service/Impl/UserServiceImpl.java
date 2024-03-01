package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.ImageService;
import com.gotruck.shipperservice.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@EnableWebSecurity
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ImageService imageService;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                // Rollerin olmadığına dikkat edin, boş bir liste olarak sağlanmıştır.
                Collections.emptyList()
        );
    }

    @Override
    public Object getUserProfile(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setEmail(user.getEmail());
                    userProfile.setCompanyName(user.getCompanyName());
                    userProfile.setContactName(user.getContactName());
                    userProfile.setPhoneNumber(user.getPhoneNumber());
                    userProfile.setImage(user.getImage() != null ? user.getImage() : imageService.getDefaultImageUrl()); // Kullanıcının resmi varsa onu kullan, yoksa varsayılan resmi kullan
                    return userProfile;
                })

                .orElseThrow(() -> new NotFoundException("User not found with email: " + email)); // Kullanıcı bulunamazsa hata döndür
    }

    @Override
    public Object getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    UserProfile userProfile = new UserProfile();
                    userProfile.setEmail(user.getEmail());
                    userProfile.setCompanyName(user.getCompanyName());
                    userProfile.setContactName(user.getContactName());
                    userProfile.setPhoneNumber(user.getPhoneNumber());
                    userProfile.setImage(user.getImage());
                    userProfile.setPassword(user.getPassword());
                    return userProfile;
                })

                .orElseThrow(() -> new NotFoundException("User not found 🤨" )); // Kullanıcı bulunamazsa hata döndür
    }

    @Override
    public ResponseEntity<?> updateProfile(UserProfile userProfile, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userEmail = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        updateUserProfile(user, userProfile);
        userRepository.save(user);

        return ResponseEntity.ok().body("Profile updated successfully");
    }

    private void updateUserProfile(User user, UserProfile userProfile) {
        if (userProfile.getCompanyName() != null) {
            user.setCompanyName(userProfile.getCompanyName());
        }
        if (userProfile.getContactName() != null) {
            user.setContactName(userProfile.getContactName());
        }
        if (userProfile.getPhoneNumber() != null) {
            user.setPhoneNumber(userProfile.getPhoneNumber());
        }
        if (userProfile.getImage() != null) {
            user.setImage(userProfile.getImage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUserByEmail(String email) {
        // Find the user with the given email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            // Delete the user from the database
            userRepository.delete(userOptional.get());
            // Return a success response message
            return ResponseEntity.ok().body("User with email " + email + " has been deleted successfully");
        } else {
            // If user not found, return an appropriate error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
        }
    }


}