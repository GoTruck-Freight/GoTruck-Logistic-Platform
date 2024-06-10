package com.gotruck.shipperservice.service.Impl;

import com.gotruck.common.dto.order.OrderDTO;
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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public UserProfile updateProfile(UserProfile userProfile, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        String userEmail = authentication.getName();
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        userMapper.toUserProfile(userEntity);
        userEntity = userRepository.save(userEntity);
        return userMapper.toUserProfile(userEntity);
    }

    @Override
    public UserProfile patchUserProfile(Long id, Map<String, Object> fields) {
        UserEntity existingUserEntity = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        UserEntity finalExistingUserEntity = existingUserEntity;
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, k);
            if (field != null) {
                field.setAccessible(true);
                Object value = v;
                // Handle type conversion if necessary
                if (field.getType().equals(Long.class) && v instanceof Integer) {
                    value = Long.valueOf((Integer) v);
                } else if (field.getType().equals(BigDecimal.class) && v instanceof String) {
                    value = new BigDecimal((String) v);
                }
                ReflectionUtils.setField(field, finalExistingUserEntity, value);
            }
        });

        existingUserEntity = userRepository.save(existingUserEntity);
        return userMapper.toUserProfile(existingUserEntity);
    }

    @Override
    public void updateAccountStatus(Long id, AccountStatus newStatus) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userEntity.setAccountStatus(newStatus);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}