package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.model.dto.UserDto;
import com.gotruck.shipperservice.model.dto.UserProfile;
import com.gotruck.shipperservice.exceptions.UnauthorizedException;
import com.gotruck.shipperservice.exceptions.UserNotFoundException;
import com.gotruck.shipperservice.mapper.UserMapper;
import com.gotruck.shipperservice.dao.entity.UserEntity;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import com.gotruck.shipperservice.dao.repository.UserRepository;
import com.gotruck.shipperservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                 .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                 .orElseThrow(UserNotFoundException::new);

        // Create a UserDetails object using the user’s information
        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                new ArrayList<>()); // No authorities for now
    }
    @Override
    public UserProfile getUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        UserEntity userEntity = userRepository.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toUserProfile(userEntity);
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
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        UserDto userDto = userMapper.toUserDto(userEntity);
        updateUserProfile(userDto, userProfile);
        userEntity = userMapper.toUserEntity(userDto);
        userRepository.save(userEntity);

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
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setAccountStatus(newStatus);
        userRepository.save(userEntity);
    }

}