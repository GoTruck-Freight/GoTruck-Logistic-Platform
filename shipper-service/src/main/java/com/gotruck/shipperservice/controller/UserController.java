package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.model.dto.UserDto;
import com.gotruck.shipperservice.model.dto.UserProfile;
import com.gotruck.shipperservice.dao.entity.UserEntity;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import com.gotruck.shipperservice.dao.repository.UserRepository;
import com.gotruck.shipperservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("api/v1/shipper-user")

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserProfile(authentication));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = userService.findOne(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/")
    public UserProfile updateProfile(@Valid @RequestBody UserProfile userProfile, Authentication authentication) {
        return userService.updateProfile(userProfile, authentication);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserProfile> patchUserProfile(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        UserProfile updatedUser = userService.patchUserProfile(id, fields);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/company/{company_name}")
    public ResponseEntity<List<UserEntity>> getUsersByCompanyName(@PathVariable("company_name") String companyName) {
        List<UserEntity> userEntities = userRepository.findByCompanyNameIgnoreCaseContaining(companyName);
        return new ResponseEntity<>(userEntities, HttpStatus.OK);
    }

    @GetMapping("/status/{account_status}")
    public ResponseEntity<List<UserEntity>> getUsersByAccountStatus(@PathVariable("account_status") String accountStatus) {
        AccountStatus status = AccountStatus.valueOf(accountStatus.toUpperCase());
        List<UserEntity> userEntities = userRepository.findByAccountStatus(status);
        return new ResponseEntity<>(userEntities, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/status/")
    public ResponseEntity<String> updateAccountStatus(@PathVariable Long userId, @RequestParam AccountStatus newStatus) {
        userService.updateAccountStatus(userId, newStatus);
        return ResponseEntity.ok().body("Account status updated successfully");
    }
}