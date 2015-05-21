package eclipseasana.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

import eclipseasana.exceptions.SaveTaskException;
import functionalities.ApplicationContext;
import resources.StringResources;

//import functionalities.LoginHandler;


public class AsanaConnector extends ViewPart {

	public static final String ID = "eclipseasana.views.AsanaConnector";
	private StringResources stringResources = StringResources.getInstance();
	private LoginUI loginPanel;
	private WorkspacesUI workspacesPanel;
	private ProjectsUI projectsPanel;
	private TaskUI taskPanel;
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
		loginPanel = new LoginUI(mainViewPanel, SWT.NONE, appContext);
		workspacesPanel = new WorkspacesUI(mainViewPanel, SWT.NONE, appContext);
		projectsPanel = new ProjectsUI(mainViewPanel, SWT.NONE, appContext);
		taskPanel = new TaskUI(mainViewPanel, SWT.NONE, appContext);
		viewManager = new ViewManager(loginPanel, workspacesPanel, projectsPanel, taskPanel);
		
		//Set current perspective
		viewManager.setView(AbstractView.BEFORE_LOGIN);

		loginPanel.getLoginButton().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.initAsana(loginPanel.getKeyTextField().getText(), loginPanel.getEmailTextField().getText());
						workspacesPanel.feedWorkspacesCombo();
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
		
		loginPanel.getLogoutButton().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.logOutAsana();
						loginPanel.clear();
						workspacesPanel.clear();
						projectsPanel.clear();
						viewManager.setView(AbstractView.BEFORE_LOGIN);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		workspacesPanel.getWorkspacesCombo().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.setSelectedWorkspaceObject(workspacesPanel.getWorkspacesCombo().getSelectionIndex());
						projectsPanel.feedProjectsCombo();
						viewManager.setView(AbstractView.WORKSPACE_PROJECT_SELECTION);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			} 
		});
		
		projectsPanel.getProjectsCombo().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						appContext.setSelectedProjectObject(projectsPanel.getProjectsCombo().getSelectionIndex());
						projectsPanel.feedTaskSection();
						viewManager.setView(AbstractView.PROJECT_DETAILS);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		projectsPanel.getMyTasksOnlyCheckbox().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						projectsPanel.setMyTaskOnlyFilterChecked(projectsPanel.getMyTasksOnlyCheckbox().getSelection());
						projectsPanel.feedTaskSection();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		projectsPanel.getSectionsCombo().getComboBox().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						projectsPanel.setSectionFilter_SectionId(Long.parseLong(projectsPanel.getSectionsCombo().getSelectedItemValue()));
						projectsPanel.feedTaskSection();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		projectsPanel.getOpenTaskButton().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						//Task not selected
						if(projectsPanel.getTasksTree().getSelection() == null){
							projectsPanel.showTaskSelectInfoMessageBox();
							return;
						}
						if(projectsPanel.getTasksTree().getSelection().length == 0){
							projectsPanel.showTaskSelectInfoMessageBox();
							return;
						}
						
						//Correct input scenario:
						projectsPanel.selectTask();
						taskPanel.setSelectedTaskData();
						viewManager.setView(AbstractView.TASK_DETAILS);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		projectsPanel.getCreateTaskButton().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						projectsPanel.unSelectTask();
						taskPanel.clear();
						viewManager.setView(AbstractView.TASK_DETAILS);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		projectsPanel.getDeleteTaskButton().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						//Task not selected
						if(projectsPanel.getTasksTree().getSelection() == null){
							projectsPanel.showTaskSelectInfoMessageBox();
							return;
						}
						if(projectsPanel.getTasksTree().getSelection().length == 0){
							projectsPanel.showTaskSelectInfoMessageBox();
							return;
						}
						
						//Correct input scenario:
						projectsPanel.selectTask();
						int deleteTaskConfirmationResponse = projectsPanel.showTaskSelectInfoMessageBox();
						
						if(deleteTaskConfirmationResponse == SWT.YES){
							appContext.deleteCurrentTask();
							projectsPanel.feedTaskSection();
							projectsPanel.showDeleteSuccessInfoMessageBox();
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		taskPanel.getSaveTaskButton().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.Selection:
						try {
							taskPanel.saveTask();
							taskPanel.showTaskSuccesfullyAddedMessageBox();
						} catch (Exception e1) {
							e1.getMessage();
						}
						break;
				}
			}
		});
		
		taskPanel.getAddSubtaskButton().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						//New task must be saved first before adding subtasks
						if(appContext.getSelectedTaskObject() == null){
							taskPanel.showErrorAddSubtaskToNewTaskInfoMessageBox();
							return;
						}
						
						//Correct input scenario:
						String newSubtaskName = taskPanel.showNewSubtaskInputDialog();

						appContext.createTask(
									null, 
									newSubtaskName, 
									null, 
									null, 
									null, 
									null, 
									null
								);
						
						taskPanel.showTaskSuccesfullyAddedMessageBox();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}
		});
		
		taskPanel.getCancelTaskButton().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						taskPanel.clear();
						projectsPanel.feedTaskSection();
						viewManager.setView(AbstractView.PROJECT_DETAILS);
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