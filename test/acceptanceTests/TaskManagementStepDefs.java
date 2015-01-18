package acceptanceTests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.joelinn.asana.projects.Project;
import net.joelinn.asana.projects.Projects;
import net.joelinn.asana.tasks.Task;
import net.joelinn.asana.tasks.TaskRequestBuilder;
import net.joelinn.asana.tasks.Tasks;
import net.joelinn.asana.workspaces.Workspace;
import net.joelinn.asana.workspaces.Workspaces;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import functionalities.ApplicationContext;

public class TaskManagementStepDefs {
	
	//For creating task scenario test;
	private long section_NEW;
	private String taskName_NEW;
	private long assignee_NEW;
	private String assigneeStatus_NEW;
	private boolean isComplete_NEW;
	private String duedate_NEW;
	private boolean isHeartMarked_NEW;
	private String notes_NEW;
	//********************************
	
	private Workspace setWorkspace;
	private Project setProject;
	private Task setTask;
	private ApplicationContext application;
	private HashMap<String, String> testedUsersCredentials = new HashMap<>();
	
	public TaskManagementStepDefs() {
		testedUsersCredentials.put("j-kwasnicki@wp.pl", "5aix9w1X.93qkODiCZmG2EBSZ92EbteK");
	}
	
	@Given("^user is logged in as_ \"(.*?)\"$")
	public void user_is_logged_in_as_(String arg1) throws Throwable {
		application = new ApplicationContext();
	    application.initAsana(testedUsersCredentials.get(arg1), arg1);
	}

	@Given("^user picked \"(.*?)\" as workspace$")
	public void user_picked_as_workspace(String arg1) throws Throwable {
	    Workspaces availableWorkspaces = application.getAvailableWorkspaces();
	    
	    for(int i=0; i<availableWorkspaces.size(); i++){
	    	if(availableWorkspaces.get(i).name.equals(arg1)){
	    		application.setSelectedWorkspaceObject(i);
	    		setWorkspace = application.getSelectedWorkspaceObject();
	    		break;
	    	}
	    }
	    
	    if(setWorkspace == null){
	    	throw new Exception();
	    }
	}

	@When("^user pick \"(.*?)\" as project$")
	public void user_pick_as_project(String arg1) throws Throwable {
		Projects availableProjects = application.getAvailableProjectsInWorkspace();
	    
	    for(int i=0; i<availableProjects.size(); i++){
	    	if(availableProjects.get(i).name.equals(arg1)){
	    		application.setSelectedProjectObject(i);
	    		setProject = application.getSelectedProjectObject();
	    		break;
	    	}
	    }
	    
	    if(setProject == null){
	    	throw new Exception();
	    }
	}

	@Then("^task list contains \"(.*?)\"$")
	public void task_list_contains(String arg1) throws Throwable {
		List<String> testVariableToList = Arrays.asList(arg1.split(","));
		
		for(int i=0; i<application.getUserTasksFromSelectedProject_NoSubtasks().size(); i++){
			if(!testVariableToList.contains(application.getUserTasksFromSelectedProject_NoSubtasks().get(i).name)){
	    		throw new Exception();
	    	}
		}
	}

	@Given("^user picked \"(.*?)\" as project$")
	public void user_picked_as_project(String arg1) throws Throwable {
		Projects availableProjects = application.getAvailableProjectsInWorkspace();
	    
	    for(int i=0; i<availableProjects.size(); i++){
	    	if(availableProjects.get(i).name.equals(arg1)){
	    		application.setSelectedProjectObject(i);
	    		setProject = application.getSelectedProjectObject();
	    		break;
	    	}
	    }
	    
	    if(setProject == null){
	    	throw new Exception();
	    }
	}

	@When("^user pick \"(.*?)\" from tasks list$")
	public void user_pick_from_tasks_list(String arg1) throws Throwable {
		Tasks availableTasks = application.getTasksFromSelectedProject();
	    
	    for(int i=0; i<availableTasks.size(); i++){
	    	if(availableTasks.get(i).name.equals(arg1)){
	    		application.setSelectedTaskObject(i);
	    		setTask = application.getSelectedTaskObject();
	    		break;
	    	}
	    }
	    
	    if(setTask == null){
	    	throw new Exception();
	    }
	}

	@Then("^chosen task is \"(.*?)\"$")
	public void chosen_task_is(String arg1) throws Throwable {
	    if(!setTask.name.equals(arg1)){
	    	throw new Exception();
	    }
	}

	@When("^user set section on \"(.*?)\"$")
	public void user_set_section_on(String arg1) throws Throwable {
		long selectedSection = -1;
		
		if(arg1.equals("")){
			section_NEW = -1;
			return;
		}
		
	    for(int i=0; i<application.getSectionsFromSelectedProject().size(); i++){
	    	if(application.getSectionsFromSelectedProject().get(i).name.equals(arg1)){
	    		selectedSection = application.getSectionsFromSelectedProject().get(i).id;
	    	}
	    }
	    
	    if(selectedSection > -1){
	    	section_NEW = selectedSection;
	    }else{
	    	throw new Exception();
	    }
	}

	@When("^user enters task name \"(.*?)\"$")
	public void user_enters_task_name(String arg1) throws Throwable {
	    taskName_NEW = arg1;
	}

	@When("^user chose assignee \"(.*?)\" from project members list$")
	public void user_chose_assignee_from_project_members_list(String arg1) throws Throwable {
		long selectedUser = -1;
		
		if(arg1.equals("")){
			assignee_NEW = -1;
			return;
		}
		
	    for(int i=0; i<application.getUsersFromSelectedProject().size(); i++){
	    	if(application.getUsersFromSelectedProject().get(i).name.equals(arg1)){
	    		selectedUser = application.getUsersFromSelectedProject().get(i).id;
	    	}
	    }
	    
	    if(selectedUser > -1){
	    	assignee_NEW = selectedUser;
	    }else{
	    	throw new Exception();
	    }
	}

	@When("^user chose assigneeStatus \"(.*?)\" from assignee statuses list$")
	public void user_chose_assigneeStatus_from_assignee_statuses_list(String arg1) throws Throwable {
		switch (arg1){
			case "Inbox":
				assigneeStatus_NEW = TaskRequestBuilder.ASSIGNEE_STATUS_INBOX;
				break;
			case "Later":
				assigneeStatus_NEW = TaskRequestBuilder.ASSIGNEE_STATUS_LATER;
				break;
			case "Today":
				assigneeStatus_NEW = TaskRequestBuilder.ASSIGNEE_STATUS_TODAY;
				break;
			case "Upcoming":
				assigneeStatus_NEW = TaskRequestBuilder.ASSIGNEE_STATUS_UPCOMING;
				break;
		}
	    
	}

	@When("^user chose completeness status false from completeness radio buttons$")
	public void user_chose_completeness_status_false_from_completeness_radio_buttons() throws Throwable {
	    isComplete_NEW = false;
	}

	@When("^user enters due date \"(.*?)\"$")
	public void user_enters_due_date(String arg1) throws Throwable {
	    duedate_NEW = arg1;
	}

	@When("^user chose heart-mark false from heart-mark radio buttons$")
	public void user_chose_heart_mark_false_from_heart_mark_radio_buttons() throws Throwable {
	    isHeartMarked_NEW = false;
	}

	@When("^user enters notes \"(.*?)\"$")
	public void user_enters_notes(String arg1) throws Throwable {
	    notes_NEW = arg1;
	}

	@When("^afterwards user clicks createTask button$")
	public void afterwards_user_clicks_createTask_button() throws Throwable {
	    application.createTask(section_NEW,
	    		taskName_NEW,
	    		assignee_NEW,
	    		assigneeStatus_NEW,
	    		isComplete_NEW,
	    		duedate_NEW,
	    		isHeartMarked_NEW,
	    		notes_NEW);
	}

	@Then("^task is \"(.*?)\"$")
	public void task_is(String arg1) throws Throwable {
	    if(!(arg1.equals("created") || arg1.equals("updated"))){
	    	throw new Exception();
	    }
	}

	@Given("^user picked (\\d+) from tasks list$")
	public void user_picked_from_tasks_list(int arg1) throws Throwable {
		Tasks availableTasks = application.getTasksFromSelectedProject();
	    
	    for(int i=0; i<availableTasks.size(); i++){
	    	if(availableTasks.get(i).name.equals(arg1)){
	    		application.setSelectedTaskObject(i);
	    		setTask = application.getSelectedTaskObject();
	    		break;
	    	}
	    }
	    
	    if(setTask == null){
	    	throw new Exception();
	    }
	}

	@Given("^task key is \"(.*?)\" (\\d+)$")
	public void task_key_is(String arg1, int arg2) throws Throwable {
	    if(!(setTask.name.equals(arg1) && setTask.id == arg2)){
	    	throw new Exception();
	    }
	}

	@When("^user clicks updateTask button$")
	public void user_clicks_updateTask_button() throws Throwable {
			
		
	    application.updateSelectedTask(-1,
	    		taskName_NEW,
	    		-1,
	    		null,
	    		false,
	    		false,
	    		null,
	    		false,
	    		false,
	    		null);
	}

	@Given("^user have picked \"(.*?)\" from tasks list$")
	public void user_have_picked_from_tasks_list(String arg1) throws Throwable {
		Tasks availableTasks = application.getTasksFromSelectedProject();
	    
	    for(int i=0; i<availableTasks.size(); i++){
	    	if(availableTasks.get(i).name.equals(arg1)){
	    		application.setSelectedTaskObject(i);
	    		setTask = application.getSelectedTaskObject();
	    		break;
	    	}
	    }
	    
	    if(setTask == null){
	    	throw new Exception();
	    }
	}

	@When("^user clicks deleteTask button$")
	public void user_clicks_deleteTask_button() throws Throwable {
	    application.deleteCurrentTask();
	}

	@Then("^\"(.*?)\" named task is \"(.*?)\"$")
	public void named_task_is(String arg1, String arg2) throws Throwable {
		boolean taskExists = false;
		
		for(Task t : application.getUserTasksFromSelectedProject()){
			if(t.name.equals(arg1)){
				taskExists = true;
				break;
			}
		}
		
	    if(arg2.equals("deleted")){
	    	if(taskExists){
	    		throw new Exception();
	    	}
	    }else{
	    	if(arg2.equals("undeleted")){
	    		if(!taskExists){
	    			throw new Exception();
	    		}
	    	}else{
	    		throw new Exception();
	    	}
	    	
	    }
	}
}
