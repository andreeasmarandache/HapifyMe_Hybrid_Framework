package com.hapifyme.automation.api;

import com.hapifyme.automation.api.models.LoginResponse;
import com.hapifyme.automation.api.models.User;
import com.hapifyme.automation.config.ConfigManager;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class AuthClient {
    private final String apiBaseUrl;
    private final String uiBaseUrl;

    public AuthClient() {
        // Citim URL-urile din fișierul de configurare
        this.apiBaseUrl = ConfigManager.getInstance().getString("base.url.api");
        this.uiBaseUrl = ConfigManager.getInstance().getString("base.url.ui");
    }

    /**
     * Login via API (JSON) - Folosit pentru teste strict de Backend.
     */
    public LoginResponse login(String username, String password) {
        User payload = User.builder()
                .username(username)
                .password(password)
                .build();

        return given()
                .baseUri(apiBaseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .log().ifValidationFails()
                .when()
                .post("/user/login.php")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }

    /**
     * Login via Form Data (Simulare Browser) - Folosit pentru Hybrid Testing.
     * Trimite date de formular (ca browserul) și User-Agent-ul browserului real.
     * Returnează cookie-ul PHPSESSID necesar pentru a fi logat în UI.
     */
    public String getWebSessionCookie(String email, String password, String userAgent) {
        return given()
                .baseUri(uiBaseUrl) // Atacăm URL-ul de UI, nu API
                .contentType("application/x-www-form-urlencoded")
                .header("User-Agent", userAgent) // CRITIC: Ne dăm drept browserul Chrome real!
                .formParam("log_email", email)
                .formParam("log_password", password)
                .formParam("login_button", "Login")
                .redirects().follow(false) // Nu vrem redirect, vrem cookie-ul din răspunsul imediat (302)
                .when()
                .post("/login_register.php")
                .then()
                .statusCode(302) // 302 înseamnă Redirect -> Login cu succes
                .extract()
                .cookie("PHPSESSID"); // Extragem aurul: Cookie-ul de sesiune
    }
}
