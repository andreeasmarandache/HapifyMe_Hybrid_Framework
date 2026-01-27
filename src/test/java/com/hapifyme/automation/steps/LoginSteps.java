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
import org.junit.Assert;

public class LoginSteps {

    // Instanțiem Page Objects
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    AuthClient authClient = new AuthClient();

    @Given("I open the browser")
    public void iOpenTheBrowser() {
        // Browser is managed by Hooks, dar putem face un check aici
    }

    // --- SCENARIUL 1: UI CLASIC ---

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.open();
    }

    @When("I enter valid credentials")
    public void iEnterValidCredentials() {
        String email = ConfigManager.getInstance().getString("valid.email");
        if(email == null) email = "student_qa@mail.com";
        String password = ConfigManager.getInstance().getString("valid.password");

        loginPage.login(email, password);
    }

    // --- SCENARIUL 2: HYBRID (API) ---

    @Given("I am logged in via API authentication")
    public void iAmLoggedInViaApiAuthentication() {
        loginPage.open();
        String email = ConfigManager.getInstance().getString("valid.email");
        if(email == null) email = "student_qa@mail.com";
        String password = ConfigManager.getInstance().getString("valid.password");
        String userAgent = BrowserUtils.getUserAgent();

        String sessionCookie = authClient.getWebSessionCookie(email, password, userAgent);
        if (sessionCookie == null) throw new RuntimeException("API Login failed!");

        BrowserUtils.injectSessionCookie("PHPSESSID", sessionCookie);
        Selenide.open(ConfigManager.getInstance().getString("base.url.ui") + "/index.php");
    }

    // --- SCENARIUL 3: DATA DRIVEN TESTING (NEGATIVE) ---

    @When("I attempt to login with {string} and {string}")
    public void iAttemptToLoginWithAnd(String email, String password) {
        // Folosim parametrii primiți din tabelul Examples din .feature
        loginPage.login(email, password);
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String expectedError) {
        String actualError = loginPage.getErrorMessage();
        // Folosim JUnit Assert pentru verificare
        Assert.assertEquals("Mesajul de eroare nu corespunde!", expectedError, actualError);
    }

    // --- VERIFICĂRI COMUNE ---

    @Then("I should be redirected to the homepage")
    public void iShouldBeRedirectedToTheHomepage() {
        homePage.assertUserIsLoggedIn();
    }
}
