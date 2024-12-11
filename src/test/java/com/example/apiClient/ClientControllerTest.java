package com.example.apiClient;


import com.example.apiClient.controller.ClientController;
import com.example.apiClient.exception.ClientNotFoundException;
import com.example.apiClient.exception.InvalidDocumentTypeException;
import com.example.apiClient.model.Client;
import com.example.apiClient.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;


    @Test
    void getClientReturnsClientWhenClientExists() throws Exception {
        String documentType = "C";
        String documentNumber = "23445322";
        Client mockClient = new Client(
                "Juan", "Carlos", "Perez", "Gomez",
                "1234567890", "Calle 123", "Bogotá",
                documentType, Integer.parseInt(documentNumber)
        );

        when(clientService.getClient(documentType, documentNumber)).thenReturn(mockClient);

        mockMvc.perform(get("/api/clients/{documentType}/{documentNumber}", documentType, documentNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.secondName").value("Carlos"))
                .andExpect(jsonPath("$.firstLastName").value("Perez"))
                .andExpect(jsonPath("$.secondLastName").value("Gomez"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("Calle 123"))
                .andExpect(jsonPath("$.city").value("Bogotá"));
    }


    @Test
    void getClientReturnsNotFoundWhenClientDoesNotExist() throws Exception {
        String documentType = "C";
        String documentNumber = "123456789";

        when(clientService.getClient(documentType, documentNumber))
                .thenThrow(new ClientNotFoundException("Client not found"));

        mockMvc.perform(get("/api/clients/{documentType}/{documentNumber}", documentType, documentNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Client not found"))
                .andExpect(jsonPath("$.message").value("Client not found"));
    }

    @Test
    void getClientReturnsBadRequestWhenDocumentTypeIsInvalid() throws Exception {
        String documentType = "INVALID";
        String documentNumber = "123456789";

        when(clientService.getClient(documentType, documentNumber))
                .thenThrow(new InvalidDocumentTypeException("Invalid document type"));

        mockMvc.perform(get("/api/clients/{documentType}/{documentNumber}", documentType, documentNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid document type"))
                .andExpect(jsonPath("$.message").value("Invalid document type"));
    }
}

