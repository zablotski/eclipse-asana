package functionalities;

import java.util.List;

import resources.StringResources;
import models.TaskTreeNode;
import net.joelinn.asana.projects.Project;
import net.joelinn.asana.projects.Projects;
import net.joelinn.asana.tasks.Task;
import net.joelinn.asana.tasks.Tasks;
import net.joelinn.asana.users.User;
import net.joelinn.asana.users.Users;
import net.joelinn.asana.workspaces.Workspace;
import net.joelinn.asana.workspaces.Workspaces;

public class ApplicationContext {
	private static boolean SHOW_ARCHIVED_PROJECTS = false;
	
	private StringResources stringResources = StringResources.getInstance();
	private AsanaTerminal terminal;
	private Workspaces availableWorkspaces;
	private Workspace selectedWorkspaceObject;

	private Projects availableProjectsInWorkspace;
	private Project selectedProjectObject;
	
	private Users usersFromSelectedProject;
	private Tasks tasksFromSelectedProject;
	private Tasks userTasksFromSelectedProject;
	private Tasks sectionsFromSelectedProject;
	private List<TaskTreeNode> tasksTree;
	private User currentUser;
	
	
	public ApplicationContext(){

	}
	
	public void initAsana(String apiKey, String email) throws Exception{
		terminal = new AsanaTerminal(apiKey, email);
		
		currentUser = terminal.users().getMe();
		availableWorkspaces = terminal.workspaces().getWorkspaces();
	}
	
	public void calculateTasksAndSections(Tasks input){
		Tasks userTasks = new Tasks();
		Tasks sections = new Tasks();
		
		for(Task t : input){
			if(t.name.endsWith(stringResources.getSectionConventionSuffix())){
				sections.add(t);
			}else{
				userTasks.add(t);
			}
		}
		
		userTasksFromSelectedProject = userTasks;
		sectionsFromSelectedProject = sections;
	}

	public Workspaces getAvailableWorkspaces() {
		return availableWorkspaces;
	}

	public Workspace getSelectedWorkspaceObject() {
		return selectedWorkspaceObject;
	}
	
	public void setSelectedWorkspaceObject(int index){
		this.selectedWorkspaceObject = availableWorkspaces.get(index);
		setAvailableProjectsInWorkspace(getWorkspaceProjects(selectedWorkspaceObject.id, SHOW_ARCHIVED_PROJECTS));
	}
	
	public void setSelectedProjectObject(int index) {
		this.selectedProjectObject = availableProjectsInWorkspace.get(index);
	}

	private Projects getWorkspaceProjects(long workspaceId, boolean archived){
		return terminal.projects().getProjects(workspaceId, archived);
	}
	
	private void setAvailableProjectsInWorkspace(Projects projects){
		this.availableProjectsInWorkspace = projects;
	}

	public Project getSelectedProjectObject() {
		return selectedProjectObject;
	}

	public Users getUsersFromSelectedProject() {
		return usersFromSelectedProject;
	}

	public Tasks getTasksFromSelectedProject() {
		return tasksFromSelectedProject;
	}

	public Tasks getUserTasksFromSelectedProject() {
		return userTasksFromSelectedProject;
	}

	public Tasks getSectionsFromSelectedProject() {
		return sectionsFromSelectedProject;
	}

	public List<TaskTreeNode> getTasksTree() {
		return tasksTree;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Projects getAvailableProjectsInWorkspace() {
		return availableProjectsInWorkspace;
	}
}
