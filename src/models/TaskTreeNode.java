package models;

import java.util.ArrayList;
import java.util.List;

import net.joelinn.asana.tasks.Task;
import net.joelinn.asana.tasks.Tasks;

public class TaskTreeNode {
	private Task root;
	private List<TaskTreeNode> subTasks;
	
	public TaskTreeNode(Task root){
		this.root = root;
	}
	
	public TaskTreeNode(Task root, List<TaskTreeNode> subTrees){
		this.root = root;
		this.subTasks = new ArrayList<TaskTreeNode>(subTrees);
	}
	
	public static List<TaskTreeNode> tasksListToForest(Tasks tasks){
		List<TaskTreeNode> forest = new ArrayList<>();
		List<TaskTreeNode> list = new ArrayList<>();
		
		for(int i=0; i<tasks.size(); i++){
			if(tasks.get(i).parent != null){
				list.add(new TaskTreeNode(tasks.get(i)));
			}else{
				forest.add(new TaskTreeNode(tasks.get(i)));
			}
		}
		
		return tasksListToForest(forest, list);
	}
	
	private static List<TaskTreeNode> tasksListToForest(List<TaskTreeNode> forest, List<TaskTreeNode> list){
		List<TaskTreeNode> subTasks = new ArrayList<>();
		
		if(list.isEmpty()){
			return forest;
		}
		
		for(TaskTreeNode t : forest){
			for(TaskTreeNode n : list){
				if(n.getRoot().parent.id == t.getRoot().id){
					subTasks.add(n);
					list.remove(n);
				}
			}
			t.setSubTasks(tasksListToForest(subTasks, list));
		}
		
		return forest;
	}
	
	public Task getRoot() {
		return root;
	}

	public void setRoot(Task root) {
		this.root = root;
	}

	public List<TaskTreeNode> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(List<TaskTreeNode> subTasks) {
		this.subTasks = subTasks;
	}
}
