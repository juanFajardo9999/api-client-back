package com.example.apiClient.exception;

public class InvalidDocumentTypeException extends RuntimeException {
    public InvalidDocumentTypeException(String message) {
        super(message);
    }
}