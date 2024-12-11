package com.example.apiClient.service;

import com.example.apiClient.exception.S3Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3PublicDownloaderService {

    private final S3Client s3Client;
    private static final Logger logger = LoggerFactory.getLogger(S3PublicDownloaderService.class);

    public S3PublicDownloaderService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public Resource downloadFile(String bucketName, String objectKey) {
        try {
            Path tempDir = Files.createTempDirectory("s3-downloads");
            Path filePath = tempDir.resolve(objectKey);
            logger.info("Requesting object '{}' from bucket '{}'", objectKey, bucketName);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.getObject(getObjectRequest, filePath);

            logger.info("Successfully downloaded object '{}' from bucket '{}'", objectKey, bucketName);
            return new FileSystemResource(filePath);
        } catch (NoSuchBucketException e) {
            logger.error("Bucket does not exist: {}", bucketName);
            throw new S3Exception("Bucket " + bucketName + " does not exist or is not accessible.", e);
        } catch (NoSuchKeyException e) {
            logger.error("Object does not exist in bucket '{}': {}", bucketName, objectKey);
            throw new S3Exception("Object " + objectKey + " does not exist in bucket " + bucketName, e);
        } catch (SdkException | IOException e) {
            logger.error("AWS SDK Exception or IO Error: {}", e.getMessage(), e);
            throw new S3Exception("Error downloading file from S3", e);
        }
    }
}