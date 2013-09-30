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
public class CylindricalSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_PANORAMA_WIDTH = 2000;
	private static final int DEF_PANORAMA_HEIGHT = 800;
	private static final double DEF_SAMPLE_SIZE = 1.0;
	private static final double DEF_ANGLE = 0.0;
	
	private Property width;
	private Property height;
	private Property sampleSize;
	private Property angle;
	
	public CylindricalSettings()
	{
		String category = "cylindrical";
		
		width = config.get(category, "width", DEF_PANORAMA_WIDTH);
		height = config.get(category, "height", DEF_PANORAMA_HEIGHT);
		sampleSize = config.get(category, "samplesize", DEF_SAMPLE_SIZE);
		angle = config.get(category, "angle", DEF_ANGLE);
	}
	
	/* properties */
	
	public int getWidth()
	{
		return width.getInt(DEF_PANORAMA_WIDTH);
	}
	public void setWidth(int width)
	{
		this.width.set(width);
	}
	
	public int getHeight()
	{
		return height.getInt(DEF_PANORAMA_HEIGHT);
	}
	public void setHeight(int height)
	{
		this.height.set(height);
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
