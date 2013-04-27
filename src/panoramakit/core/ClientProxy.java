package panoramakit.core;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * ClientProxy
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class ClientProxy extends CommonProxy
{
	@Override
	public void registerHandlers()
	{
		RenderTickHandler renderTick = new RenderTickHandler();
		TickRegistry.registerTickHandler(renderTick, Side.CLIENT);
	}
	

}
