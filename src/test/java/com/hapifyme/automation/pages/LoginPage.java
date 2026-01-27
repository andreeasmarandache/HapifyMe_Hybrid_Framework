package com.hapifyme.automation.pages;

import com.codeborne.selenide.SelenideElement;
import com.hapifyme.automation.config.ConfigManager;
import static com.codeborne.selenide.Condition.visible;
import org.slf4j.Logger;          // Import Logger
import org.slf4j.LoggerFactory;   // Import LoggerFactory

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    
    private final SelenideElement emailInput = $(byName("log_email"));
    private final SelenideElement passwordInput = $(byName("log_password"));
    private final SelenideElement loginButton = $(byName("login_button"));
    private final SelenideElement registerLink = $("#signup");
    private final SelenideElement errorMessage = $(byXpath("//p[contains(text(),'Email or password')]"));

    public void open() {
        String uiUrl = ConfigManager.getInstance().getString("base.url.ui");
        com.codeborne.selenide.Selenide.open(uiUrl + "/login_register.php");
    }

    public void login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public boolean isRegisterLinkVisible() {
        return registerLink.isDisplayed();
    }

    public String getErrorMessage() {
        logger.info("Așteptare mesaj eroare...");
        errorMessage.shouldBe(visible);
        return errorMessage.getText().trim();
    }

}

