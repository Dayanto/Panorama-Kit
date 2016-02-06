/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.settings;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class StereographicSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_PANORAMA_WIDTH = 1920;
	private static final int DEF_PANORAMA_HEIGHT = 1080;
	private static final double DEF_SAMPLE_SIZE = 2.0;
	private static final double DEF_FIELD_OF_VIEW = 90.0;
	private static final double DEF_ANGLE = 90.0;
	
	private Property width;
	private Property height;
	private Property sampleSize;
	private Property fieldOfView;
	private Property angle;

	
	public StereographicSettings()
	{
		String category = "stereographic";
		
		width = config.get(category, "width", DEF_PANORAMA_WIDTH);
		height = config.get(category, "height", DEF_PANORAMA_HEIGHT);
		sampleSize = config.get(category, "samplesize", DEF_SAMPLE_SIZE);
		fieldOfView = config.get(category, "fieldofview", DEF_FIELD_OF_VIEW);
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
	
	public float getFieldOfView()
	{
		return (float)fieldOfView.getDouble(DEF_FIELD_OF_VIEW);
	}
	public void setFieldOfView(float fieldOfView)
	{
		this.fieldOfView.set(fieldOfView);
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
