Feature: Login Error Handling

  Scenario: Invalid credentials display an error message
    When User attempts to login with invalid credentials
    Then Login error message should be displayed
