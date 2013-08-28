/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.settings;

import java.io.File;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class PanoramaSettings
{
	private final Minecraft mc = Minecraft.getMinecraft();
	private final PanoramaKit pk = PanoramaKit.instance;
	
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_PANORAMA_WIDTH = 2000;
	private static final int DEF_PANORAMA_HEIGHT = 800;
	private static final double DEF_SAMPLE_SIZE = 1.0;
	
	private Property panoramaWidth;
	private Property panoramaHeight;
	private Property sampleSize;
	private Property filePath;
	private float orientation;
	private float angle;
	
	public ArrayList<Class<GuiScreen>> imageMenuLinks = new ArrayList<Class<GuiScreen>>();
	
	public PanoramaSettings()
	{
		panoramaWidth = config.get("panorama", "width", DEF_PANORAMA_WIDTH);
		panoramaHeight = config.get("panorama", "height", DEF_PANORAMA_HEIGHT);
		sampleSize = config.get("panorama", "samplesize", DEF_SAMPLE_SIZE);
		filePath = config.get("panorama", "filepath", new File(pk.getRenderDir(), "Panorama.png").getPath());
		orientation = ((mc.thePlayer.rotationYaw % 360) + 360) % 360;
		angle = 0;
	}
	
	public int getPanoramaWidth()
	{
		return panoramaWidth.getInt(DEF_PANORAMA_WIDTH);
	}
	public void setPanoramaWidth(int width)
	{
		panoramaWidth.set(width);
	}
	
	public int getPanoramaHeight()
	{
		return panoramaHeight.getInt(DEF_PANORAMA_HEIGHT);
	}
	public void setPanoramaHeight(int height)
	{
		panoramaHeight.set(height);
	}
	
	public float getSampleSize()
	{
		return (float)sampleSize.getDouble(DEF_SAMPLE_SIZE);
	}
	public void setSampleSize(float size)
	{
		sampleSize.set(size);
	}
	
	public String getFilePath()
	{
		return filePath.getString();
	}
	public void setFilePath(String path)
	{
		filePath.set(path);
	}
	
	public float getOrientation()
	{
		return orientation;
	}
	public void setOrientation(float orientation)
	{
		this.orientation = orientation;
	}
	
	public float getAngle()
	{
		return angle;
	}
	public void setAngle(float angle)
	{
		this.angle = angle;
	}
	
	
}
