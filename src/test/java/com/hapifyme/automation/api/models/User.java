package com.hapifyme.automation.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generează automat Getteri, Setteri, toString, equals, hashCode
@Builder // Ne permite să creăm obiecte ușor: User.builder().username("...").build()
@AllArgsConstructor // Constructor cu toți parametrii
@NoArgsConstructor // Constructor fără parametri (necesar pentru Jackson)
@JsonIgnoreProperties(ignoreUnknown = true) // Dacă API-ul trimite câmpuri extra pe care nu le știm, le ignorăm (nu crapă testul)
public class User {
    private String username;
    private String password;
    // Câmpuri folosite la răspuns sau înregistrare
    private String email;
    private String id;
    private String token;
}

