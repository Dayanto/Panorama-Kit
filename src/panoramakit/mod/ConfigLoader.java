/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.mod;

import java.io.File;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;

/**
 * Based on Mineshot's MineshotConfig
 * 
 * @author dayanto
 */
public class ConfigLoader
{
	
	public static Configuration getConfig(File configFile)
	{
		Configuration config;
		
		try {
			config = new Configuration(configFile);
		} catch (RuntimeException ex) {
			FMLLog.log(Level.WARN, "Error in configuration file:" + ex.getMessage(), ex);
			FMLLog.log(Level.WARN, "Loading fail-safe defaults");
			
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
