/* 
 * This code is in the public domain. Do what you want with it. :)
 */
package panoramakit.gui.settings;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class EquirectSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_RESOLUTION = 500;
	private static final double DEF_SAMPLE_SIZE = 2.0;
	private static final double DEF_ANGLE = 0.0;
	
	private Property resolution;
	private Property sampleSize;
	private Property angle;
	
	public EquirectSettings()
	{
		String category = "equirect";
		
		resolution = config.get(category, "resolution", DEF_RESOLUTION);
		sampleSize = config.get(category, "samplesize", DEF_SAMPLE_SIZE);
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
	
	
	public float getSampleSize()
	{
		return (float)sampleSize.getDouble(DEF_SAMPLE_SIZE);
	}
	public void setSampleSize(float size)
	{
		sampleSize.set(size);
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
