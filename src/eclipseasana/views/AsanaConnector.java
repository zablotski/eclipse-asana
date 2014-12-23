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

import functionalities.LoginHandler;



/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class AsanaConnector extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "eclipseasana.views.AsanaConnector";
	
	private static final String LOGIN_LABEL_TEXT = "Panel logowania";

	private LoginHandler loginObject;
	//private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private Label loginLabel;
	private Text loginTextField;
	private Text passwordTextField;
	private Button loginButton;
	private Composite cp1;
	private Composite cp2;
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public AsanaConnector() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		/*viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());*/
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		StackLayout stackLayout = new StackLayout();
		parent.setLayout (stackLayout);
		
		cp1 = new Composite(parent, SWT.NONE);
		cp1.setLayout(new RowLayout());
		
		cp2 = new Composite(parent, SWT.NONE);
		cp2.setLayout(new RowLayout());
		
		stackLayout.topControl = cp1; 
		
		loginLabel = new Label(cp1, SWT.NONE);
		loginLabel.setText(LOGIN_LABEL_TEXT);
		
		loginTextField = new Text(cp1, SWT.BORDER);
		passwordTextField = new Text(cp1, SWT.BORDER);
		
		loginButton = new Button(cp1, SWT.PUSH);
		loginButton.setText("OK");
		loginButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		          switch (e.type) {
		          case SWT.Selection:
		      		loginObject = new LoginHandler();
		      		try {
		      			if(loginObject.login(loginTextField.getText(), passwordTextField.getText())){
		      				loginLabel.setText("Zajebiście");
		      			}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		      		
		            break;
		          }
		        }
		      });
		
		Asana asana = new Asana("5akl1GKq.TNjBXubjHJy5q58E7DZyqTS");
		Workspaces ww = asana.workspaces().getWorkspaces();
		User uu = asana.users().getMe();
		System.out.println(uu.email);		
		System.out.println(uu.name);
		
		
		
		
		
		
		// Create the help context id for the viewer's control
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "eclipse-asana.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AsanaConnector.this.fillContextMenu(manager);
			}
		});
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			/*public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}*/
		};
	}

	private void hookDoubleClickAction() {
		/*viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});*/
	}
	private void showMessage(String message) {
		/*MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Asana Connector",
			message);*/
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		//viewer.getControl().setFocus();
	}
}