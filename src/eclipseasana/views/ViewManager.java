package eclipseasana.views;

public class ViewManager {
	private LoginUI loginForm;
	private WorkspacesUI workspacesForm;
	private ProjectsUI projectForm;
	
	public ViewManager(LoginUI l, WorkspacesUI w, ProjectsUI p){
		loginForm = l;
		workspacesForm = w;
		projectForm = p;
	}
	
	public void setView(AbstractView view){
		switch(view){
		case BEFORE_LOGIN:
			loginForm.setViewVisibility(true);
			loginForm.setLoginState();
			workspacesForm.setViewVisibility(false);
			projectForm.setViewVisibility(false);
			break;
		case AFTER_LOGIN:
			loginForm.setViewVisibility(true);
			loginForm.setLogoutState();
			workspacesForm.setViewVisibility(true);
			projectForm.setViewVisibility(true);
			projectForm.setViewEnableState(false);
			break;
		case WORKSPACE_PROJECT_SELECTION:
			loginForm.setViewVisibility(true);
			loginForm.setLogoutState();
			workspacesForm.setViewVisibility(true);
			projectForm.setViewVisibility(true);
			projectForm.setViewEnableState(true);
			break;
		case PROJECT_DETAILS:
			break;
		case TASK_DETAILS:
			break;
		}
	}
}
