package panoramakit.core;

import java.util.EnumSet;
import panoramakit.engine.Dispatcher;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * ClientTickHandler
 * 
 * @author dayanto
 */
public class OnFrameTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		Dispatcher.runTick();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "panoramakit_client_tick";
	}

}
