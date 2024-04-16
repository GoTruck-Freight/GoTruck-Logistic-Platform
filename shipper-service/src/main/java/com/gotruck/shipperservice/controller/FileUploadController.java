package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.ImageService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    @Value("${upload.dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final ImageService imageService;
    @Autowired
    public FileUploadController(UserRepository userRepository, ImageService imageService){
        this.userRepository = userRepository;
        this.imageService = imageService;
    }


    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + userEmail));

        try {
            // Previous image deletion
            String oldImage = user.getImage();
            if (oldImage != null) {
                imageService.deleteImage(oldImage);
            }

            // Create a file name for the user's file upload
            String fileName = file.getOriginalFilename();

            // Save the file to the specified location
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Update the user's profile image field
            user.setImage(fileName);
            userRepository.save(user);


            return ResponseEntity.ok().body("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

}