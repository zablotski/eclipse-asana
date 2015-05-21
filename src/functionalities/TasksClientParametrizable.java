package functionalities;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.joelinn.asana.tasks.Tasks;
import net.joelinn.asana.tasks.TasksClient;

public class TasksClientParametrizable extends TasksClient {
	private static String ASANA_API_FIELDS_URL_PARAMETER_NAME = "opt_fields";
	private static String ASANA_API_URL_PARAMETER_VALUES_SEPARATOR = ",";
	
	public TasksClientParametrizable(String apiKey) {
		super(apiKey);
		// TODO Auto-generated constructor stub
	}
	
	public Tasks getSubtasks(long taskId, String[] additionalFieldNamesList){
		StringBuilder fieldParamValueString = new StringBuilder();
		
		for(int i=0; i<additionalFieldNamesList.length; i++){
			fieldParamValueString.append(additionalFieldNamesList[i]);
			
			if(i < additionalFieldNamesList.length - 1){
				fieldParamValueString.append(ASANA_API_URL_PARAMETER_VALUES_SEPARATOR);
			}
		}
		
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add(ASANA_API_FIELDS_URL_PARAMETER_NAME, fieldParamValueString);
		
		return get(String.format("tasks/%s/subtasks", taskId), params).getEntity(Tasks.class);
	}
}
