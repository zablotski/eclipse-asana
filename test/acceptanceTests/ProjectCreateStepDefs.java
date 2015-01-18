package acceptanceTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.joelinn.asana.projects.Project;
import net.joelinn.asana.projects.ProjectRequestBuilder;
import net.joelinn.asana.projects.Projects;
import net.joelinn.asana.workspaces.Workspaces;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import functionalities.ApplicationContext;

public class ProjectCreateStepDefs {
	private Workspaces availableWorkspaces;
	private HashMap<String, String> testedUsersCredentials = new HashMap<>();
	private Projects workspaceProjects;
	private ApplicationContext application;
	
	public ProjectCreateStepDefs() {
		testedUsersCredentials.put("e.zablotski@gmail.com", "5akl1GKq.TNjBXubjHJy5q58E7DZyqTS");
	}


	@Given("^user is logged in as_(\\d+) \"(.*?)\"$")
	public void user_is_logged_in_as_(int arg1, String arg2) throws Throwable {
		application = new ApplicationContext();
	    application.initAsana(testedUsersCredentials.get(arg2), arg2);
	}

	@Given("^user chosen \"(.*?)\" as setWorkspace(\\d+)$")
	public void user_chosen_as_setWorkspace(String arg1, int arg2) throws Throwable {
		int chosenWorkspaceIndex = -1;
		availableWorkspaces = application.getAvailableWorkspaces();
	    
	    for(int i=0; i<availableWorkspaces.size(); i++){
	    	if(availableWorkspaces.get(i).name.equals(arg1)){
	    		chosenWorkspaceIndex = i;
	    		break;
	    	}
	    }
	    
	    if(chosenWorkspaceIndex > -1){
	    	application.setSelectedWorkspaceObject(chosenWorkspaceIndex);
	    }else{
	    	throw new Exception();
	    }
	}

	@When("^user add  \"(.*?)\"$")
	public void user_add(String arg1) throws Throwable {
		application.createProject(application.getSelectedWorkspaceObject(), arg1);
	}

	@Then("^list of projects contains \"(.*?)\"$")
	public void list_of_projects_contains(String arg1) throws Throwable {
		Projects projects = application.getAvailableProjectsInWorkspace();
    	for(int i=0; i<projects.size(); i++){
    		if(arg1.equals(projects.get(i).name)){
    			return;
    		}
	    }
    	throw new Exception();
    	
	}
	
}
