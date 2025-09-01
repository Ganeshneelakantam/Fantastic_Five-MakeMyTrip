Feature: Retrieve travel insurance plan details

  Scenario: Retrieve insurance plan details for a student traveling to a country
    Given user is on the MakeMyTrip homepage
    When user navigates to Travel Insurance tab
    And user selects the USA country
    And user selects student insurance option
    And user views available insurance plans
    Then user retrieves and prints insurance plan details
