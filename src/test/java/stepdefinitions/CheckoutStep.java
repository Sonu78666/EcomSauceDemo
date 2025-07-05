package stepdefinitions;

import Pages.CheckoutPage;
import Utils.DriverFactory;
import Utils.TestDataManger;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class CheckoutStep {

    private final WebDriver driver = DriverFactory.getDriver();
    private final CheckoutPage checkoutPage = new CheckoutPage(driver);

    @When("User clicks on Checkout button")
    public void user_clicks_on_checkout_button() {

        checkoutPage.clickCheckoutButton();

    }
    @When("User enters checkout details for test case {string}")
    public void user_enters_checkout_details_for_test_case(String testCaseId) {

        String firstName = TestDataManger.getValue(testCaseId, "firstName");
        String lastName = TestDataManger.getValue(testCaseId, "lastName");
        String postalCode = TestDataManger.getValue(testCaseId, "postalCode");

        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);

    }
    @Then("User should be navigated to the Overview page")
    public void user_should_be_navigated_to_the_overview_page() {

        Assert.assertTrue("User is not on the overview page", checkoutPage.isOnOverviewPage());
    }

    @Then("User validates item total, tax, and total from test case {string}")
    public void userValidatesItemTotalTaxAndTotalFromTestCase(String testCaseId) {

        Map<String, Object> data = TestDataManger.getMapFromJson(testCaseId);
        double expectedItemTotal = Double.parseDouble(data.get("itemTotal").toString());
        double expectedTax = Double.parseDouble(data.get("tax").toString());
        double expectedTotal = Double.parseDouble(data.get("total").toString());

        Map<String, Double> actual = checkoutPage.getOverviewPriceDetails();

        Assert.assertEquals(expectedItemTotal, actual.get("itemTotal"), 0.01);
        Assert.assertEquals(expectedTax, actual.get("tax"), 0.01);
        Assert.assertEquals(expectedTotal, actual.get("total"), 0.01);

    }

    @When("User clicks on Finish button")
    public void userClicksOnFinishButton() {
        checkoutPage.clickFinishButton();
    }

    @Then("User should see order success confirmation")
    public void userShouldSeeOrderSuccessConfirmation() {

        Assert.assertTrue("Order success message not displayed", checkoutPage.isOrderSuccessDisplayed());
    }
}
