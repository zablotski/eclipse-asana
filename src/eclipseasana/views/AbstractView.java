package eclipseasana.views;

public enum AbstractView {
	//perspektywa dla stanu niezalogowanego do asany
	BEFORE_LOGIN,
	//perspektywa dla stanu tu¿ po zalogowaniu
	AFTER_LOGIN,
	//perspektywa po wybraniu workspace'a
	WORKSPACE_PROJECT_SELECTION,
	//perspektywa po wybraniu projektu
	PROJECT_DETAILS,
	//perspektywa po przejœciu do szczegó³owych informacji o wybranym tasku
	TASK_DETAILS
}
