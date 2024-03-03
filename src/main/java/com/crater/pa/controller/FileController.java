package com.crater.pa.controller;

import com.crater.pa.bean.response.Error;
import com.crater.pa.bean.response.SaveFileToFtpResponse;
import com.crater.pa.service.FileService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

@RestController
public class FileController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private FileService fileService;

    @PostMapping("/file/upload")
    public SaveFileToFtpResponse saveFileToFtp(@RequestParam("file") MultipartFile file) {
        try {
            var fileId = fileService.saveToLocal(file);
            return new SaveFileToFtpResponse(new Error(true, null, null), fileId);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return new SaveFileToFtpResponse(new Error(false, "file upload fail", e.getMessage()), null);
        }
    }

    @Autowired
    public FileController setFileService(FileService fileService) {
        this.fileService = fileService;
        return this;
    }
}