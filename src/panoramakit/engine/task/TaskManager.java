/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task;

import java.util.ArrayList;

/**
 * The task manager keeps track of all the tasks currently running.
 * 
 * @author dayanto
 */
public class TaskManager
{
	public static final TaskManager instance = new TaskManager();
	
	private ArrayList<Task> taskList = new ArrayList<>();
	
	/**
	 * Add a new task to the end of the task list.
	 */
	public void addTask(Task task)
	{
		taskList.add(task);
	}
	
	/**
	 * Returns whether or not there are any tasks in the taskList.
	 */
	public boolean hasTasks()
	{
		return !taskList.isEmpty();
	}
	
	/**
	 * Returns the number of tasks that are still remaining.
	 */
	public int tasksRemaining()
	{
		return taskList.size();
	}
	
	/**
	 * Get the currently active task.
	 */
	public Task getCurrentTask()
	{
		if(!taskList.isEmpty()) {
			return taskList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Terminates all tasks by first clearing all the scheduled tasks and then asking the currently active task to stop. The mod will then
	 * continuously ask the remaining task whether it has finished and remove it once it has.
	 */
	public void halt()
	{
		if (!taskList.isEmpty()) {
			Task currentTask = getCurrentTask();
			taskList.clear();
			taskList.add(currentTask);
			currentTask.stop();
		}
	}
	
	/**
	 * Executes the current task and 
	 */
	public void runTick()
	{
		Task currentTask;
		do {
			if (!hasTasks()) return;
			
			currentTask = getCurrentTask();
			currentTask.perform();
			
			runNextTaskIfCompleted();
			clearTaskIfStopped();
		} while (currentTask != getCurrentTask()); // if one has been completed continue doing the next
	}
	
	/**
	 * Move on to the next task in the list if one exists.
	 */
	private void runNextTaskIfCompleted()
	{
		if (hasTasks()) {
			Task currentTask = getCurrentTask();
			if (currentTask.hasCompleted()) {
				currentTask.finish();
				taskList.remove(0);
				runNextTask();
			}
		}
	}
	
	private void runNextTask()
	{
		if (hasTasks()) {
			Task currentTask = getCurrentTask();
			currentTask.init();
			if (currentTask instanceof ThreadedTask) {
				((ThreadedTask) currentTask).start();
			}
		}
	}
	
	/**
	 * Removes the current task if it has been stopped and handled all the cleanup.
	 */
	private void clearTaskIfStopped()
	{
		if (hasTasks() && getCurrentTask().hasStopped()) {
			taskList.remove(0);
		}
	}
}
