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
public class CubicSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_RESOLUTION = 500;
	private static final double DEF_ANGLE = 0.0;
	
	private Property resolution;
	private Property angle;
	
	public CubicSettings()
	{
		String category = "cubic";
		
		resolution = config.get(category, "resolution", DEF_RESOLUTION);
		angle = config.get(category, "angle", DEF_ANGLE);
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
	
	public float getAngle()
	{
		return (float)angle.getDouble(DEF_ANGLE);
	}
	public void setAngle(float angle)
	{
		this.angle.set(angle);
	}
}
