package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${default.image.path}")
    private String defaultImagePath;

    @Override
    public String getDefaultImageUrl() {
        return defaultImagePath;
    }

    @Override
    public String getUploadDir() {
        return uploadDir;
    }
}