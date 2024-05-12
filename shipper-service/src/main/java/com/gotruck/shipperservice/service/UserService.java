package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.dto.UserDto;
import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface  UserService {
    UserDetailsService userDetailsService();

//    UserProfile getUserProfile(String email);

    UserProfile getUserProfile(Authentication authentication);

    UserDto findOne(Long id);

    List<UserDto> findAll();

//    void updateUserProfile(Long id, UserProfile userProfile);

    @Transactional
    ResponseEntity<String> updateProfile(UserProfile userProfile, Authentication authentication);

    @Transactional
    void delete(Long id);

    void updateAccountStatus(Long userId, AccountStatus newStatus);
}

