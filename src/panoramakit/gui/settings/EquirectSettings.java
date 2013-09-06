/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.settings;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class EquirectSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_RESOLUTION = 500;
	private static final double DEF_SAMPLE_SIZE = 1.0;
	
	private Property resolution;
	private Property sampleSize;
	private static float orientation;
	private static float angle;
	
	public EquirectSettings()
	{
		resolution = config.get("equirect", "resolution", DEF_RESOLUTION);
		sampleSize = config.get("equirect", "samplesize", DEF_SAMPLE_SIZE);
	}
	
	public EquirectSettings(float playerRotation)
	{
		this();
		orientation = ((playerRotation % 360) + 360) % 360;
		angle = 0;
	}
	
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
	
	public float getOrientation()
	{
		return orientation;
	}
	public void setOrientation(float orientation)
	{
		EquirectSettings.orientation = orientation;
	}
	
	public float getAngle()
	{
		return angle;
	}
	public void setAngle(float angle)
	{
		EquirectSettings.angle = angle;
	}
	
	
}
