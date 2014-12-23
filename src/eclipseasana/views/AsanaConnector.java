package eclipseasana.views;

import net.joelinn.asana.Asana;
import net.joelinn.asana.users.User;
import net.joelinn.asana.workspaces.Workspace;
import net.joelinn.asana.workspaces.Workspaces;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

//import functionalities.LoginHandler;


public class AsanaConnector extends ViewPart {

	public static final String ID = "eclipseasana.views.AsanaConnector";

	private static final String LOGIN_LABEL_TEXT = "Your API KEY: ";

	//private LoginHandler loginObject;
	// private TableViewer viewer;
	private Composite parent;
	private StackLayout stackLayout;
//	private Action action1;
//	private Action action2;
//	private Action doubleClickAction;
	private Label loginLabel;
	private Label loginResult;
	private Text loginTextField;
	private Button loginButton;
	private Composite cp1;
	private Composite cp2;
	private Asana asana;

	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		stackLayout = new StackLayout();
		parent.setLayout(stackLayout);

		cp1 = new Composite(parent, SWT.NONE);
		cp1.setLayout(new RowLayout());

		cp2 = new Composite(parent, SWT.NONE);
		cp2.setLayout(new RowLayout());

		stackLayout.topControl = cp1;

		loginLabel = new Label(cp1, SWT.NONE);
		loginLabel.setText(LOGIN_LABEL_TEXT);

		loginTextField = new Text(cp1, SWT.BORDER);
		loginButton = new Button(cp1, SWT.PUSH);

		loginResult = new Label(cp1, SWT.NONE);
		loginResult.setText("!");

		loginButton.setText("OK");
		loginButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					try {
						asana = new Asana(loginTextField.getText());
						System.out.println("Your email: "
								+ asana.users().getMe().email + ", your Name: "
								+ asana.users().getMe().name);
						loginResult.setText("Your email: "
								+ asana.users().getMe().email + ", your Name: "
								+ asana.users().getMe().name); //jak zakomentować powyższe, to wyświetli, dlaczego, to ja nie wiem =)
						
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
		loginLabel.setFocus();
		
	}

}