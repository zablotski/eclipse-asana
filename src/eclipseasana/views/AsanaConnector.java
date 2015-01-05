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
		
		//Set view
		viewManager.setView(AbstractView.BEFORE_LOGIN);

		loginPanel.getLoginButton().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.initAsana(loginPanel.getKeyTextField().getText(), loginPanel.getEmailTextField().getText());
						viewManager.setView(AbstractView.AFTER_LOGIN);
						workspacesPanel.feedWorkspacesCombo(appContext.getAvailableWorkspaces());
						loginPanel.setLoginSuccessMessage(appContext.getCurrentUser().name);
						
						
						
						//Dodaj przycisk "przegl�daj taski", kt�ry ukryje ca�y widok dotychczasowy i odkryje widok listy task�w;
						
						//Dodaj akcj� wyboru szczeg��w taska, kt�ra ukryje to co by�o do tej pory w widoku i poka�e formatk� szczeg��w taska
						
						//
						
						/*System.out.println("Your email: "
								+ asana.users().getMe().email + ", your Name: "
								+ asana.users().getMe().name);
						loginResult.setText("Your email: "
								+ asana.users().getMe().email + ", your Name: "
								+ asana.users().getMe().name); //jak zakomentować powyższe, to wyświetli, dlaczego, to ja nie wiem =)*/
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});

	}

	public void setFocus() {
		loginPanel.getEmailTextField().setFocus();
		
	}

}