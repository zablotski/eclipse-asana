package resources;

public final class StringResources {
	private final static StringResources ourInstance = new StringResources();
	//loginUI
	private final static String LOGIN_LABEL_TEXT = "Your Asana API key:";
	private final static String LOGIN_BUTTON_TEXT = "Log In";
	private final static String LOGOUT_BUTTON_TEXT = "Log Out";
	private final static String LOGIN_RESULT_ERROR_TEXT = "Incorrect pair <email, key>";
	private final static String LOGIN_RESULT_SUCCESS = "Logged in as %s";
	private final static String LOGIN_EMAIL_LABEL_TEXT = "E-mail:";
	private final static String LOGIN_KEY_LABEL_TEXT = "API Key:";
	//workspacesUI
	private final static String WORKSPACES_COMBO_TEXT = "Workspace:";
	private final static String PROJECTS_COMBO_TEXT = "Projects:";
	private final static String SECTION_CONVENTION_SUFFIX = ":";
	//projectsUI
	private final static String TASK_GROUP_ETIQUETTE = "Tasks:";
	private final static String SECTIONS_COMBO_TEXT = "Section:";
	private final static String MY_TASKS_ONLY_CHECKBOX_LABEL_TEXT = "My tasks only";
	private final static String CREATE_TASK_BUTTON_TEXT = "Add Task";
	private final static String OPEN_TASK_BUTTON_TEXT = "Details";
	private final static String DELETE_TASK_BUTTON_TEXT = "Delete";
	private static final String EMPTY_SECTION_COMBO_ITEM_TEXT = "All";
	private final static String DELETE_CONFIRMATION_MESSAGE_BOX_TITLE_TEXT = "Confirmation";
	private final static String DELETE_CONFIRMATION_MESSAGE_BOX_MESSAGE_TEXT = "Are you sure permanently remove selected task?";
	private static final String TASK_SELECT_INFO_MESSAGE_BOX_MESSAGE_TEXT = "Info";
	private static final String TASK_SELECT_INFO_MESSAGE_BOX_TITLE_TEXT = "Please select task first.";
	private static final String DELETE_SUCCESS_INFO_MESSAGE_BOX_TITLE_TEXT = "Success!";
	private static final String DELETE_SUCCESS_INFO_MESSAGE_BOX_MESSAGE_TEXT = "Selected task was succesfully deleted";
	//taskUI
	private final static String CLOSE_TASK_BUTTON_TEXT = "Close";
	private final static String SAVE_TASK_BUTTON_TEXT = "Save";
	private final static String CREATE_SUBTASK_BUTTON_TEXT = "Add Subtask";
	private final static String TASK_NAME_INPUT_LABEL_TEXT = "Title";
	private final static String ASSIGNEE_STATUS_COMBO_LABEL_TEXT = "Status";
	private final static String ASSIGNEE_COMBO_LABEL_TEXT = "Assignee";
	private final static String DONE_BUTTON_CHECK_LABEL_TEXT = "Done";
	private final static String DUEDATE_CALENDAR_LABEL_TEXT = "Due On";
	private final static String NOTES_TEXTAREA_LABEL_TEXT = "Assignee";
	private final static String ADD_SUBTASK_INPUT_DIALOG_TITLE_TEXT = "New Task";
	private final static String ADD_SUBTASK_INPUT_DIALOG_MESSAGE_TEXT = "Task title:";
	private static final String ERROR_ADD_SUBTASK_TO_NEW_TASK_INFO_MESSAGE_BOX_TITLE_TEXT = "Error";
	private static final String ERROR_ADD_SUBTASK_TO_NEW_TASK_INFO_MESSAGE_BOX_MESSAGE_TEXT = "Save main task before adding subtask.";
	private static final String SUBTASK_CREATED_INFO_MESSAGE_BOX_TITLE_TEXT = "Success!";
	private static final String SUBTASK_CREATED_INFO_MESSAGE_BOX_MESSAGE_TEXT = "New sub task was succesfully added.";
    
	public static StringResources getInstance() {
        return ourInstance;
    }
    
    private StringResources() {
    	
    }
    
    public String getLoginResultSuccessText(String asanaUserName){
    	return String.format(LOGIN_RESULT_SUCCESS, asanaUserName);
    }
    
    public String getProjectsComboText(){
    	return PROJECTS_COMBO_TEXT;
    }

	public String getLoginLabelText() {
		return LOGIN_LABEL_TEXT;
	}

	public String getLoginButtonText() {
		return LOGIN_BUTTON_TEXT;
	}

	public String getLoginResultErrorText() {
		return LOGIN_RESULT_ERROR_TEXT;
	}

	public String getLoginEmailLabelText() {
		return LOGIN_EMAIL_LABEL_TEXT;
	}

	public String getLoginKeyLabelText() {
		return LOGIN_KEY_LABEL_TEXT;
	}

	public String getSectionConventionSuffix() {
		return SECTION_CONVENTION_SUFFIX;
	}

	public String getWorkspacesComboText() {
		return WORKSPACES_COMBO_TEXT;
	}

	public String getLogoutButtonText() {
		return LOGOUT_BUTTON_TEXT;
	}

	public String getLoginResultSuccess() {
		return LOGIN_RESULT_SUCCESS;
	}

	public String getTaskGroupEtiquette() {
		return TASK_GROUP_ETIQUETTE;
	}
	
	public String getSectionsComboText() {
		return SECTIONS_COMBO_TEXT;
	}

	public String getMyTasksOnlyCheckboxLabelText() {
		return MY_TASKS_ONLY_CHECKBOX_LABEL_TEXT;
	}

	public String getCreateTaskButtonText() {
		return CREATE_TASK_BUTTON_TEXT;
	}

	public String getOpenTaskButtonText() {
		return OPEN_TASK_BUTTON_TEXT;
	}

	public String getDeleteTaskButtonText() {
		return DELETE_TASK_BUTTON_TEXT;
	}

	public String getEmptySectionComboItemText() {
		return EMPTY_SECTION_COMBO_ITEM_TEXT;
	}

	public String getCloseTaskButtonText() {
		return CLOSE_TASK_BUTTON_TEXT;
	}

	public String getSaveTaskButtonText() {
		return SAVE_TASK_BUTTON_TEXT;
	}

	public String getCreateSubtaskButtonText() {
		return CREATE_SUBTASK_BUTTON_TEXT;
	}

	public String getTaskNameInputLabelText() {
		return TASK_NAME_INPUT_LABEL_TEXT;
	}

	public String getAssigneeStatusComboLabelText() {
		return ASSIGNEE_STATUS_COMBO_LABEL_TEXT;
	}

	public String getAssigneeComboLabelText() {
		return ASSIGNEE_COMBO_LABEL_TEXT;
	}

	public String getDoneButtonCheckLabelText() {
		return DONE_BUTTON_CHECK_LABEL_TEXT;
	}

	public String getDuedateCalendarLabelText() {
		return DUEDATE_CALENDAR_LABEL_TEXT;
	}

	public String getNotesTextareaLabelText() {
		return NOTES_TEXTAREA_LABEL_TEXT;
	}

	public String getDeleteConfirmationMessageBoxTitleText() {
		return DELETE_CONFIRMATION_MESSAGE_BOX_TITLE_TEXT;
	}

	public String getDeleteConfirmationMessageBoxMessageText() {
		return DELETE_CONFIRMATION_MESSAGE_BOX_MESSAGE_TEXT;
	}

	public String getTaskSelectInfoMessageBoxTitleText() {
		return TASK_SELECT_INFO_MESSAGE_BOX_TITLE_TEXT;
	}

	public String getTaskSelectInfoMessageBoxMessageText() {
		return TASK_SELECT_INFO_MESSAGE_BOX_MESSAGE_TEXT;
	}

	public String getAddSubtaskInputDialogTitleText() {
		return ADD_SUBTASK_INPUT_DIALOG_TITLE_TEXT;
	}

	public String getAddSubtaskInputDialogMessageText() {
		return ADD_SUBTASK_INPUT_DIALOG_MESSAGE_TEXT;
	}

	public String getDeleteSuccessInfoMessageBoxMessageText() {
		return DELETE_SUCCESS_INFO_MESSAGE_BOX_MESSAGE_TEXT;
	}

	public String getDeleteSuccessInfoMessageBoxTitleText() {
		return DELETE_SUCCESS_INFO_MESSAGE_BOX_TITLE_TEXT;
	}

	public String getErrorAddSubtaskToNewTaskInfoMessageBoxMessageText() {
		return ERROR_ADD_SUBTASK_TO_NEW_TASK_INFO_MESSAGE_BOX_MESSAGE_TEXT;
	}

	public String getErrorAddSubtaskToNewTaskInfoMessageBoxTitleText() {
		return ERROR_ADD_SUBTASK_TO_NEW_TASK_INFO_MESSAGE_BOX_TITLE_TEXT;
	}

	public String getSubTaskCreatedInfoMessageBoxMessageText() {
		return SUBTASK_CREATED_INFO_MESSAGE_BOX_TITLE_TEXT;
	}

	public String getSubTaskCreatedInfoMessageBoxTitleText() {
		return SUBTASK_CREATED_INFO_MESSAGE_BOX_MESSAGE_TEXT;
	}
}
