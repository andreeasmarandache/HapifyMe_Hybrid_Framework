package com.hapifyme.automation.tests;

import com.hapifyme.automation.api.AuthClient;
import com.hapifyme.automation.config.ConfigManager;
import com.hapifyme.automation.pages.HomePage;
import com.hapifyme.automation.pages.LoginPage;
import com.hapifyme.automation.utils.BrowserUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import static com.codeborne.selenide.Selenide.open; // Import pentru navigare

// NOTA: Daca primiti eroarea "Cannot resolve symbol 'Test'", adaugati dependenta JUnit 4 in pom.xml:
// <dependency><groupId>junit</groupId><artifactId>junit</artifactId><version>4.13.2</version><scope>test</scope></dependency>

public class HybridSmokeTest {

    @Test
    public void testLoginClasic() {
        // --- PRE-CONDITIE: Asiguram driverul corect ---
        WebDriverManager.chromedriver().setup();

        System.out.println("--- Start Login Clasic ---");
        LoginPage loginPage = new LoginPage();
        loginPage.open();

        // Citim datele din config
        String email = ConfigManager.getInstance().getString("valid.email");
        if (email == null) email = "student_qa@mail.com";

        String pass = ConfigManager.getInstance().getString("valid.password");

        loginPage.login(email, pass);

        new HomePage().assertUserIsLoggedIn();
    }

    @Test
    public void testLoginHibrid() {
        // --- PRE-CONDITIE: Asiguram driverul corect ---
        WebDriverManager.chromedriver().setup();

        System.out.println("--- Start Login Hibrid (UA Spoofing) ---");

        // 1. Deschidem browserul (necesar pentru a inițializa contextul și a lua User-Agent)
        LoginPage loginPage = new LoginPage();
        loginPage.open();

        // 2. Extragem User-Agent-ul REAL al browserului (Chrome)
        String browserUserAgent = BrowserUtils.getUserAgent();
        System.out.println("Browser User-Agent: " + browserUserAgent);

        // 3. Facem login prin API folosind ACELAȘI User-Agent
        AuthClient api = new AuthClient();

        String email = ConfigManager.getInstance().getString("valid.email");
        if (email == null) email = "student_qa@mail.com";
        String pass = ConfigManager.getInstance().getString("valid.password");

        String sessionCookie = api.getWebSessionCookie(email, pass, browserUserAgent);
        System.out.println("Cookie obtinut prin API: " + sessionCookie);

        if (sessionCookie != null) {
            // 4. Injectam Cookie-ul folosind metoda actualizată din BrowserUtils
            BrowserUtils.injectSessionCookie("PHPSESSID", sessionCookie);

            // 5. Navigam EXPLICIT la index.php pentru a forța browserul să trimită cookie-ul
            String baseUrl = ConfigManager.getInstance().getString("base.url.ui");
            open(baseUrl + "/index.php");

            // 6. Validam
            try {
                new HomePage().assertUserIsLoggedIn();
                System.out.println("Login Hibrid REUSIT!");
            } catch (Exception e) {
                System.out.println("Login Hibrid nereusit (posibil protectii server).");
                // În caz de eșec, aruncăm excepția pentru a marca testul ca picat
                throw e;
            }
        } else {
            throw new RuntimeException("Nu s-a putut obtine cookie-ul prin API. Abort.");
        }
    }
}

