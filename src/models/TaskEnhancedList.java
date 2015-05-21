package models;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import resources.StringResources;
import net.joelinn.asana.tasks.Task;
import net.joelinn.asana.tasks.Tasks;

public class TaskEnhancedList {
	private ArrayList<SimpleEntry<Task, TaskType>> tasks;
	private HashMap<Long, ArrayList<Long>> tasksInSections;
	private HashMap<Long, Long> taskSectionAsoc;
	
	public TaskEnhancedList(){
		this.tasks = new ArrayList<>();
		this.tasksInSections = new HashMap<>();
		this.taskSectionAsoc = new HashMap<>();
	}
	
	public TaskEnhancedList(Tasks tasks){
		this.tasks = new ArrayList<>();
		this.tasksInSections = new HashMap<>();
		this.taskSectionAsoc = new HashMap<>();

		long currentSection = -1;
		ArrayList<Long> sectionTasks = new ArrayList<>();
		
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).name.endsWith(StringResources.getInstance().getSectionConventionSuffix())){
				if(currentSection > -1){
					tasksInSections.put(currentSection, sectionTasks);
					sectionTasks.clear();
				}
				currentSection = tasks.get(i).id;
				this.tasks.add(new SimpleEntry<Task, TaskType>(tasks.get(i), TaskType.SECTION));
				continue;
			}
			
			if(tasks.get(i).parent == null){
				this.tasks.add(new SimpleEntry<Task, TaskType>(tasks.get(i), TaskType.USER_MAIN_TASK));
			}else{
				this.tasks.add(new SimpleEntry<Task, TaskType>(tasks.get(i), TaskType.USER_SUBTASK));
			}
			
			if(currentSection > -1){
				sectionTasks.add(tasks.get(i).id);
				taskSectionAsoc.put(tasks.get(i).id, currentSection);
				if(i == tasks.size()-1 && !sectionTasks.isEmpty()){
					tasksInSections.put(currentSection, sectionTasks);
				}
			}else{
				taskSectionAsoc.put(tasks.get(i).id, (long)-1);
			}
		}
	}
	
	public Tasks getUserTasks_NoSubtasks(){
		TaskType[] types = new TaskType[] { TaskType.USER_MAIN_TASK };
		return getTypedTasks(types);
	}
	
	public Tasks getUserTasks(){
		TaskType[] types = new TaskType[] { TaskType.USER_MAIN_TASK, TaskType.USER_SUBTASK };
		return getTypedTasks(types);
	}
	
	public Tasks getSections(){
		TaskType[] types = new TaskType[] { TaskType.SECTION };
		return getTypedTasks(types);
	}
	
	public Tasks getAllTasks(){
		Tasks result = new Tasks();
		for(SimpleEntry<Task, TaskType> t : tasks){
			result.add(t.getKey());
		}
		
		return result;
	}

	public Task get(int index){
		return tasks.get(index).getKey();
	}
	
	private Tasks getTypedTasks(TaskType[] types){
		Tasks result = new Tasks();
		
		for(int i=0; i<tasks.size(); i++){
			for(int j=0; j<types.length; j++){
				if(tasks.get(i).getValue() == types[j]){
					result.add(tasks.get(i).getKey());
					break;
				}
			}
		}
		
		return result;
	}
	
	private enum TaskType{
		USER_MAIN_TASK,
		USER_SUBTASK,
		SECTION,
		
	}

	public HashMap<Long, ArrayList<Long>> getTasksInSections() {
		return tasksInSections;
	}

	public HashMap<Long, Long> getTaskSectionAsoc() {
		return taskSectionAsoc;
	}
	
	public long getTaskSection(long taskId){
		return taskSectionAsoc.get(taskId);
	}

	public int size() {
		return tasks.size();
	}
}

