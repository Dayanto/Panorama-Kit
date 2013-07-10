package panoramakit;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import panoramakit.task.Task;
import panoramakit.task.TaskManager;
import panoramakit.task.tick.ClientTick;
import panoramakit.task.tick.PostRenderTick;
import panoramakit.task.tick.TickID;

import static net.minecraft.client.Minecraft.getMinecraft;

/**
 * This class acts as the connection between the tick handlers and the task manager. 
 * 
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class Dispatcher
{
	public static TaskManager taskManager = new TaskManager();
	
	public static void runTick(TickID tickID)
	{
		if(taskManager.hasTasks())
		{
			Task currentTask = taskManager.getCurrentTask();
			
			switch(tickID)
			{
				case CLIENT_TICK:
				{
					if(currentTask instanceof ClientTick)
						((ClientTick)currentTask).clientTick();
					break;
				}
				case POST_RENDER_TICK:
				{
					if(currentTask instanceof PostRenderTick)
						((PostRenderTick)currentTask).postRenderTick();
					break;
				}
			}
			
			if(currentTask.hasCompleted())
			{
				taskManager.nextTask();
			}
			if(currentTask.hasStopped())
			{
				taskManager.clearStoppedTask();
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P)) // TODO Replace with keykind
		{
			if(taskManager.hasTasks())
			{
				GuiScreen statusGUI = taskManager.getCurrentTask().getStatusGUI();
				getMinecraft().displayGuiScreen(statusGUI);
			}
			else if(getMinecraft().currentScreen != null)
			{
				// TODO Display menu screen.
			}
		}
		
		// TODO Remove test code
		if(Keyboard.isKeyDown(Keyboard.KEY_G))
		{
			if(getMinecraft().renderViewEntity == getMinecraft().thePlayer)
			{
				getMinecraft().renderViewEntity = new EntityCamera(getMinecraft().theWorld);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_H))
		{
			if(getMinecraft().renderViewEntity != getMinecraft().thePlayer)
			{
				getMinecraft().renderViewEntity = getMinecraft().thePlayer;
			}
		}
	}
}