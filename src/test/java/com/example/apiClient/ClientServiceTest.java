package com.example.apiClient;

import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import com.example.apiClient.model.Client;
import com.example.apiClient.service.ClientService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    private final ClientService clientService = new ClientService();

    @Test
    public void testGetClientSuccess() {
        Client client = clientService.getClient("C", "23445322");

        assertNotNull(client);
        assertEquals("Juan", client.getFirstName());
        assertEquals("C", client.getDocumentType());
    }

    @Test
    public void testGetClientNotFound() {
        Exception exception = assertThrows(ClientNotFoundException.class, () -> {
            clientService.getClient("C", "99999999");
        });
        assertEquals("Client not found with document C 99999999", exception.getMessage());
    }

    @Test
    public void testInvalidDocumentType() {
        Exception exception = assertThrows(InvalidDocumentTypeException.class, () -> {
            clientService.getClient("X", "23445322");
        });
        assertEquals("Invalid document type: X", exception.getMessage());
    }
}
