package com.epam.framework.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {}

    public static WebDriver createDriver(String browser) {
        log.info("Creating WebDriver for browser: [{}]", browser);

        return switch (browser.toLowerCase().trim()) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            case "edge" -> createEdgeDriver();
            case "chrome-headless" -> createChromeHeadlessDriver();
            default -> {
                log.warn("Unknown browser [{}]. Defaulting to Chrome.", browser);
                yield createChromeDriver();
            }
        };
    }

    private static WebDriver createChromeDriver() {
        log.debug("Setting up ChromeDriver via Selenium Manager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        log.info("ChromeDriver created successfully");
        return new ChromeDriver(options);
    }

    private static WebDriver createChromeHeadlessDriver() {
        log.debug("Setting up Chrome headless driver via Selenium Manager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        log.info("Chrome headless driver created successfully");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        log.debug("Setting up FirefoxDriver via Selenium Manager");
        FirefoxOptions options = new FirefoxOptions();
        log.info("FirefoxDriver created successfully");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        log.debug("Setting up EdgeDriver via Selenium Manager");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        log.info("EdgeDriver created successfully");
        return new EdgeDriver(options);
    }
}
