package eclipseasana.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import models.TaskEnhancedList;
import net.joelinn.asana.projects.Projects;
import net.joelinn.asana.tasks.Task;
import net.joelinn.asana.tasks.Tasks;
import net.joelinn.asana.users.User;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import customControls.ValueBasedCombo;
import functionalities.ApplicationContext;
import resources.StringResources;

public class ProjectsUI {
	private StringResources stringResources = StringResources.getInstance();
	
	private Composite verticalPanel;
	private RowLayout verticalPanelLayout;
		private Label projectsComboLabel;
		private Combo projectsCombo;
		
		
		private Group taskGroup;
		private RowLayout taskGroupLayout;
			private Label sectionsComboLabel;
			private ValueBasedCombo sectionsCombo;
			private Composite horizontalPanel_myTasksOnly;
			private RowLayout horizontalPanel_myTasksOnlyLayout;
				private Button myTasksOnlyCheckbox;
				private Label myTasksOnlyCheckboxLabel;
		
			private Label tasksTreeLabel;
			private Tree tasksTree;
			
			private Button openTaskButton;
				private MessageBox informationMessageBox;
			private Button createTaskButton;
			private Button deleteTaskButton;
				private MessageBox deleteConfirmMessageBox;
		
	private boolean isMyTaskOnlyFilterChecked = false;
	private long sectionFilter_SectionId = -1;
	private ApplicationContext appContext;
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Init
	
	public ProjectsUI(Composite parent, int style, ApplicationContext ctx) {
		this.appContext = ctx;
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
		
		taskGroup = new Group(parent, style);
		taskGroupLayout = new RowLayout();
		taskGroupLayout.type = SWT.VERTICAL;
		taskGroup.setText(stringResources.getTaskGroupEtiquette());
		taskGroup.setSize(350, 600);
		
			sectionsComboLabel = new Label(taskGroup, style);
			sectionsComboLabel.setText(stringResources.getSectionsComboText());
			sectionsCombo = new ValueBasedCombo(new Combo(taskGroup, SWT.BORDER));
			
			horizontalPanel_myTasksOnly = new Composite(taskGroup, style);
			horizontalPanel_myTasksOnlyLayout = new RowLayout();
			horizontalPanel_myTasksOnlyLayout.type = SWT.HORIZONTAL;
			horizontalPanel_myTasksOnly.setLayout(horizontalPanel_myTasksOnlyLayout);
			horizontalPanel_myTasksOnly.setSize(300, 20);
				
				myTasksOnlyCheckbox = new Button(horizontalPanel_myTasksOnly, SWT.CHECK);
				myTasksOnlyCheckboxLabel = new Label(horizontalPanel_myTasksOnly, style);
				myTasksOnlyCheckboxLabel.setText(stringResources.getMyTasksOnlyCheckboxLabelText());
			
			tasksTreeLabel = new Label(taskGroup, style);
			tasksTreeLabel.setText(stringResources.getTaskGroupEtiquette());
			tasksTree = new Tree(taskGroup, SWT.BORDER | SWT.MULTI);
			//tasksTree.setBounds(20, 40, 300, 400);
			
			openTaskButton = new Button(taskGroup, SWT.PUSH);
			openTaskButton.setText(stringResources.getOpenTaskButtonText());
			createTaskButton = new Button(taskGroup, SWT.PUSH);
			createTaskButton.setText(stringResources.getCreateTaskButtonText());
			deleteTaskButton = new Button(taskGroup, SWT.PUSH);
			deleteTaskButton.setText(stringResources.getDeleteTaskButtonText());
				deleteConfirmMessageBox = new MessageBox(parent.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				informationMessageBox = new MessageBox(parent.getShell(), SWT.ICON_INFORMATION | SWT.OK );
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//UI visibility
	
	public void clear(){
		tasksTree.removeAll();
		myTasksOnlyCheckbox.setSelection(false);
		sectionsCombo.removeAll();
		projectsCombo.removeAll();
	}

	public void setViewVisibility(boolean value){
		verticalPanel.setVisible(value);
	}

	public void setViewEnableState(boolean b) {
		projectsCombo.setEnabled(b);
	}
	
	public void setProjectDetailsSectionVisibility(boolean visible){
		this.taskGroup.setVisible(visible);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Feeding UI controls with context data
	
	public void feedProjectsCombo(){
		feedProjectsCombo(appContext.getAvailableProjectsInWorkspace());
	}
	
	private void feedProjectsCombo(Projects projects){
		String[] projectNames = new String[projects.size()];
		
		for(int i=0; i<projects.size(); i++){
			projectNames[i] = projects.get(i).name;
		}
		
		projectsCombo.setItems(projectNames);
	}
	
	public void feedTaskSection(){
		feedTaskTree(appContext.getUserTasksFromSelectedProject());
		feedSectionsCombo(appContext.getSectionsFromSelectedProject(), true);
	}
	
	private void feedTaskTree(Tasks tasks){
		List<TreeItem> items = new ArrayList<>();
		tasksTree.removeAll();
		
		for (int i=0; i<tasks.size(); i++) {
			
			if(tasks.get(i).parent == null){
				
				//Assignee filter
				if(isMyTaskOnlyFilterChecked){
					if(tasks.get(i).assignee == null){
						continue;
					}else{
						if(tasks.get(i).assignee.id != appContext.getCurrentUser().id){
							continue;
						}
					}
				}
				
				//Section filter
				if(sectionFilter_SectionId > -1 && !isTaskIncludedInSection(tasks.get(i).id, sectionFilter_SectionId))
					continue;
				
				//Create tree items
				TreeItem item = new TreeItem (tasksTree, SWT.NONE);
				item.setText (tasks.get(i).name);
				item.setData(tasks.get(i).id);
				
				items.add(item);
				items.addAll(getChildrenTreeForRoot(tasks, item, new ArrayList<TreeItem>()));
			}
		}
	}
	
	private boolean isTaskIncludedInSection(long taskId, long sectionId){
		return appContext.getSectionWhichContainsTask(taskId) == sectionId;
	}
	
	private void feedSectionsCombo(Tasks sections, boolean emptyItem){
		Task element;
		ArrayList<SimpleEntry<String, String>> comboItemList = new ArrayList<>();

		for(int i=0; i<sections.size(); i++){
			element = sections.get(i);
			comboItemList.add(new SimpleEntry<String, String>(Long.toString(element.id), element.name));
		}
		
		if(emptyItem){
			comboItemList.add(new SimpleEntry<String, String>("", stringResources.getEmptySectionComboItemText()));
		}
		
		this.sectionsCombo.setComboKeyValItemList(comboItemList);
	}
	
	private Collection<? extends TreeItem> getChildrenTreeForRoot(Tasks tasks, TreeItem item, List<TreeItem> accumulator) {
		List<TreeItem> current = new ArrayList<TreeItem>(accumulator);
		
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).parent != null){
				if(tasks.get(i).parent.id == (long)(item.getData())){
					TreeItem localRoot = new TreeItem(item, SWT.NONE);
					localRoot.setText(tasks.get(i).name);
					localRoot.setData(tasks.get(i).id);
					
					current.add(localRoot);
					current.addAll(getChildrenTreeForRoot(tasks, localRoot, current));
				}
			}
		}
		
		return current;
	}

	public boolean isMyTaskOnlyFilterChecked() {
		return isMyTaskOnlyFilterChecked;
	}

	public void setMyTaskOnlyFilterChecked(boolean isMyTaskOnlyFilterChecked) {
		this.isMyTaskOnlyFilterChecked = isMyTaskOnlyFilterChecked;
	}
	
	public long getSectionFilter_SectionId() {
		return sectionFilter_SectionId;
	}

	public void setSectionFilter_SectionId(long sectionFilter_SectionId) {
		if(sectionFilter_SectionId > -1)
			this.sectionFilter_SectionId = sectionFilter_SectionId;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Controls getters
	
	public Combo getProjectsCombo() {
		return projectsCombo;
	}

	public ValueBasedCombo getSectionsCombo() {
		return sectionsCombo;
	}

	public Button getMyTasksOnlyCheckbox() {
		return myTasksOnlyCheckbox;
	}

	public Tree getTasksTree() {
		return tasksTree;
	}

	public Button getOpenTaskButton() {
		return openTaskButton;
	}

	public Button getCreateTaskButton() {
		return createTaskButton;
	}

	public Button getDeleteTaskButton() {
		return deleteTaskButton;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Modals
	
	public int showDeleteConfirmationMessageBox() {
		deleteConfirmMessageBox.setText(stringResources.getDeleteConfirmationMessageBoxTitleText());
		deleteConfirmMessageBox.setMessage(stringResources.getDeleteConfirmationMessageBoxMessageText());
		
		return deleteConfirmMessageBox.open();
	}
	
	public int showDeleteSuccessInfoMessageBox() {
		informationMessageBox.setText(stringResources.getDeleteSuccessInfoMessageBoxTitleText());
		informationMessageBox.setMessage(stringResources.getDeleteSuccessInfoMessageBoxMessageText());
		
		return informationMessageBox.open();
	}
	
	public int showTaskSelectInfoMessageBox() {
		informationMessageBox.setText(stringResources.getTaskSelectInfoMessageBoxTitleText());
		informationMessageBox.setMessage(stringResources.getTaskSelectInfoMessageBoxMessageText());
		
		return informationMessageBox.open();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DAO operations

	public void selectTask(){
		TreeItem[] selectedItem = tasksTree.getSelection();
		appContext.setSelectedTaskObjectById((long)(selectedItem[0].getData()));
	}
	
	public void unSelectTask(){
		appContext.setCurrentTaskUnselected();
	}
}
