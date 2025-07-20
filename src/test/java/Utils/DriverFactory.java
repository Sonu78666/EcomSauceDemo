package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        WebDriver drv;

        try {
            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions ffOptions = new FirefoxOptions();
                    if (headless) {
                        ffOptions.addArguments("--headless");
                    }
                    drv = new FirefoxDriver(ffOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless=new");
                    }
                    drv = new EdgeDriver(edgeOptions);
                    break;

                case "safari":
                    drv = new SafariDriver();
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
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
                    if (headless) {
                        options.addArguments("--headless=new");
                    }

                    drv = new ChromeDriver(options);
                    break;
            }
        } catch (Exception e) {
            logger.error("Failed to initialize browser '{}'. Falling back to Chrome", browser, e);
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            drv = new ChromeDriver(options);
        }

        driver.set(drv);
        logger.info("Driver initialized for browser: {} (headless: {})", browser, headless);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
