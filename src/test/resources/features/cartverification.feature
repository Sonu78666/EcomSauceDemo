Feature: Cart verification

  Background:
    Given User is logged in
@Cart
  Scenario: Verify each item name and price in cart
    When User adds products to cart for test case "TC_02"
    Then User navigates to the cart page
    And User verifies each product name and price in cart for "TC_02"
