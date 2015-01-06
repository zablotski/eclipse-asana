package acceptanceTests;

import java.util.HashMap;

import net.joelinn.asana.projects.Projects;
import net.joelinn.asana.workspaces.Workspaces;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import functionalities.ApplicationContext;

public class ProjectsReviewStepDefs {
	private Workspaces availableWorkspaces;
	private Projects workspaceProjects;
	private ApplicationContext application;
	private HashMap<String, String> testedUsersCredentials = new HashMap<>();
	
	public ProjectsReviewStepDefs() {
		testedUsersCredentials.put("j-kwasnicki@wp.pl", "5aix9w1X.93qkODiCZmG2EBSZ92EbteK");
	}
	
	@Given("^user is logged in as \"(.*?)\"$")
	public void user_is_logged_in_as(String arg1) throws Throwable {
		application = new ApplicationContext();
	    application.initAsana(testedUsersCredentials.get(arg1), arg1);
	}

	@Given("^user chosen \"(.*?)\" as setWorkspace$")
	public void user_chosen_as_setWorkspace(String arg1) throws Throwable {
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

	@When("^user clicks projects drop down list$")
	public void user_clicks_projects_drop_down_list() throws Throwable {
	    workspaceProjects = application.getAvailableProjectsInWorkspace();
	}

	@Then("^list contains \\[\"(.*?)\"\\]$")
	public void list_contains(String arg1) throws Throwable {
		String availableProjectsNames = "";
	    

    	for(int i=0; i<workspaceProjects.size(); i++){
    		availableProjectsNames += workspaceProjects.get(i).name + ",";
	    }
    	availableProjectsNames = availableProjectsNames.substring(0, availableProjectsNames.length()-1);
	    
    	if(!availableProjectsNames.equals(arg1)){
    		throw new Exception();
    	}
	}

	@Given("^user choose workspace \"(.*?)\"$")
	public void user_choose_workspace(String arg1) throws Throwable {
		user_chosen_as_setWorkspace(arg1);
	}

	@When("^user pick \"(.*?)\" from projects drop down list$")
	public void user_pick_from_projects_drop_down_list(String arg1) throws Throwable {
		int chosenProjectIndex = -1;
	    
	    for(int i=0; i<workspaceProjects.size(); i++){
	    	if(workspaceProjects.get(i).name.equals(arg1)){
	    		chosenProjectIndex = i;
	    		break;
	    	}
	    }
	    
	    if(chosenProjectIndex > -1){
	    	application.setSelectedProjectObject(chosenProjectIndex);
	    }else{
	    	throw new Exception();
	    }
	}

	@Then("^chosen project is \"(.*?)\"$")
	public void chosen_project_is(String arg1) throws Throwable {
		application.getSelectedProjectObject().name.equals(arg1);
	}
}
