package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.dto.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface  UserService {
    UserDetailsService userDetailsService();

    UserProfile getUserProfile(String email);

    Object getUserById(Long id);

    ResponseEntity<?> updateProfile(UserProfile userProfile, Authentication authentication);

    void deleteUserByEmail(String email);

}

