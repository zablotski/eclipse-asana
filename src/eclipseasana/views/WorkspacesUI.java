package eclipseasana.views;

import net.joelinn.asana.workspaces.Workspaces;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import resources.StringResources;

public class WorkspacesUI {
	private StringResources stringResources = StringResources.getInstance();
	private Composite verticalPanel;
	private Label workspacesComboLabel;
	private Combo workspacesCombo;
	private RowLayout verticalPanelLayout;

	public WorkspacesUI(Composite parent, int style) {
		this.verticalPanel = new Composite(parent, style);
		this.verticalPanelLayout = new RowLayout();
		this.verticalPanelLayout.type = SWT.VERTICAL;
		this.verticalPanel.setLayout(verticalPanelLayout);
		
		addControls(verticalPanel, style);
	}

	private void addControls(Composite parent, int style) {
		workspacesComboLabel = new Label(parent, style);
		workspacesComboLabel.setText(stringResources.getWorkspacesComboText());
		workspacesCombo = new Combo(parent, SWT.BORDER);
	}
	
	public void feedWorkspacesCombo(Workspaces workspaces){
		String[] workspaceNames = new String[workspaces.size()];
		
		for(int i=0; i<workspaces.size(); i++){
			workspaceNames[i] = workspaces.get(i).name;
		}
		
		workspacesCombo.setItems(workspaceNames);
	}
	
	public void setViewVisibility(boolean value){
		verticalPanel.setVisible(value);
	}

}
