package functionalities;

import java.util.ArrayList;
import java.util.List;

import resources.StringResources;
import models.TaskTreeNode;
import net.joelinn.asana.projects.Project;
import net.joelinn.asana.projects.Projects;
import net.joelinn.asana.tasks.Task;
import net.joelinn.asana.tasks.TaskRequestBuilder;
import net.joelinn.asana.tasks.Tasks;
import net.joelinn.asana.users.User;
import net.joelinn.asana.users.Users;
import net.joelinn.asana.workspaces.Workspace;
import net.joelinn.asana.workspaces.Workspaces;

public class ApplicationContext {
	private static boolean SHOW_ARCHIVED_PROJECTS = false;
	private static String[] ADDITIONAL_TASK_FIELD_NAMES_TO_TASK_LIST = new String[] {"parent", "assignee", "name"};
	
	private StringResources stringResources = StringResources.getInstance();
	private AsanaTerminal terminal;
	private Workspaces availableWorkspaces;
	private Workspace selectedWorkspaceObject;

	private Projects availableProjectsInWorkspace;
	private Project selectedProjectObject;
	
	private Users usersFromSelectedProject;
	private Tasks tasksFromSelectedProject;
	private Tasks userTasksFromSelectedProject;
	private Tasks userTasksFromSelectedProject_NoSubtasks;
	private Tasks sectionsFromSelectedProject;
	private List<TaskTreeNode> tasksTree;
	
	private Task selectedTaskObject;
	
	private User currentUser;
	
	
	public ApplicationContext(){

	}
	
	
	
	//Utilities
	/////////////////////////////////////////////////////////////////
	//public
	
	public void initAsana(String apiKey, String email) throws Exception{
		terminal = new AsanaTerminal(apiKey, email);
		
		currentUser = terminal.users().getMe();
		availableWorkspaces = terminal.workspaces().getWorkspaces();
	}
	
	//private
	
	private void calculateTasksAndSections(Tasks input){
		Tasks userTasks = new Tasks();
		Tasks userTasks_NoSubtasks = new Tasks();
		Tasks sections = new Tasks();
		
		for(Task t : input){
			if(t.name.endsWith(stringResources.getSectionConventionSuffix())){
				sections.add(t);
			}else{
				userTasks.add(t);
				if(t.parent == null){
					userTasks_NoSubtasks.add(t);
				}
			}
		}
		
		userTasksFromSelectedProject_NoSubtasks = userTasks_NoSubtasks;
		userTasksFromSelectedProject = userTasks;
		sectionsFromSelectedProject = sections;
	}
	
	private Tasks getTasksWithSubtasks(Tasks results, Tasks lastAdded){
		Tasks temp = new Tasks();
		Tasks temp2 = new Tasks();
		temp2.addAll(results);
		
		for(Task t : lastAdded){
			temp.addAll(terminal.tasks().getSubtasks(t.id, ADDITIONAL_TASK_FIELD_NAMES_TO_TASK_LIST));
		}
		
		if(temp.size() == 0){
			return results;
		}else{
			temp2.addAll(temp);
			return getTasksWithSubtasks(temp2, temp);
		}
	}
	
	private void setTasksFromSelectedProject() {
		//Pobiera tylko g³ówne taski
		Tasks listOfTasks = terminal.projects().getTasks(selectedProjectObject.id, 
				ADDITIONAL_TASK_FIELD_NAMES_TO_TASK_LIST);
		
		//Pobiera wszystkie taski
		listOfTasks = getTasksWithSubtasks(listOfTasks, listOfTasks);
		tasksFromSelectedProject = listOfTasks;
		calculateTasksAndSections(listOfTasks);
		//tasksTree = TaskTreeNode.tasksListToForest(tasksFromSelectedProject);
	}
	
	private void setUsersFromSelectedProject() {
		usersFromSelectedProject = terminal.projects().getProject(selectedProjectObject.id).members;
	}
	
	private void setAvailableProjectsInWorkspace(Projects projects){
		this.availableProjectsInWorkspace = projects;
	}
	
	private Projects getWorkspaceProjects(long workspaceId, boolean archived){
		return terminal.projects().getProjects(workspaceId, archived);
	}
	
	/////////////////////////////////////////////////////////////////
	
	
	

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
		setTasksFromSelectedProject();
		setUsersFromSelectedProject();
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
	
	public Tasks getUserTasksFromSelectedProject_NoSubtasks() {
		return userTasksFromSelectedProject_NoSubtasks;
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

	public void setSelectedTaskObject(int i) {
		this.selectedTaskObject = tasksFromSelectedProject.get(i);
	}

	public Task getSelectedTaskObject() {
		return this.selectedTaskObject;
	}

	public void createTask(long section_NEW, String taskName_NEW,
			long assignee_NEW, String assigneeStatus_NEW,
			boolean isComplete_NEW, String duedate_NEW,
			boolean isHeartMarked_NEW, String notes_NEW) {
		long[] parentProject = new long[] {selectedProjectObject.id};
		
		TaskRequestBuilder builder = new TaskRequestBuilder();
		
		if(section_NEW != -1){
			builder.parent(section_NEW);
			Task selectedSection;
			
			for(int i=0; i<this.sectionsFromSelectedProject.size(); i++ ){
				if(this.sectionsFromSelectedProject.get(i).id == section_NEW){
					selectedSection = this.sectionsFromSelectedProject.get(i);
					builder.workspace(selectedSection.workspace.id);
					
					//Where is LINQ :<
					long[] selectedSectionParentProject = new long[selectedSection.projects.size()];
					for(int j=0; j<selectedSection.projects.size(); j++){
						selectedSectionParentProject[j] = selectedSection.projects.get(j).id;
					}
					
					if(selectedSectionParentProject.length > 0)
						builder.setProjects(selectedSectionParentProject);
				}
			}
		}else{
			builder.workspace(selectedWorkspaceObject.id);
			//builder.setProjects(parentProject);
			builder.setParam("Projects", "\"id\": 24420228984188, \"name\": \"Project 1\"");
		}
		
		if(assignee_NEW != -1)
			builder.assignee(assignee_NEW);
		
		if(!assigneeStatus_NEW.equals(""))
			builder.assigneeStatus(assigneeStatus_NEW);
		
		if(!duedate_NEW.equals(""))
			builder.dueOn(duedate_NEW);
		
		
		builder.name(taskName_NEW);
		builder.completed(isComplete_NEW);
		builder.notes(notes_NEW);
		builder.build();
		
		terminal.tasks().createTask(builder);
	}

	public void updateSelectedTask(long section_NEW, String taskName_NEW,
			long assignee_NEW, String assigneeStatus_NEW,
			boolean isComplete_NEW, boolean completeSpecified, String duedate_NEW,
			boolean isHeartMarked_NEW, boolean heartSpecified, String notes_NEW) {
		long[] parentProject = new long[] {selectedProjectObject.id};
		
		TaskRequestBuilder builder = new TaskRequestBuilder();
		
		if(section_NEW != -1){
			builder.parent(section_NEW);
			Task selectedSection;
			
			for(int i=0; i<this.sectionsFromSelectedProject.size(); i++ ){
				if(this.sectionsFromSelectedProject.get(i).id == section_NEW){
					selectedSection = this.sectionsFromSelectedProject.get(i);
					builder.workspace(selectedSection.workspace.id);
					
					//Where is LINQ :<
					long[] selectedSectionParentProject = new long[selectedSection.projects.size()];
					for(int j=0; j<selectedSection.projects.size(); j++){
						selectedSectionParentProject[j] = selectedSection.projects.get(j).id;
					}
					
					if(selectedSectionParentProject.length > 0)
						builder.setProjects(selectedSectionParentProject);
				}
			}
		}else{
			builder.workspace(selectedWorkspaceObject.id);
			builder.setProjects(parentProject);
		}
			
		if(assignee_NEW != -1)
			builder.assignee(assignee_NEW);
		
		if(assigneeStatus_NEW != null)
			builder.assigneeStatus(assigneeStatus_NEW);
		
		if(duedate_NEW != null)
			builder.dueOn(duedate_NEW);
		
		if(taskName_NEW != null)
			builder.name(taskName_NEW);
		
		if(completeSpecified)
			builder.completed(isComplete_NEW);
		
		if(notes_NEW != null)
			builder.notes(notes_NEW);

		builder.build();
		terminal.tasks().updateTask(selectedTaskObject.id, builder);
	}

	public void deleteCurrentTask() {
		// TODO Auto-generated method stub
		
	}
}
