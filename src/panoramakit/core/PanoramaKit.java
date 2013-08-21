package panoramakit.core;

import panoramakit.core.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * PanoramaKit
 * 
 * @author dayanto
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class PanoramaKit {
	@SidedProxy(clientSide = "panoramakit.core.ClientProxy", serverSide = "panoramakit.core.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerHandlers();
	}
}