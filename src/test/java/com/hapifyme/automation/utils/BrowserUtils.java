package com.hapifyme.automation.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import java.net.URI;

public class BrowserUtils {

    /**
     * Injectează Cookie-ul de sesiune în browser, configurând corect domeniul.
     */
    public static void injectSessionCookie(String cookieName, String cookieValue) {
        // 1. Ștergem cookie-ul existent (dacă există)
        WebDriverRunner.getWebDriver().manage().deleteCookieNamed(cookieName);

        // 2. Determinăm domeniul curent din URL-ul browserului
        // Ex: din "[https://test.hapifyme.com/login](https://test.hapifyme.com/login)" extragem "test.hapifyme.com"
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        String domain;
        try {
            domain = new URI(currentUrl).getHost();
        } catch (Exception e) {
            domain = "test.hapifyme.com"; // Fallback
        }

        // 3. Creăm cookie-ul cu parametrii corecți
        Cookie cookie = new Cookie.Builder(cookieName, cookieValue)
                .domain(domain) // Setăm explicit domeniul!
                .path("/")      // Valabil pe tot site-ul
                .isSecure(true) // Site-ul este HTTPS, deci cookie-ul trebuie să fie Secure
                .build();

        // 4. Îl adăugăm
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
    }

    /**
     * Returnează User-Agent-ul browserului curent.
     */
    public static String getUserAgent() {
        return (String) ((org.openqa.selenium.JavascriptExecutor) WebDriverRunner.getWebDriver())
                .executeScript("return navigator.userAgent;");
    }
}
