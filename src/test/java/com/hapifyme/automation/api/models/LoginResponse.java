package com.hapifyme.automation.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private String status;
    private String message;
    private String token; // Token-ul JWT sau API Key
    private User user;    // Obiectul user returnat (imbricat)
}

