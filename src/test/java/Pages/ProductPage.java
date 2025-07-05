package Pages;

import Utils.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductPage {

    private final WebDriver driver;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }
    private final String productAddButtonXpath = "//div[text()='v1']/ancestor::div[@class='inventory_item']//button";
    private final String productNameLabelXpath = "//div[@class='inventory_item_name' and text()='v1']";


    public void addProductToCart(String productName) {
        String xpath = CommonUtils.customizeXpath(productAddButtonXpath, productName);
        driver.findElement(By.xpath(xpath)).click();
        System.out.println("Added product: " + productName);
    }


    public void addMultipleProductsToCart(List<String> productNames) {
        for (String product : productNames) {
            String xpath = CommonUtils.customizeXpath(productAddButtonXpath, product);
            driver.findElement(By.xpath(xpath)).click();
            System.out.println("Added product: " + product);
        }
    }

    // Verify all products are visible on product page (or cart if same DOM)
    public boolean verifyProductsVisible(List<String> productNames) {
        for (String product : productNames) {
            String xpath = CommonUtils.customizeXpath(productNameLabelXpath, product);
            try {
                WebElement productElement = driver.findElement(By.xpath(xpath));
                if (!productElement.isDisplayed()) {
                    System.out.println("Product not visible: " + product);
                    return false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Product not found: " + product);
                return false;
            }
        }
        System.out.println("All products verified successfully");
        return true;
    }


}
