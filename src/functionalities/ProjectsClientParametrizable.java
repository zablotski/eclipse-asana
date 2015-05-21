package functionalities;

import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.joelinn.asana.projects.ProjectsClient;
import net.joelinn.asana.tasks.Tasks;

public class ProjectsClientParametrizable extends ProjectsClient {
	private static String ASANA_API_FIELDS_URL_PARAMETER_NAME = "opt_fields";
	private static String ASANA_API_URL_PARAMETER_VALUES_SEPARATOR = ",";
	
	public ProjectsClientParametrizable(String apiKey) {
		super(apiKey);
	}

	public Tasks getTasks(long projectId, String[] additionalFieldNamesList){
		StringBuilder fieldParamValueString = new StringBuilder();
		
		for(int i=0; i<additionalFieldNamesList.length; i++){
			fieldParamValueString.append(additionalFieldNamesList[i]);
			
			if(i < additionalFieldNamesList.length - 1){
				fieldParamValueString.append(ASANA_API_URL_PARAMETER_VALUES_SEPARATOR);
			}
		}
		
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add(ASANA_API_FIELDS_URL_PARAMETER_NAME, fieldParamValueString);
		
		return get(String.format("%s/tasks", projectId), params).getEntity(Tasks.class);
	}
	
}
