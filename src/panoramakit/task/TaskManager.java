package panoramakit.task;

import java.util.ArrayList;
import panoramakit.task.tick.*;

/**
 * The task manager keeps track of all the tasks currently running and executes them.
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class TaskManager 
{
	private ArrayList<Task> taskList = new ArrayList<>();
	
	public void addTask(Task task)
	{
		taskList.add(task);
	}
	
	public Task getCurrentTask()
	{
		return taskList.get(0);
	}
	
	public boolean hasTasks()
	{
		return !taskList.isEmpty();
	}
	
	/**
	 * 
	 */
	public void halt()
	{
		if(!taskList.isEmpty())
		{
			Task currentTask = getCurrentTask();
			taskList.clear();
			taskList.add(currentTask);
			currentTask.stop();
		}
	}
	
}