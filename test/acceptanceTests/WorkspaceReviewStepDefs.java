package acceptanceTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.joelinn.asana.workspaces.Workspaces;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import functionalities.ApplicationContext;

public class WorkspaceReviewStepDefs {
	private Workspaces availableWorkspaces;
	private ApplicationContext application;
	private HashMap<String, String> testedUsersCredentials = new HashMap<>();
	
	public WorkspaceReviewStepDefs() {
		testedUsersCredentials.put("j-kwasnicki@wp.pl", "5aix9w1X.93qkODiCZmG2EBSZ92EbteK");
	}
	
	@Given("^user is logged in as_2 \"(.*?)\"$")
	public void user_is_logged_in_as_2(String arg1) throws Throwable {
	    application = new ApplicationContext();
	    application.initAsana(testedUsersCredentials.get(arg1), arg1);
	}

	@When("^user clicks workspaces drop down list$")
	public void user_clicks_workspaces_drop_down_list() throws Throwable {
		availableWorkspaces = application.getAvailableWorkspaces();
	}

	@Then("^workspaces list contains \"(.*?)\"$")
	public void workspaces_list_contains(String arg1) throws Throwable {
	    List<String> compareVariableToList = Arrays.asList(arg1.split(","));
	    
    	for(int i=0; i<availableWorkspaces.size(); i++){
	    	if(!compareVariableToList.contains(availableWorkspaces.get(i).name)){
	    		throw new Exception();
	    	}
	    }

	}

	@When("^user pick \"(.*?)\" from workspaces drop down list$")
	public void user_pick_from_workspaces_drop_down_list(String arg1) throws Throwable {
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

	@Then("^chosen workspace is \"(.*?)\"$")
	public void chosen_workspace_is(String arg1) throws Throwable {
	    application.getSelectedWorkspaceObject().name.equals(arg1);
	}
}
