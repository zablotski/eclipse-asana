Feature: Task management
	In order to be able to act fast on my broject board
	As an AsanaUser
	I want to manage my tasks from eclipse IDE
	
	Scenario Outline: View Tasks
		Given user is logged in as <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		When user clicks manage tasks
		Then list contains <tasks>
		
	Examples:
    |  email                     |  workspaceName                |  projectsList   |
    |  "j-kwasnicki@wp.pl"       |  "PWR"                        |  ["Project 1"]  |
    |  "j-kwasnicki@wp.pl"       |  "PWR2"                       |  ["Project2"]   |
	
	Scenario Outline: Open task
		Given user is logged in as <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		When user pick <taskName> from tasks list
		Then chosen task is <taskName>
		
	|  email                     |  workspaceName  |  projectName  |
    |  "j-kwasnicki@wp.pl"       |  "PWR"          |  "Project 1"  |
	
	Scenario Outline: Create task
		Given user is logged in as <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		When user enters task name <taskName>
		And user set section on <section>
		And user chose assignee <taskAssignee> from project members list
		And user chose assigneeStatus <assigneeStatus> from assignee statuses list
		And user chose completeness status <completeTask> from completeness radio buttons
		And user enters due date <dueDate>
		And user chose heart-mark <heartMark> from heart-mark radio buttons
		And user enters notes <taskNotes>
		And afterwards user clicks createTask button
		Then task is <createdStatus>
		
	| email               | workspaceName | project     | section         | taskName          | taskAssignee        | assigneeStatus | completeTask | dueDate | heartMark | taskNotes                                                  | createdStatus |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | ""              | "Napisac feature" | "Michal G"          | "Inbox"        | false        | ""      | false     | "Opis"                                                     | "created"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | ""              | "Pusty task"      | "Yauheni Zablotski" | ""             | false        | ""      | false     | ""                                                         | "created"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "Section ToDo:" | "Pusty task"      | ""                  | ""             | false        | ""      | false     | "task o tej nazwie istnieje, a mimo tego nie ma konfliktu" | "created"     |
	
	Scenario Outline: Update task
		Given user is logged in as <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		And user picked id: <taskId> as current task
		And <taskId> task name is <taskName_PreEdit>
		When user enters task name <taskName_PostEdit>
		And afterwards user clicks updateTask button
		Then task is <updatedStatus>
		
	| email               | workspaceName | project     | taskId         | taskName_PreEdit          | taskName_PostEdit        | updatedStatus |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | 19191138125432 | "Section ToDo:"           | "ToDo:"                  | "updated"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | 19191138125432 | "ToDo:"                   | "Section ToDo:"          | "updated"     |
	
	Scenario Outline: Delete task
		Given user is logged in as <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		And user picked task: <taskName> as current task
		When user clicks deleteTask button
		Then <taskName> task is <deletedStatus>
		
	| email               | workspaceName | project     | taskName          | deletedStatus |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "Napisac feature" | "deleted"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "Pusty task"      | "deleted"     |