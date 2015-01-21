package eclipseasana.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import functionalities.ApplicationContext;
import resources.StringResources;

public class LoginUI {
	private StringResources stringResources = StringResources.getInstance();
	private ApplicationContext appContext;
	private Composite verticalPanel;
	private Composite horizontalEmailPanel;
	private Composite horizontalKeyPanel;
	
	private RowLayout verticalPanelLayout;
	private RowLayout horizontalEmailPanelLayout;
	private RowLayout horizontalKeyPanelLayout;
	
	private Label loginEmailLabel;
	private Label loginKeyLabel;
	private Label loginResult;
	private Text emailTextField;
	private Text keyTextField;
	private Button loginButton;
	private Button logoutButton;
	
	public LoginUI(Composite parent, int style, ApplicationContext appContext){
		this.appContext = appContext;
		this.verticalPanel = new Composite(parent, style);
		this.verticalPanelLayout = new RowLayout();
		this.verticalPanelLayout.type = SWT.VERTICAL;
		this.verticalPanel.setLayout(verticalPanelLayout);
		
		addControls(verticalPanel, style);
		
		logoutButton = new Button(verticalPanel, SWT.PUSH);
		logoutButton.setText(stringResources.getLogoutButtonText());
		logoutButton.setVisible(false);
		loginResult = new Label(verticalPanel, SWT.NONE);
		loginButton = new Button(verticalPanel, SWT.PUSH);
		loginButton.setText(stringResources.getLoginButtonText());
	}
	
	private void addControls(Composite localParent, int style) {
		horizontalEmailPanel = new Composite(localParent, style);
		horizontalEmailPanelLayout = new RowLayout();
		horizontalEmailPanelLayout.type = SWT.HORIZONTAL;
		horizontalEmailPanel.setLayout(horizontalEmailPanelLayout);
		
		loginEmailLabel = new Label(horizontalEmailPanel, SWT.NONE);
		loginEmailLabel.setText(stringResources.getLoginEmailLabelText());
		emailTextField = new Text(horizontalEmailPanel, SWT.BORDER);
		//emailTextField.setText();
		
		horizontalKeyPanel = new Composite(localParent, style);
		horizontalKeyPanelLayout = new RowLayout();
		horizontalKeyPanelLayout.type = SWT.HORIZONTAL;
		horizontalKeyPanel.setLayout(horizontalKeyPanelLayout);
		
		loginKeyLabel = new Label(horizontalKeyPanel, SWT.NONE);
		loginKeyLabel.setText(stringResources.getLoginKeyLabelText());
		keyTextField = new Text(horizontalKeyPanel, SWT.BORDER);
		//keyTextField.setText();
	}

	public void setViewVisibility(boolean value){
		verticalPanel.setVisible(value);
	}
	
	public void clear(){
		emailTextField.setText("");
		keyTextField.setText("");;
	}
	
	public void setLoginState(){
		setViewState(true);
	}
	
	public void setLogoutState(){
		setViewState(false);
	}
	
	private void setViewState(boolean value){
		//po wylogowaniu/zalogowaniu zmienia siê layout
		horizontalEmailPanel.setVisible(value);
		horizontalKeyPanel.setVisible(value);
		loginButton.setVisible(value);
		
		logoutButton.setVisible(!value);
	}
	
	public void setLoginSuccessMessage(String asanaUserName){
		loginResult.setText(stringResources.getLoginResultSuccessText(asanaUserName));
	}
	
	public void setLoginErrorMessage(){
		loginResult.setText(stringResources.getLoginResultErrorText());
	}

	public Composite getVerticalPanel() {
		return verticalPanel;
	}

	public Composite getHorizontalEmailPanel() {
		return horizontalEmailPanel;
	}

	public Composite getHorizontalKeyPanel() {
		return horizontalKeyPanel;
	}

	public RowLayout getVerticalPanelLayout() {
		return verticalPanelLayout;
	}

	public RowLayout getHorizontalEmailPanelLayout() {
		return horizontalEmailPanelLayout;
	}

	public RowLayout getHorizontalKeyPanelLayout() {
		return horizontalKeyPanelLayout;
	}

	public Label getLoginEmailLabel() {
		return loginEmailLabel;
	}

	public Label getLoginKeyLabel() {
		return loginKeyLabel;
	}

	public Label getLoginResult() {
		return loginResult;
	}

	public Text getEmailTextField() {
		return emailTextField;
	}

	public Text getKeyTextField() {
		return keyTextField;
	}

	public Button getLoginButton() {
		return loginButton;
	}
	
	public Button getLogoutButton() {
		return logoutButton;
	}
}
