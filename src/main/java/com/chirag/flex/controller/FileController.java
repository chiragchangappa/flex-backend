package com.chirag.flex.controller;

import com.chirag.flex.service.CloudinaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    private final CloudinaryService cloudinaryService;

    public FileController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}