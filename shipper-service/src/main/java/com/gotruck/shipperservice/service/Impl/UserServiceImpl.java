package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.dto.UserDto;
import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.exceptions.UnauthorizedException;
import com.gotruck.shipperservice.exceptions.UserNotFoundException;
import com.gotruck.shipperservice.mapper.UserMapper;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebSecurity
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                 .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                 .orElseThrow(UserNotFoundException::new);

        // Create a UserDetails object using the user’s information
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()); // No authorities for now
    }
    @Override
    public UserProfile getUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toUserProfile(user);
    }

    @Override
    public UserDto findOne(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProfile(UserProfile userProfile, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        UserDto userDto = userMapper.toUserDto(user);
        updateUserProfile(userDto, userProfile);
        user = userMapper.toUser(userDto);
        userRepository.save(user);

        return ResponseEntity.ok().body("Profile updated successfully");
    }

    private void updateUserProfile(UserDto userDto, UserProfile userProfile) {
        if (userProfile.getCompanyName() != null && !userProfile.getCompanyName().isEmpty()) {
            userDto.setCompanyName(userProfile.getCompanyName());
        }
        if (userProfile.getContactName() != null && !userProfile.getContactName().isEmpty()) {
            userDto.setContactName(userProfile.getContactName());
        }
        if (userProfile.getPhoneNumber() != null && !userProfile.getPhoneNumber().isEmpty()) {
            userDto.setPhoneNumber(userProfile.getPhoneNumber());
        }
        if (userProfile.getImage() != null && !userProfile.getImage().isEmpty()) {
            userDto.setImage(userProfile.getImage());
        }
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateAccountStatus(Long id, AccountStatus newStatus) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setAccountStatus(newStatus);
        userRepository.save(user);
    }

}