package panoramakit;

import panoramakit.task.Task;
import panoramakit.task.TaskManager;
import panoramakit.task.tick.ClientTick;
import panoramakit.task.tick.PostRenderTick;
import panoramakit.task.tick.TickID;

/**
 * FlowControl
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
				default:
				{
					break;
				}
			}
		}
		else
		{
			// TODO Do the logic for when the mod is idle.
		}
	}
}
