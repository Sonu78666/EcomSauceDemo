package stepdefinitions;

import Pages.CartPage;
import Pages.ProductPage;
import Utils.DriverFactory;
import Utils.TestDataManger;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartStep {

    private static final Logger logger = LoggerFactory.getLogger(CartStep.class);

    private final WebDriver driver = DriverFactory.getDriver();
    private final ProductPage productPage=new ProductPage(driver);
    private final CartPage cartPage = new CartPage(driver);



    @Then("User navigates to the cart page")
    public void user_navigates_to_the_cart_page() {
        cartPage.clickCartIcon();
        logger.info("User is on cart page");
    }
    @Then("User verifies each product name and price in cart for {string}")
    public void user_verifies_each_product_name_and_price_in_cart_for(String testCaseId) {
        Map<String, Object> rawData = TestDataManger.getMapFromJson(testCaseId, "prices");
        Map<String, Double> expectedData = new HashMap<>();
        for (Map.Entry<String, Object> entry : rawData.entrySet()) {
            expectedData.put(entry.getKey(), Double.parseDouble(entry.getValue().toString()));
        }

        boolean result = cartPage.isCartDataMatching(expectedData);
        Assert.assertTrue("Cart validation failed", result);

    }
}
