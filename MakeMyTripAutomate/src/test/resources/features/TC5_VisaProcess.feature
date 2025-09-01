Feature: Retrieve visa application process details

  Scenario: Retrieve visa process details for a specific country and dates
    Given the user navigate to MakeMyTrip homepage
    When read visa data from excel for row one
    And the user completes visa application steps
    And the user clicks on the search button
    Then visa process details should be displayed and saved to JSON
