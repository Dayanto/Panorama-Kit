package panoramakit;

import panoramakit.core.CommonProxy;
import panoramakit.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * PanoramaKit
 *  
 * @author dayanto
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class PanoramaKit
{	
	@SidedProxy(clientSide = "panoramakit.core.ClientProxy", serverSide = "panoramakit.core.CommonProxy")
	public static CommonProxy proxy;
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		proxy.registerHandlers();
	}
}