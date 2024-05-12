package com.gotruck.shipperservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void deleteImage(String filePath);

    void deleteUserImage();

    String getDefaultImageUrl();

    void uploadImage(MultipartFile file);
}
