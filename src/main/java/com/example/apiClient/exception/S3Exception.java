package com.example.apiClient.exception;


public class S3Exception extends RuntimeException {
    public S3Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public S3Exception(String message) {
        super(message);
    }
}
