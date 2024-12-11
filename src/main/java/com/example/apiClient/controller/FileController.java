package com.example.apiClient.controller;

import com.example.apiClient.service.S3PublicDownloaderService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final S3PublicDownloaderService s3PublicDownloaderService;

    public FileController(S3PublicDownloaderService s3PublicDownloaderService) {
        this.s3PublicDownloaderService = s3PublicDownloaderService;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(
            @RequestParam String bucketName,
            @RequestParam String objectKey) {

        Resource fileResource = s3PublicDownloaderService.downloadFile(bucketName, objectKey);


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                //.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + objectKey + "\"")
                .body(fileResource);
    }
}
