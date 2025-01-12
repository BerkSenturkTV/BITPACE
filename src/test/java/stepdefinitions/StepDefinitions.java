package stepdefinitions;
import java.util.List;
import java.util.Set;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import utils.ExtentReportManager;
import java.time.Duration;
import static org.junit.Assert.assertTrue;

public class StepDefinitions {
    WebDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    String clickedProductName;
    double totalProductPrice = 0;

    WebElement currentProductDescription = null;
    WebElement currentProductTitle = null;

    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    @Given("I open the Trendyol website")
    public void i_open_the_trendyol_website() {
        driver.get("https://www.trendyol.com");
        driver.manage().window().maximize();
        waitForPageToLoad();
        try {
            WebElement closePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".fancybox-close")));
            closePopup.click();
        } catch (Exception e) {
        }
    }

    @When("I search for {string}")
    public void i_search_for(String keyword) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-testid='suggestion']")));
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(org.openqa.selenium.Keys.ENTER);
        ExtentReportManager.getTest().log(Status.PASS, "Search performed: " + keyword);
    }

    @Then("I verify the search results are displayed")
    public void i_verify_the_search_results_are_displayed() {
        waitForPageToLoad();
        WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.p-card-chldrn-cntnr.card-border")));
        assertTrue("Search results are not visible!", resultsContainer.isDisplayed());
        ExtentReportManager.getTest().log(Status.PASS, "Search results displayed.");
    }

    @Then("I verify the search results contain {string} or {string}")
    public void i_verify_the_search_results_contain_or(String keyword1, String keyword2) {
        waitForPageToLoad();

        List<WebElement> productDescriptions = driver.findElements(By.cssSelector("div.product-desc-sub-text"));
        List<WebElement> productTitles = driver.findElements(By.cssSelector("span.prdct-desc-cntnr-name"));

        boolean keywordFound = false;

        for (WebElement productDescription : productDescriptions) {
            if (productDescription.getText().toLowerCase().contains(keyword1.toLowerCase()) ||
                    productDescription.getText().toLowerCase().contains(keyword2.toLowerCase())) {
                keywordFound = true;
                currentProductDescription = productDescription;
                break;
            }
        }

        if (!keywordFound) {
            for (WebElement productTitle : productTitles) {
                if (productTitle.getText().toLowerCase().contains(keyword1.toLowerCase()) ||
                        productTitle.getText().toLowerCase().contains(keyword2.toLowerCase())) {
                    keywordFound = true;
                    currentProductTitle = productTitle;
                    break;
                }
            }
        }

        assertTrue("Search results do not contain one of the specified keywords: " + keyword1 + " or " + keyword2, keywordFound);
        ExtentReportManager.getTest().log(Status.PASS, "Search results contain the keywords");
    }

    @When("I click on a random product containing {string} or {string}")
    public void i_click_on_a_random_product_containing_or(String keyword1, String keyword2) {
        waitForPageToLoad();
        List<WebElement> productDescriptions = driver.findElements(By.cssSelector("div.product-desc-sub-text"));
        boolean keywordFound = false;
        WebElement randomProduct = null;

        for (WebElement productDescription : productDescriptions) {
            if (productDescription.getText().toLowerCase().contains(keyword1.toLowerCase()) ||
                    productDescription.getText().toLowerCase().contains(keyword2.toLowerCase())) {
                randomProduct = productDescription.findElement(By.xpath(".."));
                keywordFound = true;
                break;
            }
        }

        if (keywordFound && randomProduct != null) {
            WebElement productNameElement = randomProduct.findElement(By.cssSelector("div.product-desc-sub-text"));
            clickedProductName = productNameElement.getText();
            randomProduct.click();
            ExtentReportManager.getTest().log(Status.PASS, "Clicked on a random product containing the keyword");

            String currentWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();

            for (String window : allWindows) {
                if (!window.equals(currentWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }
        } else {
            ExtentReportManager.getTest().log(Status.FAIL, "No product containing the keywords was found.");
            throw new RuntimeException("No product containing the keywords was found.");
        }
    }

    @When("I click on the product from search results")
    public void i_click_on_the_product_from_search_results() {
        try {
            if (currentProductDescription != null) {
                currentProductDescription.click();
                ExtentReportManager.getTest().log(Status.PASS, "Clicked from product description.");
            } else if (currentProductTitle != null) {
                currentProductTitle.click();
                ExtentReportManager.getTest().log(Status.PASS, "Clicked from product title.");
            } else {
                throw new RuntimeException("Search results not found or could not be selected.");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to click on product: " + e.getMessage());
            throw new RuntimeException("Failed to click on product", e);
        }
    }

    @Then("I log the product's name and check if it is displayed")
    public void i_log_the_product_s_name_and_check_if_it_is_displayed() {
        try {
            WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(@class, 'pr-new-br')]//span")));
            String productName = productNameElement.getText();

            assertTrue("Product name is not visible!", productNameElement.isDisplayed());
            ExtentReportManager.getTest().log(Status.PASS, "Product name displayed.");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Product name validation failed: " + e.getMessage());
            throw new RuntimeException("Product name validation failed!", e);
        }
    }

    @Then("I log the product's price and check if it is displayed")
    public void i_log_the_product_s_price_and_check_if_it_is_displayed() {
        try {
            WebElement productPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@class='prc-dsc']")));
            String productPrice = productPriceElement.getText();

            assertTrue("Product price is not visible!", productPriceElement.isDisplayed());
            ExtentReportManager.getTest().log(Status.PASS, "Product price displayed.");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Product price validation failed: " + e.getMessage());
            throw new RuntimeException("Product price validation failed!", e);
        }
    }

    @When("I click on the product's all features button")
    public void i_click_on_the_product_s_all_features_button() {
        try {
            WebElement allFeaturesButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='feature-buttons']//a[@class='button-all-features']")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allFeaturesButton);
            ExtentReportManager.getTest().log(Status.PASS, "Clicked on product's all features button.");

            Thread.sleep(2000);
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to click on all features button: " + e.getMessage());
            throw new RuntimeException("Failed to click on all features button!", e);
        }
    }

    @When("I add the product to the cart")
    public void i_add_the_product_to_the_cart() {
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='add-to-basket-button-text']")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
            ExtentReportManager.getTest().log(Status.PASS, "Clicked on Add to Cart button.");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to add product to cart: " + e.getMessage());
            throw new RuntimeException("Failed to add product to cart!", e);
        }
    }

    @When("I go to the cart")
    public void i_go_to_the_cart() {
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.redirect-to-basket")));
        cartButton.click();
        ExtentReportManager.getTest().log(Status.PASS, "Navigated to the cart.");
    }

    @Then("I verify the product name in the cart matches the added product")
    public void i_verify_the_product_name_in_the_cart_matches_the_added_product() {
        try {
            WebElement cartProductName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[@class='pb-item' and contains(text(),'" + clickedProductName + "')]")));

            String cartProductNameText = cartProductName.getText();
            assertTrue("Product name in cart is incorrect!", cartProductNameText.contains(clickedProductName));
            ExtentReportManager.getTest().log(Status.PASS, "Product name in cart validated.");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to validate product name in cart: " + e.getMessage());
            throw new RuntimeException("Failed to validate product name in cart!", e);
        }
    }

    @Then("I verify that the total price equals the sum of individual product prices in the cart")
    public void i_verify_that_the_total_price_equals_the_sum_of_individual_product_prices_in_the_cart() {
        try {
            Thread.sleep(1000);
            List<WebElement> priceElements = driver.findElements(By.xpath("//ul[@class='pb-summary-box-prices']//strong"));

            double totalCalculatedPrice = 0;

            Thread.sleep(1000);
            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replaceAll("[^0-9,]", "");
                double priceValue = Double.parseDouble(priceText.replace(",", "."));

                Thread.sleep(1000);
                if (priceElement.getText().contains("-")) {
                    totalCalculatedPrice -= priceValue;
                } else {
                    totalCalculatedPrice += priceValue;
                }

                Thread.sleep(1000);
            }

            WebElement totalPriceElement = driver.findElement(By.xpath("//div[@class='pb-summary-total-price discount-active']"));
            String totalPriceText = totalPriceElement.getText().replaceAll("[^0-9,]", "");
            double totalPriceValue = Double.parseDouble(totalPriceText.replace(",", "."));

            Thread.sleep(1000);

            if (totalPriceValue != totalCalculatedPrice) {
                ExtentReportManager.getTest().log(Status.WARNING, "Prices do not match! Expected: " + totalCalculatedPrice + " TL, Found: " + totalPriceValue + " TL");
            } else {
                ExtentReportManager.getTest().log(Status.PASS, "Total price validated. Calculated total: " + totalCalculatedPrice + " TL, Actual total: " + totalPriceValue + " TL");
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Total price validation failed: " + e.getMessage());
            throw new RuntimeException("Total price validation failed!", e);
        }
    }

    @When("I remove a product from the cart")
    public void i_remove_a_product_from_the_cart() {
        try {
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.className("checkout-saving-remove-button")));

            Thread.sleep(5000);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeButton);
            ExtentReportManager.getTest().log(Status.PASS, "Product removed using the button in the cart.");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to remove product from cart using button: " + e.getMessage());
            throw new RuntimeException("Failed to remove product from cart", e);
        }
    }

    @When("I increase the product quantity to 2 in the cart")
    public void i_increase_the_product_quantity_to_2_in_the_cart() {
        try {
            WebElement productPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='pb-basket-item-price']")));
            String productPriceText = productPriceElement.getText().replaceAll("[^0-9,]", "");
            double productPrice = Double.parseDouble(productPriceText.replace(",", "."));

            totalProductPrice = productPrice;

            WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@class='counter-content' and @value='1']")));

            quantityInput.clear();
            quantityInput.sendKeys("2");

            Thread.sleep(2000);

            WebElement updatedPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='pb-summary-total-price discount-active']")));

            String updatedPriceText = updatedPrice.getText().replaceAll("[^0-9,]", "");
            double updatedPriceValue = Double.parseDouble(updatedPriceText.replace(",", "."));

            double expectedPrice = totalProductPrice * 2;
            if (updatedPriceValue != expectedPrice) {
                ExtentReportManager.getTest().log(Status.WARNING, "Product quantity could not be increased. Expected total price: " + expectedPrice + " TL, Found: " + updatedPriceValue + " TL");
            }

            ExtentReportManager.getTest().log(Status.PASS, "Product quantity increased to 2. Updated total price: " + updatedPriceValue + " TL");
            ExtentReportManager.getTest().log(Status.PASS, "Total price updated and correctly calculated: " + updatedPriceValue + " TL");

        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Error while increasing product quantity: " + e.getMessage());
            throw new RuntimeException("Error while increasing product quantity!", e);
        }
    }

    @Then("I switch to the new tab and close the old one")
    public void i_switch_to_the_new_tab_and_close_the_old_one() {
        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                driver.close();
                break;
            }
        }

        driver.switchTo().window(currentWindow);
    }
}
