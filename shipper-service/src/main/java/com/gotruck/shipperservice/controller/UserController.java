package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/shipper-user")

public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/getUserProfile")
    public ResponseEntity getUserProfile(Principal principal){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = principal.getName(); // Userin e-posta adresini al
        UserProfile userProfile = (UserProfile) userService.getUserProfile(email);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfile userProfile, Authentication authentication) {
        return userService.updateProfile(userProfile, authentication);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{company_name}")
    public ResponseEntity<List<User>> getUsersByCompanyName(@PathVariable("company_name") String companyName) {
        List<User> users = userRepository.findByCompanyName(companyName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();
            userService.deleteUserByEmail(userEmail);
            return ResponseEntity.ok("Hesabınız uğurla silindi.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Yetkisiz erişim.");
        }
    }
}