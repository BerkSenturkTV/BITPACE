Feature: Verify product details and add to cart

  Scenario: Verify product details are displayed
    Given I open the Trendyol website
    When I search for "Kablosuz kulaklık"
    Then I verify the search results are displayed
    And I verify the search results contain "Kablosuz" or "Kulaklık"
    When I click on a random product containing "Kablosuz" or "Kulaklık"
    Then I log the product's name and check if it is displayed
    And I log the product's price and check if it is displayed
    When I click on the product's all features button
    And I add the product to the cart
    And I go to the cart
    Then I verify the product name in the cart matches the added product
    And I verify that the total price equals the sum of individual product prices in the cart
    When I increase the product quantity to 2 in the cart
    When I search for "sony kamera"
    Then I verify the search results are displayed
    And I verify the search results contain "sony" or "kamera"
    Then I switch to the new tab and close the old one
    When I click on a random product containing "sony" or "kamera"
    Then I switch to the new tab and close the old one
    Then I log the product's name and check if it is displayed
    And I log the product's price and check if it is displayed
    And I add the product to the cart
    And I go to the cart
    Then I verify the product name in the cart matches the added product
    And I verify that the total price equals the sum of individual product prices in the cart
    When I remove a product from the cart
    And I verify that the total price equals the sum of individual product prices in the cart
