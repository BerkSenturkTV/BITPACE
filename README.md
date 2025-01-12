
# Trendyol Test Automation

This project is designed to perform automated tests on the Trendyol website. It uses tools such as **Cucumber**, **Selenium WebDriver**, and **ExtentReports** to perform these tests. The tests are conducted to verify various functionalities of Trendyol, test user interactions, and assess the website's performance.

---

## Requirements

To ensure the proper functioning of this project, the following software must be installed:

1. **Java 11 or newer**
2. **Maven** (for dependency management and project configuration)
3. **Chrome WebDriver** (must be located in the `drivers/chromedriver.exe` file within the project directory)

---

## Project Structure

```
Trendyol
│
├── drivers/                  # WebDriver file is located here
│   └── chromedriver.exe      # Chrome WebDriver
│
├── src/
│   ├── main/                 
│   │   ├── java/ 
│   │   │   ├── com.trendyol/  # Main package
│   │   │   │   ├── utils/    # Helper tools (DriverManager, ExtentReportManager)
│   │   │   │   └── com/      # Test classes
│   │   └── resources/        # Resource files
│   │
│   ├── test/
│   │   ├── java/            
│   │   │   ├── hooks/        # Hooks class (Operations before and after test)
│   │   │   ├── runners/      # Test runners
│   │   │   ├── stepdefinitions/ # Defines test steps
│   │   │   └── TestRunner.java  # Main test execution file
│   │   ├── resources/        # Feature files (Cucumber test files)
│   │   │   └── features/     # Feature files for tests
│   │   │       └── TrendyolTest.feature
│   └── target/               # Compiled files and reports
│       └── ExtentReports/    # Test reports
│
├── .gitignore                # Git ignore file
├── pom.xml                   # Maven configuration file
└── README.md                 # This file
```

---

## Running the Tests

### 1. Install Dependencies

First, run the following command to install the Maven dependencies:

```bash
mvn clean install
```

### 2. Run the Tests

To start the tests, use the following command:

```bash
mvn test
```

This command will execute the Cucumber scenarios and save the results in the `TrendyolTestReport.html` report file.

### 3. View the Test Report

After the tests have completed, the report will be saved as `TrendyolTestReport.html` in the main project directory. You can open this file in a browser to view the test results.

---

## Test Structure

The tests are based on Cucumber-written `.feature` files and corresponding StepDefinitions classes. Additionally, certain Hooks and JUnit configurations are used to ensure the tests run smoothly.

### 1. Test Scenarios

The tests are defined in the Cucumber feature file located at `src/test/resources/features/TrendyolTest.feature`. Each scenario in this file is written to verify a specific functionality on the website. Various scenarios in this file validate user interactions and results.

### 2. Hooks

The setup and teardown processes for the tests are defined in the `Hooks` class. Reporting is started when the test begins and stopped once the test ends.

### 3. Reporting

The test results are reported using **ExtentReports**. The reports are stored in the `target/ExtentReports` folder and can be viewed in HTML format.

---

## Technologies Used

- **Selenium WebDriver**: Used to interact with web browsers.
- **Cucumber**: A tool that facilitates behavior-driven testing.
- **ExtentReports**: Used to generate visual reports for tests.
- **Maven**: Used for dependency management and project configuration.
- **JUnit**: Used as the test framework.

---

## Developer Notes

- **WebDriver Update**: This project uses the Chrome WebDriver. You may need to update the WebDriver by replacing the `drivers/chromedriver.exe` file if a new version of Chrome is released.
- **Other Browser Support**: If you want to use a different browser, you can download and use the appropriate WebDriver.

---

## Test Scenarios

### Scenario 1: Verify Product Details and Add to Cart

1. **Open Trendyol Website**
   - Navigate to the Trendyol homepage.
   - Ensure the page is fully loaded.

2. **Search and Verify Results**
   - Search for the keyword "Kablosuz kulaklık".
   - Verify the search results are displayed correctly.
   - Ensure that "Kablosuz" or "Kulaklık" appears in the product descriptions or titles.

3. **Select a Random Product**
   - Choose a product that contains "Kablosuz" or "Kulaklık".
   - Record the product's name and price.
   - Verify that the product details are visible.

4. **View All Features of the Product**
   - Click on the button to show all product features.
   - Verify that all features of the product are displayed correctly.

5. **Add Product to Cart**
   - Add the product to the cart.
   - Ensure the product was added to the cart successfully.

6. **Verify Product in Cart**
   - Go to the cart.
   - Verify that the product name matches the added product.

7. **Verify Total Price**
   - Verify that the total price in the cart matches the sum of individual product prices.
   - If the prices do not match, a failure message should be displayed.

### Scenario 2: Increase Product Quantity and Remove Product from Cart

1. **Increase Product Quantity to 2**
   - Increase the quantity of the product to 2 in the cart.
   - Verify that the total price updates correctly.

2. **Search for a New Product**
   - Search for the keyword "Sony kamera".
   - Verify the search results are displayed correctly.
   - Ensure that "sony" or "kamera" appears in the product descriptions or titles.

3. **Select a New Product and Close Old Tab**
   - Select a random product from the search results.
   - Switch to the new tab and close the old one.

4. **Verify Product Details**
   - Record the product's name and price.
   - Verify that the product's name and price are displayed correctly.

5. **Add New Product to Cart**
   - Add the selected product to the cart.
   - Ensure the product was added successfully.

6. **Verify Product in Cart**
   - Go to the cart.
   - Verify that the product name matches the added product.

7. **Verify Total Price**
   - Verify that the total price in the cart matches the sum of individual product prices.

8. **Remove Product from Cart**
   - Click the "Remove" button to remove the product from the cart.
   - Ensure that the product was removed and the total price is updated.

9. **Recheck Total Price**
   - Verify that the remaining product's total price matches the sum of individual product prices.

---

## Configuring the Test Environment

### Java Home Configuration
Ensure that the `JAVA_HOME` environment variable is set correctly.

### WebDriver Configuration
Since **WebDriver Manager** is used, no manual WebDriver installation is required. However, if you want to manually set up the WebDriver for a specific browser, the `WebDriverManager` class in the project can be used.

---

## Adding New Test Scenarios

To add a new test scenario:
1. Edit or add a new `.feature` file in the `src/test/resources/features` folder. You can use Cucumber's Gherkin syntax to write the scenarios.
2. Add the corresponding test steps in the `StepDefinitions.java` file.

---

## Debugging and Logging

You can use **ExtentReports** logs to review errors encountered during test execution. After running the tests, you can trace errors in the detailed report generated.
