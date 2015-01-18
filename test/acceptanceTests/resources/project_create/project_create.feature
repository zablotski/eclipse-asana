Feature: Workspaces review
	In order to be able to create my own workspaces
	As an AsanaUser
	I want to create new workspace
	
	Scenario Outline: Create workspace
		Given user is logged in as_4 <email>
		And user chosen <workspaceName> as setWorkspace2
		When user add  <new_project> 
		Then list of projects contains <new_project>
		
	Examples:
    |  email                     	 |  new_project       |  workspaceName         |
    |  "e.zablotski@gmail.com"       |  "added From Test" |  		"Zawody"       |
