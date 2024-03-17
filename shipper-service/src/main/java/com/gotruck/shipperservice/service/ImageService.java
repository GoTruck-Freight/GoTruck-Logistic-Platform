package com.gotruck.shipperservice.service;

public interface ImageService {
    String getUploadDir();

    void deleteImage(String fileName);

    String getDefaultImageUrl();
}
