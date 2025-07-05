package stepdefinitions;

import Pages.LoginPage;
import Utils.DriverFactory;
import Utils.PropertyUtils;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class CommonSteps {





    @Given("User is logged in")
    public void userIsLoggedIn() {
        WebDriver driver = DriverFactory.getDriver();

        String username = PropertyUtils.getValue("username");
        String password = PropertyUtils.getValue("password");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        System.out.println("User is logged in successfully");
    }
}
