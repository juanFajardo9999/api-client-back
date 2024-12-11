package com.example.apiClient.exception;

import com.example.apiClient.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidDocumentTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDocumentType(InvalidDocumentTypeException ex) {
        logger.warn("Handling InvalidDocumentTypeException: {}", ex.getMessage());
        return buildErrorResponse("Invalid document type", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNotFoundException ex) {
        logger.error("Handling ClientNotFoundException: {}", ex.getMessage());
        return buildErrorResponse("Client not found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<ErrorResponse> handleS3Exception(S3Exception ex) {
        logger.error("Handling S3Exception: {}", ex.getMessage());
        return buildErrorResponse("S3 Error", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "Internal server error",
                "An unexpected error occurred. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String title, String detail, HttpStatus status) {
        logger.info("Building error response: {} - {}", title, detail);
        ErrorResponse errorResponse = new ErrorResponse(title, detail);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
