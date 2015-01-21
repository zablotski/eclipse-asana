package eclipseasana.views;

import net.joelinn.asana.workspaces.Workspaces;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import functionalities.ApplicationContext;
import resources.StringResources;

public class WorkspacesUI {
	private StringResources stringResources = StringResources.getInstance();
	private Composite verticalPanel;
	private Label workspacesComboLabel;
	private Combo workspacesCombo;
	private RowLayout verticalPanelLayout;
	private ApplicationContext appContext;

	public WorkspacesUI(Composite parent, int style, ApplicationContext appContext) {
		this.appContext = appContext;
		this.verticalPanel = new Composite(parent, style);
		this.verticalPanelLayout = new RowLayout();
		this.verticalPanelLayout.type = SWT.VERTICAL;
		this.verticalPanel.setLayout(verticalPanelLayout);
		
		addControls(verticalPanel, style);
	}

	private void addControls(Composite parent, int style) {
		{
			workspacesComboLabel = new Label(parent, SWT.NONE);
			RowData workspacesComboLabelLData = new RowData();
			workspacesComboLabelLData.width = 210;
			workspacesComboLabelLData.height = 15;
			workspacesComboLabel.setLayoutData(workspacesComboLabelLData);
			workspacesComboLabel.setText("Select Workspace");
		}
	
		{
			workspacesCombo = new Combo(parent, SWT.NONE);
			RowData workspacesComboLData = new RowData();
			workspacesComboLData.width = 211;
			workspacesComboLData.height = 27;
			workspacesCombo.setLayoutData(workspacesComboLData);
			workspacesCombo.setText("Select Workspace");
		}
	}
	
	public void feedWorkspacesCombo(){
		feedWorkspacesCombo(appContext.getAvailableWorkspaces());
	}
	
	private void feedWorkspacesCombo(Workspaces workspaces){
		String[] workspaceNames = new String[workspaces.size()];
		
		for(int i=0; i<workspaces.size(); i++){
			workspaceNames[i] = workspaces.get(i).name;
		}
		
		workspacesCombo.setItems(workspaceNames);
	}
	
	public void setViewVisibility(boolean value){
		verticalPanel.setVisible(value);
	}

	public Combo getWorkspacesCombo() {
		return workspacesCombo;
	}
	
	public void clear(){
		workspacesCombo.removeAll();
	}

}
