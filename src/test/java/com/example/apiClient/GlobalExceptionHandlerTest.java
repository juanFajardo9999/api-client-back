package com.example.apiClient;

import com.example.apiClient.exception.GlobalExceptionHandler;
import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import com.example.apiClient.exception.S3Exception;
import com.example.apiClient.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleClientNotFound() {
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleClientNotFound(
                new ClientNotFoundException("Client not found"));

        assertEquals(404, response.getStatusCodeValue());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Client not found", errorResponse.getError());
        assertEquals("Client not found", errorResponse.getMessage());
    }

    @Test
    public void testHandleInvalidDocumentType() {
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidDocumentType(
                new InvalidDocumentTypeException("Invalid document type"));

        assertEquals(400, response.getStatusCodeValue());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Invalid document type", errorResponse.getError());
        assertEquals("Invalid document type", errorResponse.getMessage());
    }

    @Test
    public void testHandleS3Exception() {
        S3Exception exception = new S3Exception("S3 bucket error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleS3Exception(exception);

        assertEquals(500, response.getStatusCodeValue());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("S3 Error", errorResponse.getError());
        assertEquals("S3 bucket error", errorResponse.getMessage());
    }

    @Test
    public void testHandleGeneralException() {
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(
                new RuntimeException("Unexpected error"));

        assertEquals(500, response.getStatusCodeValue());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Internal server error", errorResponse.getError());
        assertEquals("An unexpected error occurred. Please try again later.", errorResponse.getMessage());
    }
}
