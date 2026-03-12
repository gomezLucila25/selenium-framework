package com.epam.framework.pages;

import com.epam.framework.config.ConfigProvider;
import com.epam.framework.core.DriverManager;
import com.epam.framework.utils.HighlightUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
     * Highlights the element, waits until it's clickable, then clicks it.
     */
    protected void click(WebElement element) {
        log.debug("ACTION click — element: [{}]", element.getTagName());
        HighlightUtils.highlight(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Highlights, clears, then types into an input field.
     */
    protected void type(WebElement element, String text) {
        log.debug("ACTION type — text: [{}]", text);
        HighlightUtils.highlight(element);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
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
