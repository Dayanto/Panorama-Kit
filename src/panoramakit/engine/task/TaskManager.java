package panoramakit.engine.task;

import java.util.ArrayList;

/**
 * The task manager keeps track of all the tasks currently running.
 * 
 * @author dayanto
 */
public class TaskManager {
	private ArrayList<Task> taskList = new ArrayList<>();

	public void addTask(Task task) {
		taskList.add(task);
	}

	/**
	 * Get the currently active task.
	 */
	public Task getCurrentTask() {
		return taskList.get(0);
	}

	/**
	 * Move on to the next task in the list if one exists.
	 * 
	 * Should only be used by the dispatcher.
	 */
	public void runNextTaskIfCompleted() {
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
			if(currentTask instanceof ThreadedTask)
			{
				((ThreadedTask)currentTask).start();
			}
		}
	}

	/**
	 * Removes the current task if it has been stopped and handled all the cleanup.
	 * 
	 * Should only be used by the task manager.
	 */
	public void clearTaskIfStopped() {
		if (hasTasks() && getCurrentTask().hasStopped()) {
			taskList.remove(0);
		}
	}

	/**
	 * Whether or not there are any tasks in the taskList.
	 */
	public boolean hasTasks() {
		return !taskList.isEmpty();
	}

	/**
	 * Terminates all tasks by first clearing all the scheduled tasks and then asking the currently
	 * active task to stop. The mod will then continuously ask the remaining task whether it has
	 * finished and remove it once it has.
	 */
	public void halt() {
		if (!taskList.isEmpty()) {
			Task currentTask = getCurrentTask();
			taskList.clear();
			taskList.add(currentTask);
			currentTask.stop();
		}
	}
}
