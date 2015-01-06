package eclipseasana.views;

import net.joelinn.asana.projects.Projects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import resources.StringResources;

public class ProjectsUI {
	private StringResources stringResources = StringResources.getInstance();
	private Composite verticalPanel;
	private Label projectsComboLabel;
	private Combo projectsCombo;
	private RowLayout verticalPanelLayout;
	
	public ProjectsUI(Composite parent, int style) {
		this.verticalPanel = new Composite(parent, style);
		this.verticalPanelLayout = new RowLayout();
		this.verticalPanelLayout.type = SWT.VERTICAL;
		this.verticalPanel.setLayout(verticalPanelLayout);
		
		addControls(verticalPanel, style);
	}
	
	private void addControls(Composite parent, int style) {
		projectsComboLabel = new Label(parent, style);
		projectsComboLabel.setText(stringResources.getProjectsComboText());
		projectsCombo = new Combo(parent, SWT.BORDER);
	}

	public void setViewVisibility(boolean value){
		verticalPanel.setVisible(value);
	}

	public void setViewEnableState(boolean b) {
		projectsCombo.setEnabled(b);
	}
	
	public void feedProjectsCombo(Projects projects){
		String[] projectNames = new String[projects.size()];
		
		for(int i=0; i<projects.size(); i++){
			projectNames[i] = projects.get(i).name;
		}
		
		projectsCombo.setItems(projectNames);
	}

	public Combo getProjectsCombo() {
		return projectsCombo;
	}
}
