Feature: Login Failure Actions on MakeMyTrip

  Scenario: User tries to login with invalid credentials and verifies error message
    Given user is on MakeMyTrip Home page
    When user navigates to Biz Account section
    And user clicks on Forgot Login ID
    And user enters an invalid "9381283039" and continues
    Then error message should be displayed
