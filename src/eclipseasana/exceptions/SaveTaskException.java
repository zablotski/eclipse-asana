package eclipseasana.exceptions;

public class SaveTaskException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5972881623618510298L;
	
	public SaveTaskException(Exception inner){
		super("Task save error; inner exception message: " + inner.getLocalizedMessage());
	}
	
}
