package stepdefinitions;

import Pages.LoginPage;
import Utils.DriverFactory;
import Utils.ExtentCucumberAdapter;
import Utils.ExtentManager;
import Utils.PropertyUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Hooks {

    private static ExtentReports extent;

    @BeforeAll
    public static void initReport() {
        extent = ExtentManager.getInstance();
    }
    @Before
    public void setup(Scenario scenario) {
        System.out.println("Hook is running");
        DriverFactory.setDriver();
        //System.out.println("Driver initialized in Hooks: " + DriverFactory.getDriver());
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        String url = PropertyUtils.getValue("base.url");
        driver.get(url);
        ExtentTest test = extent.createTest(scenario.getName());
        ExtentCucumberAdapter.setTest(test);

    }

    @AfterStep
    public void captureFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
            ExtentCucumberAdapter.getTest().fail("Step Failed").addScreenCaptureFromBase64String(
                    ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BASE64));
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentCucumberAdapter.getTest().fail("Scenario failed: " + scenario.getName());
        } else {
            ExtentCucumberAdapter.getTest().pass("Scenario passed: " + scenario.getName());
        }

        // Add scenario tags
        for (String tag : scenario.getSourceTagNames()) {
            ExtentCucumberAdapter.getTest().assignCategory(tag.replace("@", ""));
        }
        DriverFactory.quitDriver();
    }
    @AfterAll
    public static void flushReport() {
        extent.flush();
    }


}
