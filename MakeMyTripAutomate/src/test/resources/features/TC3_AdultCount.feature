Feature: Get max adult count from the Guests field in Hotels tab
Navigate to hotels tab and get the max adult count from the dropdown in Guests feild

  Scenario: Get maximum adult count available in Hotels guests selector
    Given user navigates to MakeMyTrip homepage
    When user navigates to Hotels tab and opens guest dropdown
    And user selects the Adults dropdown
    Then user retrieves the maximum adult count from the dropdown
