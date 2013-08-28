/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.settings;

import java.io.File;
import panoramakit.mod.PanoramaKit;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/** 
 * @author dayanto
 */
public class PanoramaSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_PANORAMA_WIDTH = 2000;
	private static final int DEF_PANORAMA_HEIGHT = 800;
	private static final double DEF_SAMPLE_SIZE = 1.0;
	
	private Property panoramaWidth;
	private Property panoramaHeight;
	private Property sampleSize;
	private Property filePath;
	
	public PanoramaSettings()
	{
		panoramaWidth = config.get("panorama", "width", DEF_PANORAMA_WIDTH);
		panoramaHeight = config.get("panorama", "height", DEF_PANORAMA_HEIGHT);
		sampleSize = config.get("panorama", "resolution", DEF_SAMPLE_SIZE);
		filePath = config.get("panorama", "filepath", new File(pk.getRenderDir(), "Panorama.png").getPath());
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
	
	public double getSampleSize()
	{
		return sampleSize.getDouble(DEF_SAMPLE_SIZE);
	}
	
	public void setSampleSize(double size)
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
}
