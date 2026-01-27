package com.hapifyme.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager manager;
    private static final Properties prop = new Properties();

    private ConfigManager() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Nu am putut găsi fișierul config.properties", e);
        }
    }

    public static ConfigManager getInstance() {
        if (manager == null) {
            manager = new ConfigManager();
        }
        return manager;
    }

    public String getString(String key) {
        return prop.getProperty(key);
    }
}

