package com.gotruck.shipperservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface  UserService<UserProfile> {
    UserDetailsService userDetailsService();

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserProfile getUserProfile(String email);

    Object getUserById(Long id);

    ResponseEntity<?> updateProfile(com.gotruck.shipperservice.dto.UserProfile userProfile, Authentication authentication);

    ResponseEntity<String> deleteUserByEmail(String email);

}

