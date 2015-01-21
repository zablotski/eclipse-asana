package eclipseasana.views;

import java.util.Calendar;
import net.joelinn.asana.tasks.Task;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import resources.StringResources;
import eclipseasana.exceptions.SaveTaskException;
import functionalities.ApplicationContext;

public class TaskUI {
	private StringResources stringResources = StringResources.getInstance();
	private Calendar calendar = Calendar.getInstance();
	
	private Composite verticalPanel;
	private RowLayout verticalPanelLayout;
		private Composite taskPropertiesGridPanel;
		private GridLayout taskPropertiesGridPanelLayout;
			private Label taskTitleLabel;
			private Text taskTitleTextBox;
			private Label assigneeComboLabel;
			private Combo assigneeCombo;
			private Label assigneeStatusComboLabel;
			private Combo assigneeStatusCombo;
			private Label sectionComboLabel;
			private Combo sectionCombo;
			private Label completedCheckboxLabel;
			private Button completedCheckbox;
			private Label dueDateCalendarLabel;
			private Text dueDateCalendar;
		private Composite taskNotesRowPanel;
		private RowLayout taskNotesRowPanelLayout;
			private Label notesTextAreaLabel;
			private Text notesTextArea;
		private Composite buttonRowPanel;
		private RowLayout buttonRowPanelLayout;
			private Button saveTaskButton;
			private Button cancelTaskButton;
			private Button addSubtaskButton;
				private MessageBox informationMessageBox;
			private InputDialog newSubtaskNameInputDialog;
		
	private ApplicationContext appContext;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Init
			
	public TaskUI(Composite parent, int style, ApplicationContext ctx) {
		this.appContext = ctx;
		this.verticalPanel = new Composite(parent, style);
		this.verticalPanelLayout = new RowLayout();
		this.verticalPanelLayout.type = SWT.VERTICAL;
		this.verticalPanel.setLayout(verticalPanelLayout);
		
		addControls(verticalPanel, style);
	}
	
	private void addControls(Composite parent, int style) {
		taskPropertiesGridPanel = new Composite(parent, style);
		taskPropertiesGridPanelLayout = new GridLayout(2, true);
		taskPropertiesGridPanel.setLayout(taskPropertiesGridPanelLayout);
			taskTitleLabel = new Label(taskPropertiesGridPanel, style);
			taskTitleLabel.setText(stringResources.getTaskNameInputLabelText());
			
			taskTitleTextBox = new Text(taskPropertiesGridPanel, SWT.BORDER);
			
			assigneeComboLabel = new Label(taskPropertiesGridPanel, style);
			assigneeComboLabel.setText(stringResources.getAssigneeComboLabelText());
			
			assigneeCombo = new Combo(taskPropertiesGridPanel, style);
			
			assigneeStatusComboLabel = new Label(taskPropertiesGridPanel, style);
			assigneeStatusComboLabel.setText(stringResources.getAssigneeStatusComboLabelText());
			
			assigneeStatusCombo = new Combo(taskPropertiesGridPanel, style);
			
			sectionComboLabel = new Label(taskPropertiesGridPanel, style);
			sectionComboLabel.setText(stringResources.getSectionsComboText());
			
			sectionCombo = new Combo(taskPropertiesGridPanel, style);
			
			completedCheckboxLabel = new Label(taskPropertiesGridPanel, style);
			completedCheckboxLabel.setText(stringResources.getDoneButtonCheckLabelText());
			
			completedCheckbox = new Button(taskPropertiesGridPanel, SWT.CHECK);
			
			dueDateCalendarLabel = new Label(taskPropertiesGridPanel, style);
			dueDateCalendarLabel.setText(stringResources.getDuedateCalendarLabelText());
			
			dueDateCalendar = new Text(taskPropertiesGridPanel, SWT.BORDER);
			dueDateCalendar.setEnabled(false);
			dueDateCalendar.addMouseListener(new MouseListener() {

				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				
			});

		taskNotesRowPanel = new Composite(parent, style);
		taskNotesRowPanelLayout = new RowLayout(SWT.VERTICAL);
		taskNotesRowPanel.setLayout(taskNotesRowPanelLayout);
			notesTextAreaLabel = new Label(taskNotesRowPanel, style);
			notesTextAreaLabel.setText(stringResources.getNotesTextareaLabelText());
			
			notesTextArea = new Text(taskNotesRowPanel, SWT.MULTI);
		buttonRowPanel = new Composite(parent, style);
		buttonRowPanelLayout = new RowLayout(SWT.HORIZONTAL);
		buttonRowPanel.setLayout(buttonRowPanelLayout);
			saveTaskButton = new Button(buttonRowPanel, SWT.PUSH);
			cancelTaskButton = new Button(buttonRowPanel, SWT.PUSH);
			addSubtaskButton = new Button(buttonRowPanel, SWT.PUSH);
			informationMessageBox = new MessageBox(parent.getShell(), SWT.ICON_INFORMATION | SWT.OK );
			newSubtaskNameInputDialog = new InputDialog(parent.getShell(), 
					SWT.ICON_INFORMATION | SWT.OK | SWT.CANCEL, 
					stringResources.getAddSubtaskInputDialogTitleText(), 
					stringResources.getAddSubtaskInputDialogMessageText());
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//UI visibility
	
	public void clear(){
		taskTitleTextBox.setText("");
		assigneeCombo.removeAll();
		assigneeStatusCombo.removeAll();
		sectionCombo.removeAll();
		completedCheckbox.setSelection(false);
		dueDateCalendar.setText("");
		notesTextArea.setText("");
	}

	public void setViewVisibility(boolean value){
		verticalPanel.setVisible(value);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Feeding UI controls with context data
	
	public void setSelectedTaskData(){
		Task t = appContext.getSelectedTaskObject();
		
		feedAssigneeCombo();
		feedAssigneeStatusCombo();
		feedSectionCombo();
		
		if(t.name != null)
			taskTitleTextBox.setText(t.name);
		
		if(t.assignee == null){
			assigneeCombo.select(assigneeCombo.getItemCount()-1);
		}else{
			for(int i=0; i<appContext.getUsersFromSelectedProject().size(); i++){
				if(appContext.getUsersFromSelectedProject().get(i).id == t.assignee.id){
					assigneeCombo.select(i);
				}
			}
		}
		
		assigneeStatusCombo.select(assigneeStatusCombo.getItemCount()-1);
		if(t.assigneeStatus != null){
			if(t.assigneeStatus != ""){
				for(int i=0; i<appContext.getAssigneeStatusesStrings().length; i++){
					if(appContext.getAssigneeStatusesStrings()[i].equals(t.assigneeStatus)){
						assigneeStatusCombo.select(i);
					}
				}
			}
		}
		
		sectionCombo.select(sectionCombo.getItemCount()-1);
		if(appContext.getSectionWhichContainsTask(t.id) > -1){
			for(int i=0; i<appContext.getSectionsFromSelectedProject().size(); i++){
				if(appContext.getSectionsFromSelectedProject().get(i).id == appContext.getSectionWhichContainsTask(t.id)){
					sectionCombo.select(i);
				}
			}
		}
		
		if(t.dueOn != null)
			dueDateCalendar.setText(t.dueOn);
		
		if(t.notes != null)
			notesTextArea.setText(t.notes);
		
		completedCheckbox.setSelection(t.completed);
	}

	private void feedSectionCombo() {
		String[] items = new String[appContext.getSectionsFromSelectedProject().size() + 1];
		for(int i=0; i<appContext.getSectionsFromSelectedProject().size(); i++){
			items[i] = appContext.getSectionsFromSelectedProject().get(i).name;
		}
		items[appContext.getSectionsFromSelectedProject().size()] = "-------";
		sectionCombo.setItems(items);
	}

	private void feedAssigneeStatusCombo() {
		assigneeStatusCombo.setItems(appContext.getAssigneeStatusesStrings());
	}

	private void feedAssigneeCombo() {
		String[] items = new String[appContext.getUsersFromSelectedProject().size()+1];
		for(int i=0; i<appContext.getUsersFromSelectedProject().size(); i++){
			items[i] = appContext.getUsersFromSelectedProject().get(i).name;
		}
		items[appContext.getUsersFromSelectedProject().size()] = "-------";
		assigneeCombo.setItems(items);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Controls getters
	
	public Button getSaveTaskButton() {
		return saveTaskButton;
	}

	public Button getCancelTaskButton() {
		return cancelTaskButton;
	}

	public Button getAddSubtaskButton() {
		return addSubtaskButton;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Modals

	public int showErrorAddSubtaskToNewTaskInfoMessageBox() {
		informationMessageBox.setText(stringResources.getErrorAddSubtaskToNewTaskInfoMessageBoxTitleText());
		informationMessageBox.setMessage(stringResources.getErrorAddSubtaskToNewTaskInfoMessageBoxMessageText());
		
		return informationMessageBox.open();
	}
	
	public String showNewSubtaskInputDialog(){
		return newSubtaskNameInputDialog.open();
	}

	public int showTaskSuccesfullyAddedMessageBox() {
		informationMessageBox.setText(stringResources.getSubTaskCreatedInfoMessageBoxTitleText());
		informationMessageBox.setMessage(stringResources.getSubTaskCreatedInfoMessageBoxMessageText());
		
		return informationMessageBox.open();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DAO operations

	public void saveTask() throws SaveTaskException{
		try{
			if(appContext.getSelectedTaskObject() == null){
				
				appContext.createTask(
							getSelectedSectionID(), 
							getTypedTaskName(),
							getSelectedAssigneeID(), 
							getSelectedAssigneeStatusString(), 
							completedCheckbox.getSelection(), 
							getSelectedDueDate(),
							getTypedNotes()
						);
			} else {
				
				Long section_NEW = checkSectionModification()? getSelectedSectionID() : null;
				String taskName_NEW = checkTaskNameModification()? getTypedTaskName() : null;
				Long assignee_NEW = checkAssigneeModification()? getSelectedAssigneeID() : null;
				String assigneeStatus_NEW = checkAssigneeStatusModification()? getSelectedAssigneeStatusString() : null;
				Boolean isComplete_NEW = checkCompletedModification()? completedCheckbox.getSelection() : null;
				String duedate_NEW = checkDueDateModification()? getSelectedDueDate() : null;
				String notes_NEW = checkNotesModification()? getTypedNotes() : null;
				
				appContext.updateSelectedTask(
							section_NEW, 
							taskName_NEW, 
							assignee_NEW, 
							assigneeStatus_NEW, 
							isComplete_NEW, 
							duedate_NEW, 
							notes_NEW
						);
			}
		} catch (Exception e){
			throw new SaveTaskException(e);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Modifications validators (case: modyfying task)

	private boolean checkSectionModification(){
		//never null
		Long oldSectionValue = appContext.getSectionWhichContainsTask(
				(Long)(appContext.getSelectedTaskObject().id));
		if(oldSectionValue == -1){
			if(getSelectedSectionID() == null){
				return false;
			}
			return true;
		}else{
			if(getSelectedSectionID() == null){
				return false;
			}else{
				if(!getSelectedSectionID().equals(oldSectionValue)){
					return true;
				}
				return false;
			}
		}
	}

	private boolean checkTaskNameModification(){
		if(appContext.getSelectedTaskObject().name == null){
			return getTypedTaskName() != null;
		}else{
			return !appContext.getSelectedTaskObject().name.equals(getTypedTaskName());
		}
	}

	private boolean checkNotesModification(){
		if(appContext.getSelectedTaskObject().notes == null){
			return getTypedTaskName() != null;
		}else{
			return !appContext.getSelectedTaskObject().notes.equals(getTypedNotes());
		}
	}

	private boolean checkAssigneeModification(){
		if(appContext.getSelectedTaskObject().assignee == null){
			return getSelectedAssigneeID() != null;
		}else{
			return !appContext.getSelectedTaskObject().assignee.equals(getSelectedAssigneeID());
		}
	}

	private boolean checkAssigneeStatusModification(){
		if(appContext.getSelectedTaskObject().assigneeStatus == null){
			return getSelectedAssigneeStatusString() != null;
		}else{
			return !appContext.getSelectedTaskObject().assigneeStatus.equals(getSelectedAssigneeStatusString());
		}
	}

	private boolean checkDueDateModification(){
		if(appContext.getSelectedTaskObject().dueOn == null){
			return getSelectedAssigneeStatusString() != null;
		}else{
			return !appContext.getSelectedTaskObject().assigneeStatus.equals(getSelectedAssigneeStatusString());
		}
	}

	private boolean checkCompletedModification(){
		return appContext.getSelectedTaskObject().completed != completedCheckbox.getSelection();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Get constructor required information from UI controls

	private Long getSelectedSectionID(){
		Long result = null;
		
		int index = sectionCombo.getSelectionIndex();
		
		if(index < appContext.getSectionsFromSelectedProject().size() && index > -1){
			result = appContext.getSectionsFromSelectedProject().get(index).id;
		}
		
		return result;
	}
	
	private String getTypedTaskName(){
		String result = "";
		
		if(taskTitleTextBox.getText() != null){
			result = taskTitleTextBox.getText();
		}
		return result;
	}
	
	private String getTypedNotes(){
		String result = "";
		
		if(notesTextArea.getText() != null){
			result = notesTextArea.getText();
		}
		return result;
	}
	
	private Long getSelectedAssigneeID(){
		Long assigneeId = null;
		
		if(assigneeCombo.getSelectionIndex() != -1){
			if(assigneeCombo.getSelectionIndex() < assigneeCombo.getItems().length-1){
				if(assigneeCombo.getItems()[assigneeCombo.getSelectionIndex()] != ""){
					assigneeId = Long.getLong(assigneeCombo.getItem(assigneeCombo.getSelectionIndex()));
				}
			}
		}
		return assigneeId;
	}
	
	private String getSelectedAssigneeStatusString(){
		String as_status = null;
		
		if(assigneeStatusCombo.isEnabled()){
			if(assigneeStatusCombo.getSelectionIndex() != -1){
				if(assigneeStatusCombo.getSelectionIndex() < assigneeStatusCombo.getItems().length-1){
					if(assigneeStatusCombo.getItems()[assigneeStatusCombo.getSelectionIndex()] != ""){
						as_status = assigneeStatusCombo.getItem(assigneeStatusCombo.getSelectionIndex());
					}
				}
			}
		}
		return as_status;
	}
	
	private String getSelectedDueDate() {
		String result = "";
		
		if(dueDateCalendar.getText() == null){
			result = null;
		}else{
			if(dueDateCalendar.getText().equals("")){
				result = null;
			}else{
				result = dueDateCalendar.getText();
			}
		}
		
		return result;
	}
}
