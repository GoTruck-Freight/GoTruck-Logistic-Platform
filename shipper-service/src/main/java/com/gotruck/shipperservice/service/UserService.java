package com.gotruck.shipperservice.service;

import com.gotruck.shipperservice.model.dto.UserDto;
import com.gotruck.shipperservice.model.dto.UserProfile;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface  UserService {
    UserDetailsService userDetailsService();

//    UserProfile getUserProfile(String email);

    UserProfile getUserProfile(Authentication authentication);

    UserDto findOne(Long id);

    List<UserDto> findAll();

//    void updateUserProfile(Long id, UserProfile userProfile);

    @Transactional
    UserProfile updateProfile(UserProfile userProfile, Authentication authentication);

    UserProfile patchUserProfile(Long id, Map<String, Object> fields);

    @Transactional
    void delete(Long id);

    void updateAccountStatus(Long userId, AccountStatus newStatus);
}

