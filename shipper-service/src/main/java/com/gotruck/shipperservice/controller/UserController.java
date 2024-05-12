package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.dto.UserDto;
import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserProfile userProfile, Authentication authentication) {
        return userService.updateProfile(userProfile, authentication);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/company/{company_name}")
    public ResponseEntity<List<User>> getUsersByCompanyName(@PathVariable("company_name") String companyName) {
        List<User> users = userRepository.findByCompanyNameIgnoreCaseContaining(companyName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/status/{account_status}")
    public ResponseEntity<List<User>> getUsersByAccountStatus(@PathVariable("account_status") String accountStatus) {
        AccountStatus status = AccountStatus.valueOf(accountStatus.toUpperCase());
        List<User> users = userRepository.findByAccountStatus(status);
        return new ResponseEntity<>(users, HttpStatus.OK);
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