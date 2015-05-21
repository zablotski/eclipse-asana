package functionalities;

import com.google.common.primitives.Longs;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.joelinn.asana.tasks.TaskRequestBuilder;

public class TaskRequestBuilderFixed extends TaskRequestBuilder{
	
	public MultivaluedMapImpl build(){
		if(followers.size() > 0){
			for(int i = 0; i < followers.size(); i++){
				params.add(String.format("followers[%s]", i), followers.get(i));
			}
		}
		
		if(projects.size() > 0){
			String projectIds = "";
			
			for(int i = 0; i < projects.size(); i++){
				projectIds += projects.get(i) + ",";
			}
			
			projectIds = projectIds.substring(0, projectIds.length()-1);
			
			params.add("projects", projectIds);
		}
		return params;
	}
	
}
