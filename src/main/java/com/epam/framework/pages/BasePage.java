package com.epam.framework.pages;

import com.epam.framework.config.ConfigProvider;
import com.epam.framework.core.DriverManager;
import com.epam.framework.utils.HighlightUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger log = LogManager.getLogger(getClass());

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        int explicitWait = ConfigProvider.getInstance().getExplicitWait();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        PageFactory.initElements(driver, this);
        log.debug("Page initialised: [{}]", getClass().getSimpleName());
    }

    /**
     * Highlights the element, waits until it's clickable, then clicks it via JavaScript
     * to ensure React synthetic events are reliably triggered.
     */
    protected void click(WebElement element) {
        log.debug("ACTION click — element: [{}]", element.getTagName());
        wait.until(ExpectedConditions.elementToBeClickable(element));
        HighlightUtils.highlight(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Highlights and types into an input field.
     * Uses the native HTMLInputElement value setter to reliably update
     * React-controlled inputs, then dispatches an 'input' event so React
     * picks up the new value.
     */
    protected void type(WebElement element, String text) {
        log.debug("ACTION type — text: [{}]", text);
        wait.until(ExpectedConditions.visibilityOf(element));
        HighlightUtils.highlight(element);
        ((JavascriptExecutor) driver).executeScript(
            "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(" +
                "window.HTMLInputElement.prototype, 'value').set;" +
            "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
            element, text);
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        String text = element.getText();
        log.debug("getText — value: [{}]", text);
        return text;
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
