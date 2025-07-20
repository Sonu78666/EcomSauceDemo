package Pages;

import Utils.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPage.class);
    private final WebDriver driver;
    private final By checkoutButton = By.xpath("//a[text()='CHECKOUT']");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.xpath("//input[@value='CONTINUE']");
    private final By overviewHeader = By.xpath("//div[contains(text(),'Checkout')]");
    private final By finishButton = By.xpath("//a[text()='FINISH']");
    private final By successHeader = By.xpath("//h2[@class='complete-header']");
    private final By successText = By.xpath("//div[@class='complete-text']");


    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }



    public void clickCheckoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        btn.click();
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        driver.findElement(continueButton).click();
        logger.info("Checkout information submitted");
    }

    public boolean isOnOverviewPage() {
        return driver.findElement(overviewHeader).isDisplayed();
    }
    public Map<String, Double> getOverviewPriceDetails() {
        Map<String, Double> overviewData = new HashMap<>();
        overviewData.put("itemTotal", extractPriceByLabel("Item total:"));
        overviewData.put("tax", extractPriceByLabel("Tax:"));
        overviewData.put("total", extractPriceByLabel("Total:"));
        return overviewData;
    }

    private double extractPriceByLabel(String labelText) {
        //String xpath=CommonUtils.customizeXpath("//*[contains(text(),'v1')]",labelText);
        String xpath = String.format("//*[contains(text(),'%s')]", labelText);
        String fullText = driver.findElement(By.xpath(xpath)).getText();
        String value = fullText.split("\\$")[1].trim();
        return Double.parseDouble(value);
    }
    public void clickFinishButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        logger.info("Clicked on Finish button.");
    }

    public boolean isOrderSuccessDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String header = wait.until(ExpectedConditions.visibilityOfElementLocated(successHeader)).getText().trim();
        String text = driver.findElement(successText).getText().trim();
        return header.equals("THANK YOU FOR YOUR ORDER") && text.contains("Your order has been dispatched");
    }
}
