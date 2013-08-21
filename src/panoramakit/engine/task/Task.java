package panoramakit.engine.task;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;

/**
 * Task
 * 
 * @author dayanto
 */
public abstract class Task {
	/**
	 * Initalizes the task at the moment it ends up first in the tasklist. This allows things to be
	 * defined at the moment of execution.
	 */
	public abstract void init();

	/**
	 * Performs the task. Until the task has finished, this will repeat once every frame.
	 */
	public abstract void perform();

	/**
	 * Cleans up after the task has finished running if necessary.
	 */
	public abstract void finish();

	/**
	 * Politely asks the task to abort as quickly as possible, performing any cleanup that has to be
	 * done. While this will usually has an immediate effect, threaded tasks might take some time to
	 * come to a halt and there is no guarantee that a task will be able to stop at all before
	 * finishing.
	 */
	public abstract void stop();

	// current states of the task
	private boolean completed = false;
	private boolean stopped = false;

	/**
	 * Once the task has finished running, this method must be called to signal that the task should
	 * be removed. (the task will perpetually run its perfom method until this has been done)
	 * 
	 * TODO Actually use it in the task manager/dispatcher
	 */
	public void setCompleted() {
		completed = true;
	}

	/**
	 * Once any cleanup has been performed after stopping a task, this has to be called to signal
	 * that
	 * 
	 * TODO Actually use it in the task manager/dispatcher
	 */
	public void setStopped() {
		stopped = true;
	}

	/**
	 * Whether this task has been completed.
	 */
	public final boolean hasCompleted() {
		return completed;
	}

	/**
	 * If this task has been aborted, returns whether it has done all cleanup and come to a halt.
	 */
	public final boolean hasStopped() {
		return stopped;
	}

	/**
	 * Whether the task is currently waiting for something. Some tasks need to wait before running
	 * and some might take pauses.
	 * 
	 * Will probably be removed in favor of letting the tasks handle waiting themselves.
	 */
	public boolean isWaiting() {
		return false; // default value
	}

	/**
	 * Retrieves a GUI specific to this task that usually contains information about the progress as
	 * well as providing a way of stopping all tasks. There is however no requirement for the task
	 * to provide such a GUI and it might just return null.
	 * 
	 * TODO Might get replaced by something simpler.
	 */
	public abstract GuiScreen getStatusGUI();

	/**
	 * Used by tasks to display a status message in the chat.
	 */
	public void printStatusMessage(String message) {
		// TODO Add setting to disable status messages from tasks.

		GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
		chat.printChatMessage(message);
	}

}