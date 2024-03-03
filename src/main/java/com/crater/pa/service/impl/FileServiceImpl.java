package com.crater.pa.service.impl;

import com.crater.pa.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private String sftpHost;
    private String sftpPort;
    private String sftpUser;
    private String sftpPassword;
    private String sftpPath;

    @Override
    public String uploadFIleToSftp(MultipartFile file) {
        try {
            var fileName = generateRandomFileId();
            uploadToSftp(fileName, file);
            return fileName;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("upload file to sftp have unknown error", e);
        }
    }

    @Override
    public String saveToLocal(MultipartFile file) {
        try {
            var fileName = generateRandomFileId();
            saveFileToLocal(fileName, file);
            return fileName;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("save file to local have unknown error", e);
        }
    }

    @Override
    public String saveToLocal(String base64ImageString) {
        try {
            var fileName = generateRandomFileId();
            saveBase64ToImageFile(fileName, base64ImageString);
            return fileName;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("save file to local have unknown error", e);
        }
    }

    private String generateRandomFileId() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            throw new RuntimeException("generate random file id fail", e);
        }
    }

    private void uploadToSftp(String fileName, MultipartFile file) {
        try {
            var ftpClient = new FTPClient();
            ftpClient.connect(sftpHost, Integer.parseInt(sftpPort));
            ftpClient.login(sftpUser, sftpPassword);
            try (InputStream fis = file.getInputStream()) {
                ftpClient.storeFile(sftpPath + fileName, fis);
            } catch (IOException e) {
                throw new RuntimeException("FileInputStream or FTP storeFile operation failed", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Upload to SFTP failed", e);
        }
    }

    private void saveBase64ToImageFile(String fileName, String base64Image) {
        try {
            var homeDirectory = System.getProperty("user.home");
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            FileUtils.writeByteArrayToFile(new File(homeDirectory + sftpPath + fileName), decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void saveFileToLocal(String fileName, MultipartFile file) {
        try {
            // Get home directory
            String homeDirectory = System.getProperty("user.home");
            // Create a file in the user's home directory
            File outputFile = new File(homeDirectory + sftpPath, fileName);
            // Transfer the multipart file to the newly created file
            file.transferTo(outputFile);
        } catch (Exception e) {
            throw new RuntimeException("save file to local fail", e);
        }
    }

    @Value("${sftp.host}")
    public void setSftpHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }

    @Value("${sftp.port}")
    public void setSftpPort(String sftpPort) {
        this.sftpPort = sftpPort;
    }

    @Value("${sftp.user}")
    public void setSftpUser(String sftpUser) {
        this.sftpUser = sftpUser;
    }

    @Value("${sftp.password}")
    public void setSftpPassword(String sftpPassword) {
        this.sftpPassword = sftpPassword;
    }

    @Value("${sftp.path}")
    public void setSftpPath(String sftpPath) {
        this.sftpPath = sftpPath;
    }
}
