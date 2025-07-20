# EcomSauceDemo

This project contains UI test automation for the [Sauce Demo](https://www.saucedemo.com/) sample e‑commerce site. It uses **Java**, **TestNG** and **Cucumber** with Selenium WebDriver. ExtentReports is used for HTML reporting.

## Project structure

```
src
├── main
│   └── java
│       └── org/example/Main.java        # basic hello world
└── test
    ├── java
    │   ├── Pages                        # Page Object Model classes
    │   ├── Runners                      # Cucumber TestNG runner
    │   ├── Utils                        # driver, config and helpers
    │   └── stepdefinitions             # glue code for features
    └── resources
        ├── Config                       # environment properties
        ├── features                     # Gherkin feature files
        └── testdata                    # JSON test data used in scenarios
```

`pom.xml` declares the dependencies and configures the Maven Surefire plugin so tests can be launched via `mvn test`.

## Running the tests

1. Install Java (15+) and Maven.
2. Select the environment using the `env` system property (defaults to `qa`). Example:

```bash
mvn test -Denv=qa
```

Additional runtime options:

- `-Dbrowser=<chrome|firefox|edge|safari>` &ndash; browser to use (default `chrome`).
- `-Dretry=true -DretryCount=2` &ndash; enable TestNG retry for flaky tests.

After the run an HTML report is created at `reports/ExtentReport.html`.

## Test data

Scenarios read their data from `src/test/resources/testdata/Data.json`:

```json
{
  "TC_01": {
    "products": ["Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"]
  },
  "TC_02": {
    "products": ["Sauce Labs Backpack", "Sauce Labs Bike Light"],
    "prices": { "Sauce Labs Backpack": "29.99", "Sauce Labs Bike Light": "9.99" }
  },
  "TC_03": {
    "products": ["Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt"],
    "firstName": "John",
    "lastName": "Doe",
    "postalCode": "12345",
    "itemTotal": "45.98",
    "tax": "3.68",
    "total": "49.66"
  }
}
```

## Useful classes

- **Pages** implement common actions on the site (login, adding products, cart validation, checkout).
- **stepdefinitions** map Gherkin steps to these page actions.
- **Utils** contains helpers like `DriverFactory` (creates the WebDriver), `PropertyUtils` (loads environment properties) and `TestDataManger` (reads the JSON test data).

With these pieces you can extend the feature files or add new page objects and step definitions for additional scenarios.

