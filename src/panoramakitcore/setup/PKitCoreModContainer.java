/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakitcore.setup;

import java.util.Arrays;
import panoramakit.mod.VersionInfo;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

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
		myMeta.url = "http://bit.ly/panorama-kit/";
	}
	
	public boolean registerBus(EventBus eb, LoadController lc)
	{
		eb.register(this);
		return true;
	}
}