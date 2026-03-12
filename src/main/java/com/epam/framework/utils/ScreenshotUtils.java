package com.epam.framework.utils;

import com.epam.framework.core.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtils() {}

    /**
     * Captures a screenshot and saves it to target/screenshots/.
     * The path is logged so CI can archive it as an artifact.
     *
     * @param testName the name of the failed test (used in filename)
     * @return the absolute path of the saved screenshot
     */
    public static String takeScreenshot(String testName) {
        log.debug("Attempting to capture screenshot for test: [{}]", testName);

        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(FORMATTER);
            String filename = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";
            File destination = new File(filename);

            FileUtils.copyFile(source, destination);

            // This INFO log is the required "log should have info about the saved screenshot"
            log.info("SCREENSHOT SAVED — test: [{}], path: [{}]", testName,
                    destination.getAbsolutePath());
            return destination.getAbsolutePath();

        } catch (IOException e) {
            log.error("Failed to save screenshot for test: [{}]", testName, e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error during screenshot capture for test: [{}]", testName, e);
            return null;
        }
    }
}
