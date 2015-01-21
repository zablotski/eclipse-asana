package functionalities;

import resources.StringResources;
import models.TaskEnhancedList;
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
	private static String[] ADDITIONAL_TASK_FIELD_NAMES_TO_TASK_LIST = new String[] 
		{
			"parent", 
			"assignee", 
			"name", 
			"workspace", 
			"projects"
		};
	private static String[] assigneeStatusesStrings = new String[]
			{
				TaskRequestBuilder.ASSIGNEE_STATUS_INBOX, 
				TaskRequestBuilder.ASSIGNEE_STATUS_LATER, 
				TaskRequestBuilder.ASSIGNEE_STATUS_TODAY, 
				TaskRequestBuilder.ASSIGNEE_STATUS_UPCOMING,
				"-------"
			};
	
	private StringResources stringResources = StringResources.getInstance();
	private AsanaTerminal terminal;
	private Workspaces availableWorkspaces;
	private Workspace selectedWorkspaceObject;

	private Projects availableProjectsInWorkspace;
	private Project selectedProjectObject;
	
	private Users usersFromSelectedProject;
	private TaskEnhancedList tasksFromSelectedProject;
	
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
	
	public void logOutAsana(){
		terminal = null;
		availableWorkspaces = null;
		selectedWorkspaceObject = null;
		availableProjectsInWorkspace = null;
		selectedProjectObject = null;
		usersFromSelectedProject = null;
		tasksFromSelectedProject = null;
		selectedTaskObject = null;
		currentUser = null;
		
		System.gc();
	}
	
	//private
		
	private Tasks downloadTasksWithSubtasks(Tasks results, Tasks lastAdded){
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
			return downloadTasksWithSubtasks(temp2, temp);
		}
	}
	
	private void downloadTasksFromSelectedProject() {
		//Pobiera tylko g³ówne taski
		Tasks listOfTasks = terminal.projects().getTasks(selectedProjectObject.id, 
				ADDITIONAL_TASK_FIELD_NAMES_TO_TASK_LIST);
		
		//Pobiera wszystkie taski
		listOfTasks = downloadTasksWithSubtasks(listOfTasks, listOfTasks);
		tasksFromSelectedProject = new TaskEnhancedList(listOfTasks);
	}
	
	private void downloadUsersFromSelectedProject() {
		usersFromSelectedProject = terminal.projects().getProject(selectedProjectObject.id).members;
	}
	
	private Task downloadFullTaskObject(long id){
		return terminal.tasks().getTask(id);
	}
	
	private void setAvailableProjectsInWorkspace(Projects projects){
		this.availableProjectsInWorkspace = projects;
	}
	
	private Projects downloadWorkspaceProjects(long workspaceId, boolean archived){
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
		setAvailableProjectsInWorkspace(downloadWorkspaceProjects(selectedWorkspaceObject.id, SHOW_ARCHIVED_PROJECTS));
	}
	
	public void setSelectedProjectObject(int index) {
		this.selectedProjectObject = availableProjectsInWorkspace.get(index);
		downloadTasksFromSelectedProject();
		downloadUsersFromSelectedProject();
	}

	public Project getSelectedProjectObject() {
		return selectedProjectObject;
	}

	public Users getUsersFromSelectedProject() {
		return usersFromSelectedProject;
	}

	public Tasks getTasksFromSelectedProject() {
		return tasksFromSelectedProject.getAllTasks();
	}

	public Tasks getUserTasksFromSelectedProject() {
		return tasksFromSelectedProject.getUserTasks();
	}
	
	public Tasks getUserTasksFromSelectedProject_NoSubtasks() {
		return tasksFromSelectedProject.getUserTasks_NoSubtasks();
	}

	public Tasks getSectionsFromSelectedProject() {
		return tasksFromSelectedProject.getSections();
	}
	
	public long getSectionWhichContainsTask(long taskId){
		return tasksFromSelectedProject.getTaskSection(taskId);
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Projects getAvailableProjectsInWorkspace() {
		return availableProjectsInWorkspace;
	}
	
	public void setCurrentTaskUnselected(){
		this.selectedTaskObject = null;
	}

	public void setSelectedTaskObject(int i) {
		this.selectedTaskObject = downloadFullTaskObject(tasksFromSelectedProject.get(i).id);
	}
	
	public void setSelectedTaskObjectById(long id) {
		int index=-1;
		
		for(int i=0; i<tasksFromSelectedProject.size(); i++){
			if(tasksFromSelectedProject.get(i).id == id){
				index = i;
				break;
			}
		}
		
		if(index > -1)
			this.selectedTaskObject = downloadFullTaskObject(tasksFromSelectedProject.get(index).id);
	}

	public Task getSelectedTaskObject() {
		return this.selectedTaskObject;
	}

	public void createTask(Long section_NEW, String taskName_NEW,
			Long assignee_NEW, String assigneeStatus_NEW,
			Boolean isComplete_NEW, String duedate_NEW,
			String notes_NEW) {
		Tasks sectionsFromSelectedProject = tasksFromSelectedProject.getSections();
		
		long parentProject = selectedProjectObject.id;
		
		TaskRequestBuilderFixed builder = new TaskRequestBuilderFixed();
		
		if(section_NEW != null){
			builder.parent(section_NEW);
			Task selectedSection;
			
			for(int i=0; i<sectionsFromSelectedProject.size(); i++ ){
				if(sectionsFromSelectedProject.get(i).id == section_NEW){
					selectedSection = sectionsFromSelectedProject.get(i);
					builder.workspace(selectedSection.workspace.id);
					
					//Where is LINQ :<
					for(int j=0; j<selectedSection.projects.size(); j++){
						builder.addProject(selectedSection.projects.get(j).id);
					}
				}
			}
		}else{
			builder.workspace(selectedWorkspaceObject.id);
			builder.addProject(parentProject);
		}
		
		if(assignee_NEW != null)
			builder.assignee(assignee_NEW);
		
		if(assigneeStatus_NEW != null)
			builder.assigneeStatus(assigneeStatus_NEW);
		
		if(duedate_NEW != null)
			builder.dueOn(duedate_NEW);
		
		
		builder.name(taskName_NEW);
		builder.completed(isComplete_NEW);
		
		if(notes_NEW != null)
			builder.notes(notes_NEW);
		
		builder.build();
		
		terminal.tasks().createTask(builder);
		
		//update offline tasks list
		downloadTasksFromSelectedProject();
	}

	public void updateSelectedTask(Long section_NEW, String taskName_NEW,
			Long assignee_NEW, String assigneeStatus_NEW,
			Boolean isComplete_NEW, String duedate_NEW, 
			String notes_NEW) {
		Tasks sectionsFromSelectedProject = tasksFromSelectedProject.getSections();
		
		TaskRequestBuilderFixed builder = new TaskRequestBuilderFixed();
		
		if(section_NEW != null){
			builder.parent(section_NEW);
			Task selectedSection;
			
			for(int i=0; i<sectionsFromSelectedProject.size(); i++ ){
				if(sectionsFromSelectedProject.get(i).id == section_NEW){
					selectedSection = sectionsFromSelectedProject.get(i);
					builder.workspace(selectedSection.workspace.id);
					
					//Where is LINQ :<
					for(int j=0; j<selectedSection.projects.size(); j++){
						builder.addProject(selectedSection.projects.get(j).id);
					}
				}
			}
		}
			
		if(assignee_NEW != null)
			builder.assignee(assignee_NEW);
		
		if(assigneeStatus_NEW != null)
			builder.assigneeStatus(assigneeStatus_NEW);
		
		if(duedate_NEW != null)
			builder.dueOn(duedate_NEW);
		
		if(taskName_NEW != null)
			builder.name(taskName_NEW);
		
		if(isComplete_NEW != null)
			builder.completed(isComplete_NEW);
		
		if(notes_NEW != null)
			builder.notes(notes_NEW);

		builder.build();
		
		terminal.tasks().updateTask(selectedTaskObject.id, builder);
		
		//update offline tasks list
		downloadTasksFromSelectedProject();
	}

	public void deleteCurrentTask() {
		terminal.tasks().deleteTask(selectedTaskObject.id);
		
		//update offline tasks list
		downloadTasksFromSelectedProject();
		
		//remove association to selected task
		selectedTaskObject = null;
	}



	public String[] getAssigneeStatusesStrings() {
		return assigneeStatusesStrings;
	}
}
