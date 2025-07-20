package stepdefinitions;

import Pages.LoginPage;
import Utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginStep {
    private static final Logger logger = LoggerFactory.getLogger(LoginStep.class);
    private final WebDriver driver = DriverFactory.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);

    @When("User attempts to login with invalid credentials")
    public void userAttemptsInvalidLogin() {
        loginPage.login("invalid_user", "invalid_pass");
        logger.info("Attempted login with invalid credentials");
    }

    @Then("Login error message should be displayed")
    public void loginErrorMessageShouldBeDisplayed() {
        Assert.assertTrue("Expected login error was not displayed", loginPage.isErrorMessageDisplayed());
    }
}
