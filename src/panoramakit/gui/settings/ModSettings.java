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
public class ModSettings
{
	private final PanoramaKit pk = PanoramaKit.instance;
	private final Configuration config = pk.getConfig();
	
	private static final int DEF_FILE_NUMBERING = 0;
	
	public static final String increment, date;
	public static final String[] fileNumberingOptions = {increment = "Increment" , date = "Date"};
	
	private Property fileNumbering;
	
	public ModSettings()
	{
		String category = "Mod";
		
		fileNumbering = config.get(category, "filenumbering", DEF_FILE_NUMBERING);
	}
	
	public int getFileNumbering()
	{
		int value = fileNumbering.getInt(DEF_FILE_NUMBERING);
		return value < fileNumberingOptions.length ? value : fileNumberingOptions.length - 1;
	}
	public void setFileNumbering(int fileNumbering)
	{
		this.fileNumbering.set(fileNumbering);
	}
}
