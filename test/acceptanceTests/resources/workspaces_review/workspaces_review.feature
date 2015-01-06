Feature: Workspaces review
	In order to be able to act on my Projects included in workspace
	As an AsanaUser
	I want to see my all workspaces
	
	Scenario Outline: View Workspaces
		Given user is logged in as_2 <email>
		When user clicks workspaces drop down list
		Then workspaces list contains <workspacesList>
		
	Examples:
    |  email                     |  workspacesList                        |
    |  "j-kwasnicki@wp.pl"       |  "PWR,PWR2,Personal Projects"          |
	
	Scenario Outline: Choose workspace
		Given user is logged in as_2 <email>
		When user pick <workspaceName> from workspaces drop down list
		Then chosen workspace is <workspaceName>
		
	Examples:
	|  email                     |  workspaceName  |
    |  "j-kwasnicki@wp.pl"       |  "PWR"          |