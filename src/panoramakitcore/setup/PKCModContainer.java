package panoramakitcore.setup;

import java.util.Arrays;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

/**
 * PKCModContainer
 * 
 * @author dayanto
 */
public class PKCModContainer extends DummyModContainer {
	public PKCModContainer() {
		super(new ModMetadata());
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[] { "Dayanto" });
		myMeta.description = "";
		myMeta.modId = "panoramakitcore";
		myMeta.name = "Panorama Kit Core";
		myMeta.url = "http://www.minecraftforum.net/topic/792414-panorama-kit/";
	}

	public boolean registerBus(EventBus eb, LoadController lc) {
		eb.register(this);
		return true;
	}
}