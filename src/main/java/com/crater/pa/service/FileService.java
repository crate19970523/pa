package com.crater.pa.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    String uploadFIleToSftp(MultipartFile file);
    String saveToLocal(MultipartFile file);
    String saveToLocal(String base64ImageString);
}
