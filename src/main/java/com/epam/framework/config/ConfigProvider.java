package com.epam.framework.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

    private static final Logger log = LogManager.getLogger(ConfigProvider.class);
    private static ConfigProvider instance;
    private final Properties properties = new Properties();

    private ConfigProvider() {
        String env = System.getProperty("env", "qa");
        String configFile = "config/" + env + ".properties";
        log.info("Loading configuration for environment: [{}] from file: [{}]", env, configFile);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (is == null) {
                log.error("Config file not found: {}", configFile);
                throw new RuntimeException("Config file not found: " + configFile);
            }
            properties.load(is);
            log.debug("Configuration loaded successfully. Keys: {}", properties.keySet());
        } catch (IOException e) {
            log.error("Failed to load config file: {}", configFile, e);
            throw new RuntimeException("Failed to load config file: " + configFile, e);
        }
    }

    public static ConfigProvider getInstance() {
        if (instance == null) {
            instance = new ConfigProvider();
        }
        return instance;
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property not found for key: [{}]", key);
        }
        return value;
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public String getBaseUrl() {
        return get("base.url");
    }

    public String getStandardUser() {
        return get("standard.user");
    }

    public String getPassword() {
        return get("password");
    }

    public int getImplicitWait() {
        return getInt("implicit.wait");
    }

    public int getExplicitWait() {
        return getInt("explicit.wait");
    }

    public int getPageLoadTimeout() {
        return getInt("page.load.timeout");
    }
}
