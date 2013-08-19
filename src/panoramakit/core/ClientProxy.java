package panoramakit.core;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * ClientProxy
 * 
 * @author dayanto
 */
public class ClientProxy extends CommonProxy {
	@Override
	public void registerHandlers() {
		OnFrameTickHandler onFrameTick = new OnFrameTickHandler();
		TickRegistry.registerTickHandler(onFrameTick, Side.CLIENT);
	}

}
