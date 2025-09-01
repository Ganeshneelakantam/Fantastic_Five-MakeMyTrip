Feature: Cab Booking Functionality
This feature is to book a one-way outstation trip using cab facility
	
	Scenario: Finding one-way cab from <From> to <Destination>
	    Given the user is on the MakeMyTrip homepage
	    When the user selects Cabs from the menu
	    And ensures Outstation One Way is selected
   	    And read the data from excel sheet through config properties file
	    And enters "<From>" as the source
	    And enters "<Destination>" as the destination
	    And selects travel date as "<TravelDate>"
	    And selects pickup time as "<Hour>" "<Minute>" "<Meridian>"
	    And clicks on Search
	    Then the cab search should be initiated successfully
		Then the user selects "<CarType>" as the car type and get the lowest price for SUV cabs	    