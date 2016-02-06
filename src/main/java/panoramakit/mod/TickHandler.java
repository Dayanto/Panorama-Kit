/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.mod;

import java.util.EnumSet;

import panoramakit.engine.task.TaskManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

/**
 * ClientTickHandler
 * 
 * @author dayanto
 */
public class TickHandler
{
	private final TaskManager taskManager = TaskManager.instance;
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent clientTickEvent)
	{
		if(clientTickEvent.phase == Phase.START) taskManager.runTick();
	}
}
