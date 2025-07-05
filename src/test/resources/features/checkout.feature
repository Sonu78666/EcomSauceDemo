Feature: Checkout Functionality

  Background:
    Given User is logged in

@Checkout
  Scenario: User proceeds to checkout with valid information
    When User adds products to cart for test case "TC_03"
    Then User navigates to the cart page
    When User clicks on Checkout button
    And User enters checkout details for test case "TC_03"
    Then User should be navigated to the Overview page
    Then User validates item total, tax, and total from test case "TC_03"
    When User clicks on Finish button
    Then User should see order success confirmation