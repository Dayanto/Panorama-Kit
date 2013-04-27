package panoramakit.core;

import java.util.EnumSet;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * Panorama Kit
 * 
 * RenderTickHandler
 * 
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class RenderTickHandler implements ITickHandler
{

	/**
	 * This block of code gets executed every frame right before any vanilla rendering.
	 * As this runs once every frame, it doesn't specifically have to be used with the
	 * rendering, but may have more generic purposes that are performed once every
	 * frame.
	 */
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	/**
	 * This block of code gets executed right after the vanilla code has rendered.
	 */
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return "panoramakit_render_tick";
	}
}
