/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.settings;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class BackgroundSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	public static final String saveBackground, replaceBackground;
	public static final String[] modes = {saveBackground = "Just Save As Zip", replaceBackground = "Placeholder! Wait for MC 1.7" /*"Add To The Game"*/};
	
	private static final int DEF_RESOLUTION = 256;
	private static final int DEF_MODE = 0;
	
	private Property resolution;
	private Property mode;
	
	public BackgroundSettings()
	{
		String category = "background";
		
		resolution = config.get(category, "resolution", DEF_RESOLUTION);
		mode = config.get(category, "mode", DEF_MODE);
	}
	
	/* properties */
	
	public int getResolution()
	{
		return resolution.getInt(DEF_RESOLUTION);
	}
	public void setResolution(int resolution)
	{
		this.resolution.set(resolution);
	}
	
	public int getMode()
	{
		int value = mode.getInt(DEF_MODE);
		return value < modes.length ? value : modes.length - 1;
	}
	public void setMode(int mode)
	{
		this.mode.set(mode);
	}
}
