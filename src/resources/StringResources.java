package resources;

public final class StringResources {
	private final static StringResources ourInstance = new StringResources();
	private final static String LOGIN_LABEL_TEXT = "Your Asana API key:";
	private final static String LOGIN_BUTTON_TEXT = "Zaloguj";
	private final static String LOGOUT_BUTTON_TEXT = "Wyloguj";
	private final static String LOGIN_RESULT_ERROR_TEXT = "Incorrect pair <email, key>";
	private final static String LOGIN_RESULT_SUCCESS = "Logged in as %s";
	private final static String ASANA_API_KEY_TEST = "5aix9w1X.93qkODiCZmG2EBSZ92EbteK";
	private final static String ASANA_EMAIL_TEST = "j-kwasnicki@wp.pl";
	private final static String LOGIN_EMAIL_LABEL_TEXT = "E-mail:";
	private final static String LOGIN_KEY_LABEL_TEXT = "API Key:";
	private final static String WORKSPACES_COMBO_TEXT = "Workspace:";
	private final static String PROJECTS_COMBO_TEXT = "Projects:";
	private final static String SECTION_CONVENTION_SUFFIX = ":";
	
    
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
}
