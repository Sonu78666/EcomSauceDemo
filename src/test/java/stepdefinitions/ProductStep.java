package stepdefinitions;

import Pages.ProductPage;
import Utils.DriverFactory;
import Utils.TestDataManger;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductStep {

    private static final Logger logger = LoggerFactory.getLogger(ProductStep.class);

    private WebDriver driver;
    private ProductPage productPage;
    private List<String> productsToAdd;

    public ProductStep() {
        this.driver = DriverFactory.getDriver();
        this.productPage = new ProductPage(driver);
    }


    @When("User adds products to cart for test case {string}")
    public void user_adds_products_to_cart_for_test_case(String testCaseId) {
        productsToAdd= TestDataManger.getListFromJson(testCaseId,"products");
        logger.info("Products fetched for {}: {}", testCaseId, productsToAdd);
        productPage.addMultipleProductsToCart(productsToAdd);

    }
    @Then("User should see all added products in the cart")
    public void user_should_see_all_added_products_in_the_cart() {
        boolean allProductsPresent = productPage.verifyProductsVisible(productsToAdd);
        Assert.assertTrue("One or more products are not visible in the cart",allProductsPresent);
    }
}
