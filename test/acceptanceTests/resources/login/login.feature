Feature: Login
	In order to be able to act on my Asana account
	As an AsanaUser
	I want to log into my Asana account
	
	Scenario Outline: Login
		Given email is <email> 
		And APIkey is <apiKey>
		When user clicks submit login form button
		Then user is <loginMessage>
		
	Examples:
    |  email                     |  apiKey                              |  loginMessage     |
    |  "j-kwasnicki@wp.pl"       |  "5aix9w1X.93qkODiCZmG2EBSZ92EbteK"  |  "Logged in"      |
    |  "e.zablotski@gmail.com"   |  "????????????????????????????????"  |  "Not logged in"  |