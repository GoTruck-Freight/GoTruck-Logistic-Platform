package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${default.image.path}")
    private String defaultImagePath;

    @Override
    public void deleteImage(String fileName) {
        File file = new File(uploadDir + fileName);
        if (file.exists()) {
            boolean deletionResult = file.delete();
            if (deletionResult) {
                // Log success message using a logger
                System.out.println("File deleted successfully: " + fileName);
            } else {
                // Log failure message using a logger
                System.err.println("Failed to delete the file: " + fileName);
            }
        } else {
            // Throw custom exception if file not found
            throw new IllegalArgumentException("File not found: " + fileName);
        }
    }

    @Override
    public String getDefaultImageUrl() {
        return defaultImagePath;
    }

    @Override
    public String getUploadDir() {
        return uploadDir;
    }
}