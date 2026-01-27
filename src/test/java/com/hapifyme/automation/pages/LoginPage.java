package com.hapifyme.automation.pages;

import com.codeborne.selenide.SelenideElement;
import com.hapifyme.automation.config.ConfigManager;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement emailInput = $(byName("log_email"));
    private final SelenideElement passwordInput = $(byName("log_password"));
    private final SelenideElement loginButton = $(byName("login_button"));
    private final SelenideElement registerLink = $("#signup");

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
}

