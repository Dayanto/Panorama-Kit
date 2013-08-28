/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod.handlers;

import java.util.EnumSet;
import panoramakit.engine.task.TaskManager;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * ClientTickHandler
 * 
 * @author dayanto
 */
public class OnFrameTickHandler implements ITickHandler
{
	private final TaskManager taskManager = TaskManager.instance;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		taskManager.runTick();
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{}
	
	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public String getLabel()
	{
		return "panoramakit_client_tick";
	}
	
}
