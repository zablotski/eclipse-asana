package eclipseasana.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class DateDialog extends Dialog {
	String oldValue;
	String value;
	String dialogTitle = "";
	String dialogMessage = "";

	public DateDialog(Shell parent) {
		super(parent);
	}

	public DateDialog(Shell parent, int style) {
		super(parent, style);
	}

	public DateDialog(Shell parent, int style, String title, String message) {
		super(parent, style);
		this.dialogMessage = message;
		this.dialogTitle = title;
	}
	
	private String getSelectedDueDateApiString(DateTime cal){
		Date selectedDueDate = new Date();
		Calendar temp = Calendar.getInstance();
		temp.set(
					cal.getYear(), 
					cal.getMonth(), 
					cal.getDay()
				);
		selectedDueDate.setTime(temp.getTimeInMillis());
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.format(selectedDueDate);
	}

	public String open() {
		Shell parent = getParent();
		final Shell shell = new Shell(parent, SWT.TITLE | SWT.BORDER
				| SWT.APPLICATION_MODAL);
		shell.setText(dialogTitle);

		shell.setLayout(new GridLayout(2, true));

		Label label = new Label(shell, SWT.NULL);
		label.setText(dialogMessage);

		final DateTime cal = new DateTime (shell, SWT.DATE | SWT.SHORT);

		final Button buttonOK = new Button(shell, SWT.PUSH);
		buttonOK.setText("Set Date");
		buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		Button buttonSetEmpty = new Button(shell, SWT.PUSH);
		buttonSetEmpty.setText("Unset date");
		Button buttonCancel = new Button(shell, SWT.PUSH);
		buttonCancel.setText("Cancel");

		buttonOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				value = getSelectedDueDateApiString(cal);
				shell.dispose();
			}
		});

		buttonCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				value = null;
				shell.dispose();
			}
		});
		
		buttonSetEmpty.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				value = "";
				shell.dispose();
			}
		});

		shell.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
			}
		});

		shell.pack();
		shell.open();

		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		return value;
	}
}

