package com.hapifyme.automation.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.hapifyme.automation.config.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

    @Before
    public void setup() {
        // 1. Configurare Driver (Fix pentru versiuni)
        WebDriverManager.chromedriver().setup();

        // 2. Configurare Selenide
        Configuration.browser = ConfigManager.getInstance().getString("browser");

        // LOGICĂ NOUĂ PENTRU CI/CD:
        // Verificăm dacă există proprietatea 'headless' setată din linia de comandă (ex: mvn test -Dheadless=true)
        // Dacă da, folosim acea valoare. Dacă nu, citim din fișierul de configurare.
        String headlessProp = System.getProperty("headless");
        if(headlessProp != null) {
            Configuration.headless = Boolean.parseBoolean(headlessProp);
        } else {
            Configuration.headless = Boolean.parseBoolean(ConfigManager.getInstance().getString("headless"));
        }

        Configuration.timeout = Long.parseLong(ConfigManager.getInstance().getString("timeout"));

        // Optimizare: Maximizare fereastra la start (pentru consistență vizuală în screenshot-uri)
        Configuration.browserSize = "1920x1080";
    }

    @After
    public void tearDown() {
        // Închidem browserul după fiecare scenariu pentru a avea un mediu curat
        // Selenide face asta automat de obicei, dar e bine să fim expliciți în BDD
        Selenide.closeWebDriver();
    }
}


