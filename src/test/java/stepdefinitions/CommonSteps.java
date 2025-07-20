package stepdefinitions;

import Pages.LoginPage;
import Utils.DriverFactory;
import Utils.PropertyUtils;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonSteps {

    private static final Logger logger = LoggerFactory.getLogger(CommonSteps.class);





    @Given("User is logged in")
    public void userIsLoggedIn() {
        WebDriver driver = DriverFactory.getDriver();

        String username = PropertyUtils.getValue("username");
        String password = PropertyUtils.getValue("password");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        logger.info("User is logged in successfully");
    }
}
