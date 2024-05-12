package com.gotruck.shipperservice.controller;

import com.gotruck.shipperservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    private final ImageService imageService;
    @Autowired
    public FileUploadController(ImageService imageService){
        this.imageService = imageService;
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        imageService.uploadImage(file);
        return ResponseEntity.ok().body("Image added successfully");
    }

    @DeleteMapping("/deleteImage")
    public ResponseEntity<String> deleteImage() {
        imageService.deleteUserImage();
        return ResponseEntity.ok().body("Image deleted successfully");
    }
}