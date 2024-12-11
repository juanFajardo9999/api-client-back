package com.example.apiClient;

import com.example.apiClient.controller.FileController;
import com.example.apiClient.service.S3PublicDownloaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileControllerTest {

    private S3PublicDownloaderService s3PublicDownloaderService;
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        s3PublicDownloaderService = mock(S3PublicDownloaderService.class);
        fileController = new FileController(s3PublicDownloaderService);
    }

    @Test
    public void testDownloadFileSuccess() {
        String bucketName = "test-bucket";
        String objectKey = "test-file.txt";
        Resource mockResource = new ByteArrayResource("Test content".getBytes());

        when(s3PublicDownloaderService.downloadFile(bucketName, objectKey)).thenReturn(mockResource);

        ResponseEntity<Resource> response = fileController.downloadFile(bucketName, objectKey);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("attachment; filename=\"test-file.txt\"", response.getHeaders().get("Content-Disposition").get(0));
        assertEquals(mockResource, response.getBody());
    }

    @Test
    public void testDownloadFileServiceThrowsException() {
        String bucketName = "test-bucket";
        String objectKey = "test-file.txt";

        when(s3PublicDownloaderService.downloadFile(bucketName, objectKey))
                .thenThrow(new RuntimeException("Service error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileController.downloadFile(bucketName, objectKey)
        );
        assertEquals("Service error", exception.getMessage());
    }
}
