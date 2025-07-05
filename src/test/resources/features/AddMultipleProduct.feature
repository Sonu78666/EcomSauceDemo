Feature: Add multiple products to cart

  Background:
    Given User is logged in

  @Product
  Scenario: Add multiple products from test data
    When User adds products to cart for test case "TC_01"
    Then User should see all added products in the cart