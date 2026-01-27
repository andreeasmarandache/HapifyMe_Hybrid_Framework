package com.hapifyme.automation.utils;

import com.codeborne.selenide.Selenide;
import com.hapifyme.automation.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestSetup {
    public static void main(String[] args) {
        // 1. Testam Config Reader
        String url = ConfigManager.getInstance().getString("base.url.ui");
        System.out.println("URL-ul testat este: " + url);

        // 1.1 Configurare WebDriverManager ca fallback/fix
        WebDriverManager.chromedriver().setup();

        // 2. Testam Browser-ul (Selenide)
        // Selenide va deschide automat Chrome si va inchide la final
        Selenide.open(url);

        // Verificare vizuala
        System.out.println("Titlul paginii este: " + Selenide.title());

        // Daca vedeti pagina de Login HapifyMe, setup-ul este CORECT!
    }
}
