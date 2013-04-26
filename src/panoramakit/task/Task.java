package panoramakit.task;


public abstract interface Task
{
	/**
	 * Runs any pre-render code associated with this task.
	 */
	public abstract void clientTick();
	
	/**
	 * Runs any pre-render code associated with this task.
	 */
	public abstract void preRenderTick();
	
	/**
	 * Runs any post-render code associated with this task.
	 */
	public abstract void postRenderTick();

	
	/**
	 * Displays a GUI specific to this task while it's running. The GUI usually displays the current 
	 * progress as well as supplying a way of aborting all operation that is currently scheduled.  
	 */
	public abstract void displayGUI();
	
	/**
	 * Politely asks the task to abort as quickly as possible, performing any cleanup that has
	 * to be done. While this will usually has an immediate effect, threaded tasks might take 
	 * some time to come to a halt and there is no guarantee that a task will be able to stop 
	 * at all before finishing.
	 */
	public abstract void stop();
	
	/**
	 * If this task has been aborted, returns whether it has done all cleanup and come to a halt.
	 */
	public abstract boolean hasStopped();
	
	/**
	 * Whether the task is currently waiting for something. Some tasks need to wait before running and
	 * some might take pauses. 
	 */
	public abstract boolean isWaiting();
	
	/**
	 * Whether this task has been completed.
	 */
	public abstract boolean isFinished();

}