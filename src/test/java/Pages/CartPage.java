package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private final String cartItemNameXpath = "//div[@class='cart_item']//div[@class='inventory_item_name']";
    private final String cartItemPriceXpath = "//div[@class='cart_item']//div[@class='inventory_item_price']";
    private final By cartIcon = By.xpath("//a[contains(@class, 'shopping_cart_link')]");
    public void clickCartIcon()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        icon.click();
        logger.info("Clicked on cart icon.");
    }

    public Map<String, Double> getCartItemsWithPrices() {
        List<WebElement> names = driver.findElements(By.xpath(cartItemNameXpath));
        List<WebElement> prices = driver.findElements(By.xpath(cartItemPriceXpath));

        Map<String, Double> cartData = new HashMap<>();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i).getText().trim();
            String priceText = prices.get(i).getText().replace("$", "").trim();
            double price = Double.parseDouble(priceText);
            cartData.put(name, price);
        }
        return cartData;
    }
    public boolean isCartDataMatching(Map<String, Double> expectedData) {
            Map<String, Double> actualData = getCartItemsWithPrices();

            if (actualData.size() != expectedData.size()) {
                logger.warn("Mismatch in item count");
                return false;
            }

            for (Map.Entry<String, Double> entry : expectedData.entrySet()) {
                String expectedProduct = entry.getKey();
                Double expectedPrice = entry.getValue();

                if (!actualData.containsKey(expectedProduct)) {
                    logger.warn("Missing product: {}", expectedProduct);
                    return false;
                }

                if (!actualData.get(expectedProduct).equals(expectedPrice)) {
                    logger.warn("Price mismatch for {} — Expected: {}, Found: {}", expectedProduct, expectedPrice, actualData.get(expectedProduct));
                    return false;
                }
            }

            logger.info("Cart contents matched with expected data.");
            return true;
        }
}
