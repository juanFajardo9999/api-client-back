package com.example.apiClient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String phone;
    private String address;
    private String city;

    @JsonIgnore
    private String documentType;
    @JsonIgnore
    private int documentNumber;


}