package eclipseasana.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.part.ViewPart;

import functionalities.ApplicationContext;
import resources.StringResources;

//import functionalities.LoginHandler;


public class AsanaConnector extends ViewPart {

	public static final String ID = "eclipseasana.views.AsanaConnector";
	private StringResources stringResources = StringResources.getInstance();
	private LoginUI loginPanel;
	private WorkspacesUI workspacesPanel;
	private ProjectsUI projectsPanel;
	private ApplicationContext appContext = new ApplicationContext();
	private Composite mainViewPanel;
	private ViewManager viewManager;

	public void createPartControl(Composite parent) {
		//stack layout is good for many forms in one view
		//parent.setLayout(new FillLayout());
		mainViewPanel = new Composite(parent, SWT.NONE);
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		mainViewPanel.setLayout(rowLayout);

		//Init all views
		loginPanel = new LoginUI(mainViewPanel, SWT.NONE);
		workspacesPanel = new WorkspacesUI(mainViewPanel, SWT.NONE);
		projectsPanel = new ProjectsUI(mainViewPanel, SWT.NONE);
		viewManager = new ViewManager(loginPanel, workspacesPanel, projectsPanel);
		
		//Set current perspective
		viewManager.setView(AbstractView.BEFORE_LOGIN);

		loginPanel.getLoginButton().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.initAsana(loginPanel.getKeyTextField().getText(), loginPanel.getEmailTextField().getText());
						workspacesPanel.feedWorkspacesCombo(appContext.getAvailableWorkspaces());
						loginPanel.setLoginSuccessMessage(appContext.getCurrentUser().name);
						viewManager.setView(AbstractView.AFTER_LOGIN);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		workspacesPanel.getWorkspacesCombo().addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.setSelectedWorkspaceObject(workspacesPanel.getWorkspacesCombo().getSelectionIndex());
						projectsPanel.feedProjectsCombo(appContext.getAvailableProjectsInWorkspace());
						viewManager.setView(AbstractView.WORKSPACE_PROJECT_SELECTION);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			} 
		});
		
		projectsPanel.getProjectsCombo().addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.setSelectedWorkspaceObject(workspacesPanel.getWorkspacesCombo().getSelectionIndex());
						
						//
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		//Dodaj przycisk "przegl¹daj taski", który ukryje ca³y widok dotychczasowy i odkryje widok listy tasków;
		
		//Dodaj akcjê wyboru szczegó³ów taska, która ukryje to co by³o do tej pory w widoku i poka¿e formatkê szczegó³ów taska
		
		
		
	}

	public void setFocus() {
		loginPanel.getEmailTextField().setFocus();
		
	}

}