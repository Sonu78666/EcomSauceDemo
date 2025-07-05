package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private final By usernameField=By.xpath("//input[@id='user-name']");
    private final By passwordField=By.xpath("//input[@id='password']");
    private final By loginButton=By.xpath("//input[@id='login-button']");
    private final By productTitle = By.xpath("//div[@class='product_label']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username,String password)
    {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
    public boolean isOnProductPage() {

        return driver.findElement(productTitle).isDisplayed();
    }

    public String getProductPageTitle() {
        return driver.findElement(productTitle).getText();
    }
}

