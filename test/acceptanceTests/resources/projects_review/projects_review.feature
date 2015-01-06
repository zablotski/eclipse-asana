Feature: Projects review
	In order to be able to act on my Tasks included in project
	As an AsanaUser
	I want to see my all projects included in chosen workspace
	
	Scenario Outline: View Projects
		Given user is logged in as <email>
		And user chosen <workspaceName> as setWorkspace
		When user clicks projects drop down list
		Then projects list contains <projectsList>
		
	Examples:
    |  email                     |  workspaceName                |  projectsList   |
    |  "j-kwasnicki@wp.pl"       |  "PWR"                        |  "Project 1"    |
    |  "j-kwasnicki@wp.pl"       |  "PWR2"                       |  "Project2"     |
	
	Scenario Outline: Choose project
		Given user is logged in as <email>
		And user choose workspace <workspaceName>
		When user pick <projectName> from projects drop down list
		Then chosen project is <projectName>
		
	Examples:
	|  email                     |  workspaceName  |  projectName  |
    |  "j-kwasnicki@wp.pl"       |  "PWR"          |  "Project 1"  |