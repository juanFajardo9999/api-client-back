package com.example.apiClient.controller;

import com.example.apiClient.model.Client;
import com.example.apiClient.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{documentType}/{documentNumber}")
    public ResponseEntity<Client> getClient(
            @PathVariable String documentType,
            @PathVariable String documentNumber) {
        logger.info("Fetching client with documentType={} and documentNumber={}", documentType, documentNumber);
        Client client = clientService.getClient(documentType, documentNumber);
        return ResponseEntity.ok(client);
    }
}

