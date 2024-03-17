package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.repository.UserRepository;
import com.gotruck.shipperservice.service.ImageService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + userEmail));

        try {
            // Önceki resmi sil
            String oldImage = user.getImage();
            if (oldImage != null) {
                imageService.deleteImage(oldImage);
            }

            // Kullanıcının dosya yüklemesi için bir dosya adı oluştur
            String fileName = file.getOriginalFilename();

            // Dosyayı belirtilen yere kaydet
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Kullanıcının profil resmi alanını güncelle
            user.setImage(fileName);
            userRepository.save(user);

            return ResponseEntity.ok().body("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

}
