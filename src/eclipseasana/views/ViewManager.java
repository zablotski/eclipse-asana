package eclipseasana.views;

public class ViewManager {
	private LoginUI loginForm;
	private WorkspacesUI workspacesForm;
	private ProjectsUI projectForm;
	private TaskUI taskForm;	
	
	public ViewManager(LoginUI l, WorkspacesUI w, ProjectsUI p, TaskUI t){
		loginForm = l;
		workspacesForm = w;
		projectForm = p;
		taskForm = t;
	}
	
	public void setView(AbstractView view){
		switch(view){
		case BEFORE_LOGIN:
			loginForm.setViewVisibility(true);
			loginForm.setLoginState();
			workspacesForm.setViewVisibility(false);
			projectForm.setViewVisibility(false);
			taskForm.setViewVisibility(false);
			break;
		case AFTER_LOGIN:
			loginForm.setViewVisibility(true);
			loginForm.setLogoutState();
			workspacesForm.setViewVisibility(true);
			projectForm.setViewVisibility(true);
			projectForm.setViewEnableState(false);
			projectForm.setProjectDetailsSectionVisibility(false);
			taskForm.setViewVisibility(false);
			break;
		case WORKSPACE_PROJECT_SELECTION:
			loginForm.setViewVisibility(true);
			loginForm.setLogoutState();
			workspacesForm.setViewVisibility(true);
			projectForm.setViewVisibility(true);
			projectForm.setViewEnableState(true);
			projectForm.setProjectDetailsSectionVisibility(false);
			taskForm.setViewVisibility(false);
			break;
		case PROJECT_DETAILS:
			loginForm.setViewVisibility(true);
			loginForm.setLogoutState();
			workspacesForm.setViewVisibility(true);
			projectForm.setViewVisibility(true);
			projectForm.setViewEnableState(true);
			projectForm.setProjectDetailsSectionVisibility(true);
			taskForm.setViewVisibility(false);
			break;
		case TASK_DETAILS:
			loginForm.setViewVisibility(false);
			workspacesForm.setViewVisibility(false);
			projectForm.setViewVisibility(false);
			taskForm.setViewVisibility(true);
			break;
		}
	}
}
