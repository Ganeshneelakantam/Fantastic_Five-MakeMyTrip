Feature: Gift Card Functionality
while buying a group gift card enter the email address wrong get the error message

	Scenario: get the error message for wrong <Email> address
		Given user is on the MakeMyTrip Homepage
		When the user click on the gift card tab
		And read the data from excel
		And the user select the group "<Gift Card Name>"
		And the user enter the recipient "<Name>" "<Number>" and enter the wrong "<Email>" address
		And the user click on buy gift card button
		Then get the error message displayed 
		