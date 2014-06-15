/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod;

import java.util.EnumSet;

import panoramakit.engine.task.TaskManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

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
