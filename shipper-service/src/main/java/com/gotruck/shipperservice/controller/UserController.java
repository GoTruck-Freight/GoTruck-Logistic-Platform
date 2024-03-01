package com.gotruck.shipperservice.controller;


import com.gotruck.shipperservice.dto.UserProfile;
import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.AuthService;
import com.gotruck.shipperservice.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


//@Slf4j
//@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shipper-user")

public class UserController {
    @GetMapping()
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("HI USER");
    }


    @Autowired
    private UserService userService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private final AuthService authService;

    @GetMapping("/getUserProfile")
    public ResponseEntity getUserProfile(Principal principal){
        if (principal == null) {
            // Eğer kullanıcı kimliği alınamazsa, yetkisiz erişim hatası döndür
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = principal.getName(); // Kullanıcının e-posta adresini al

        // Kullanıcının e-posta adresiyle profil bilgilerini al ve döndür
        UserProfile userProfile = (UserProfile) userService.getUserProfile(email);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            // Profil bulunamazsa 404 Not Found hatası döndür
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
}


//    @RolesAllowed({"USER","ADMIN"})
//    @GetMapping("/getUserProfile")
//    public ResponseEntity getUserProfile(Principal principal){
//        return ResponseEntity.status(HttpStatus.OK).body(UserService.getProfile(principal));
//    }

//@GetMapping("/profile")
//public <UserProfile> ResponseEntity<UserProfile> getUserProfile() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String userEmail = authentication.getName();
//    UserProfile userProfile = userService.getUserProfile(userEmail);
//    return ResponseEntity.status(HttpStatus.OK).body(userProfile);
//}




//@GetMapping("/client/getUserId")
//public ResponseEntity<?> getUserId(@RequestParam String email) {
//    return ResponseEntity.status(HttpStatus.OK).body(UserService.getIdByEmail(email));
//}
//
//@RolesAllowed({"ROLE_MEMBER", "ROLE_ADMIN", "ROLE_PARTNER", "ROLE_SYSTEMADMIN"})
//@GetMapping("/profile/getUserProfile")
//public ResponseEntity getUserProfile(Principal principal) {
//    return ResponseEntity.status(HttpStatus.OK).body(userService.getProfile(principal));
//}
//
//@RolesAllowed({"ROLE_MEMBER", "ROLE_ADMIN", "ROLE_PARTNER", "ROLE_SYSTEMADMIN"})
//@PostMapping("/profile/updateProfile")
//public ResponseEntity updateProfile(Principal principal, @RequestBody User user) {
//    return ResponseEntity.status(HttpStatus.OK).body(userService.updateProfile(principal, user));
////}
//@RolesAllowed({"ROLE_MEMBER", "ROLE_ADMIN", "ROLE_PARTNER", "ROLE_SYSTEMADMIN"})
//@GetMapping("/getAllUser")
//public ResponseEntity getAllUser(Principal principal) {
//    return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(principal));
//}

