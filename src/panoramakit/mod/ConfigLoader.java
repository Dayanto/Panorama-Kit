/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraftforge.common.Configuration;

/**
 * Based on Mineshot's MineshotConfig
 * 
 * @author dayanto
 */
public class ConfigLoader
{
	
	private static final Logger L = PanoramaKit.instance.L;
	
	public static Configuration getConfig(File configFile)
	{
		Configuration config;
		
		try {
			config = new Configuration(configFile);
		} catch (RuntimeException ex) {
			L.log(Level.WARNING, "Error in configuration file:" + ex.getMessage(), ex);
			L.log(Level.WARNING, "Loading fail-safe defaults");
			
			File configFileFS = new File(configFile.getParentFile(), "failsafe_" + configFile.getName());
			if (configFileFS.exists()) {
				configFileFS.delete();
			}
			// if that one fails as well, we're screwed
			config = new Configuration(configFileFS);
		}
		
		if (config.hasChanged()) {
			config.save();
		}
		
		return config;
	}
}
