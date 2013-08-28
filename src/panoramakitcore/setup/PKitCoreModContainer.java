/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakitcore.setup;

import java.util.Arrays;
import panoramakit.mod.VersionInfo;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

/**
 * PKCModContainer
 * 
 * @author dayanto
 */
public class PKitCoreModContainer extends DummyModContainer
{
	public PKitCoreModContainer()
	{
		super(new ModMetadata());
		ModMetadata myMeta = super.getMetadata();
		myMeta.modId = "PanoramaKitCore";
		myMeta.name = "Panorama Kit Core";
		myMeta.version = VersionInfo.VERSION;
		myMeta.authorList = Arrays.asList(new String[] { "Dayanto" });
		myMeta.description = "Enables Panorama Kit to capture images properly";
		myMeta.url = "http://www.minecraftforum.net/topic/792414-panorama-kit/";
	}
	
	public boolean registerBus(EventBus eb, LoadController lc)
	{
		eb.register(this);
		return true;
	}
}