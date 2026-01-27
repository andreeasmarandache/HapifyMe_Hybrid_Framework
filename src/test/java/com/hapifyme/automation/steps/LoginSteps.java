package com.hapifyme.automation.steps;

import com.codeborne.selenide.Selenide;
import com.hapifyme.automation.api.AuthClient;
import com.hapifyme.automation.config.ConfigManager;
import com.hapifyme.automation.pages.HomePage;
import com.hapifyme.automation.pages.LoginPage;
import com.hapifyme.automation.utils.BrowserUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    // Instanțiem Page Objects
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    AuthClient authClient = new AuthClient();

    @Given("I open the browser")
    public void iOpenTheBrowser() {
        // Selenide deschide browserul automat la prima comandă 'open',
        // dar putem face o deschidere explicită dacă e nevoie.
        // Hooks @Before a configurat deja driverul.
    }

    // --- SCENARIUL 1: UI CLASIC ---

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.open();
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        String email = ConfigManager.getInstance().getString("valid.email");
        // Fallback dacă nu e în config
        if(email == null) email = "student_qa@mail.com";

        String password = ConfigManager.getInstance().getString("valid.password");

        loginPage.login(email, password);
    }

    // --- SCENARIUL 2: HYBRID (API) ---

    @Given("I am logged in via API authentication")
    public void iAmLoggedInViaApiAuthentication() {
        // 1. Deschidem pagina de login pentru a inițializa driverul și contextul
        loginPage.open();

        // 2. Pregătim datele
        String email = ConfigManager.getInstance().getString("valid.email");
        if(email == null) email = "student_qa@mail.com";
        String password = ConfigManager.getInstance().getString("valid.password");

        // 3. Extragem User-Agent real (Tehnica Spoofing din Cap 2)
        String userAgent = BrowserUtils.getUserAgent();

        // 4. Obținem cookie-ul prin API
        System.out.println("Attempting Hybrid Login for: " + email);
        String sessionCookie = authClient.getWebSessionCookie(email, password, userAgent);

        if (sessionCookie == null) {
            throw new RuntimeException("API Login failed! Cookie is null.");
        }

        // 5. Injectăm cookie-ul
        BrowserUtils.injectSessionCookie("PHPSESSID", sessionCookie);

        // 6. Navigăm la pagina protejată (Index)
        String baseUrl = ConfigManager.getInstance().getString("base.url.ui");
        Selenide.open(baseUrl + "/index.php");
    }

    // --- VERIFICĂRI COMUNE ---

    @Then("I should be redirected to the homepage")
    public void iShouldBeRedirectedToTheHomepage() {
        homePage.assertUserIsLoggedIn();
    }
}
