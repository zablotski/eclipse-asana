Feature: Task management
	In order to be able to act fast on my broject board
	As an AsanaUser
	I want to manage my tasks from eclipse IDE
	
	Scenario Outline: View Tasks
		Given user is logged in as_ <email>
		And user picked <workspaceName> as workspace
		When user pick <project> as project
		Then task list contains <tasks>
		
	Examples:
    |  email                     |  workspaceName                |  project      |  tasks                              |
    |  "j-kwasnicki@wp.pl"       |  "PWR"                        |  "Project 1"  | "Sample task,esf,ss,Nowy task,Test" |
    |  "j-kwasnicki@wp.pl"       |  "PWR2"                       |  "Project2"   | ""                                  |
	
	Scenario Outline: Open task
		Given user is logged in as_ <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		When user pick <taskName> from tasks list
		Then chosen task is <taskName>
	
	Examples:	
	|  email                     |  workspaceName  |  project      |  taskName  |
    |  "j-kwasnicki@wp.pl"       |  "PWR"          |  "Project 1"  |  "esf"     |
	
	Scenario Outline: Create task
		Given user is logged in as_ <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		When user set section on <section>
		And user enters task name <taskName>
		And user chose assignee <taskAssignee> from project members list
		And user chose assigneeStatus <assigneeStatus> from assignee statuses list
		And user chose completeness status <completeTask> from completeness radio buttons
		And user enters due date <dueDate>
		And user enters notes <taskNotes>
		And afterwards user clicks createTask button
		Then task is <createdStatus>
		
	Examples:
	| email               | workspaceName | project     | section         | taskName          | taskAssignee        | assigneeStatus | completeTask | dueDate | taskNotes                                                  | createdStatus |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | ""              | "Napisac feature" | "Michal G"          | "Inbox"        | false        | ""      | "Opis"                                                     | "created"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "Section ToDo:" | "Pusty task"      | "Yauheni Zablotski" | ""             | false        | ""      | ""                                                         | "created"     |
	
	Scenario Outline: Update task
		Given user is logged in as_ <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		And user picked <taskId> from tasks list
		And task key is <taskName_PreEdit> <taskId>
		When user enters task name <taskName_PostEdit>
		And user clicks updateTask button
		Then task is <updatedStatus>
		
	Examples:
	| email               | workspaceName | project     | taskId           | taskName_PreEdit          | taskName_PostEdit        | updatedStatus |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "24420228984192" | "Sample task"             | "Sformulowac feature"    | "updated"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "24420228984192" | "Sformulowac feature"     | "Sample task"            | "updated"     |
	
	Scenario Outline: Delete task
		Given user is logged in as_ <email>
		And user picked <workspaceName> as workspace
		And user picked <project> as project
		And user have picked <taskName> from tasks list
		When user clicks deleteTask button
		Then <taskName> named task is <deletedStatus>
		
	Examples:
	| email               | workspaceName | project     | taskName          | deletedStatus |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "Napisac feature" | "deleted"     |
	| "j-kwasnicki@wp.pl" | "PWR"         | "Project 1" | "Pusty task"      | "deleted"     |