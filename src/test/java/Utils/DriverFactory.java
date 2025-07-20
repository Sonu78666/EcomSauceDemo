package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        WebDriver drv;
        try {
        switch (browser) {
            case "firefox":
                drv = new FirefoxDriver();
                break;
            case "edge":
                drv = new EdgeDriver();
                break;
            case "safari":
                drv = new SafariDriver();
                break;
            case "chrome":
            default:
                ChromeOptions options = new ChromeOptions();

                // ✅ Disable Chrome password manager and autofill prompts
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                // Add arguments to run in incognito and suppress infobars/popups
                options.addArguments("--incognito"); // 💡 disables saved credentials
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-features=AutofillServerCommunication");

                drv = new ChromeDriver(options);
                break;
        }
        } catch (Exception e) {
            logger.error("Failed to initialize browser '{}'. Falling back to Chrome", browser, e);
            ChromeOptions options = new ChromeOptions();
            drv = new ChromeDriver(options);
        }

        driver.set(drv);
        logger.info("Driver initialized for browser: {}", browser);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
