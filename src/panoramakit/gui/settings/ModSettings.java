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
public class ModSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_FILE_NUMBERING = 0;
	
	public static final String[] fileNumberingOptions = {"Increment", "Date"};
	
	private Property fileNumbering;
	
	public ModSettings()
	{
		String category = "Mod";
		
		fileNumbering = config.get(category, "filenumbering", DEF_FILE_NUMBERING);
	}
	
	public int getFileNumbering()
	{
		return fileNumbering.getInt(DEF_FILE_NUMBERING);
	}
	public void setFileNumbering(int fileNumbering)
	{
		this.fileNumbering.set(fileNumbering);
	}
}