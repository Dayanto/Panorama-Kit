package panoramakit.task;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;

/**
 * Task
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public abstract class Task
{	
	/**
	 * Initalizes the task at the moment it ends up first in the tasklist. This allows things
	 * to be defined at the moment of execution.
	 */
	public abstract void init();
	
	/**
	 * Cleans up after the task has finished running if necessary.
	 */
	public abstract void end();
	
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

	/**
	 * Displays a GUI specific to this task while it's running. The GUI usually displays the current 
	 * progress as well as supplying a way of aborting all operation that is currently scheduled.  
	 */
	public abstract void displayStatusGUI();
	
	/**
	 * Used by tasks to display a status message in the chat.
	 */
	public void printStatusMessage(String message)
	{
		// TODO Add setting to disable status messages from tasks.
		
		GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
		chat.printChatMessage(message);
	}
	
}