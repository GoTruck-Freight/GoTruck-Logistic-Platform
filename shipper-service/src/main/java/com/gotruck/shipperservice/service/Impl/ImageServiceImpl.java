package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.exceptions.UserNotFoundException;
import com.gotruck.shipperservice.dao.entity.UserEntity;
import com.gotruck.shipperservice.dao.repository.UserRepository;
import com.gotruck.shipperservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private  final UserRepository userRepository;

    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${default.image.path}")
    private String defaultImagePath;
    
    @Override
    public void uploadImage(MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return;
            }
            // Generate unique file name
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Invalid file name: " + fileName);
            }
            // Save file to upload directory
            String rootDir = System.getProperty("user.dir");
            Path uploadPath = Paths.get(rootDir, uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Get the authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            // Update user profile image path in the database
            UserEntity userEntity = userRepository.findByEmail(userEmail)
                    .orElseThrow(UserNotFoundException::new);

            // Delete the old image if it exists and is not the default image
            String oldImage = userEntity.getImage();
            if (oldImage != null && !oldImage.equals(defaultImagePath)) {
                deleteImage(oldImage);
            }

            // Convert the path to use forward slashes explicitly
            String imagePath = "/images/profileImages/" + fileName;
            userEntity.setImage(imagePath);
            userRepository.save(userEntity);

            // Return the full file path as the image URL
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }

    //  delete image from the file system
    @Override
    public void deleteImage(String filePath) {
        String rootDir = System.getProperty("user.dir");
        Path fileToDelete = Paths.get(rootDir, filePath);
        try {
            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }

    // delete user profile image from the file system and database
    @Override
    public void deleteUserImage() {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Find the user in the database
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        // Delete the old image if it exists and is not the default image
        String oldImage = userEntity.getImage();
        if (oldImage != null && !oldImage.equals(defaultImagePath)) {
            deleteImage(oldImage);
        }

        // Set the user's image to the default image
        userEntity.setImage(defaultImagePath);

        // Save the updated user to the database
        userRepository.save(userEntity);
    }

    @Override
    public String getDefaultImageUrl() {
        return defaultImagePath;
    }
}
