package panoramakitcore.setup;

import java.util.Map;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/**
 * PKCPluginLoader
 * 
 * @author dayanto
 */
@TransformerExclusions({ "panoramakitcore" })
@MCVersion("1.5.1")
public class PKCPluginLoader implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { "panoramakitcore.asm.CodeTransformer" };
	}

	@Override
	public String getModContainerClass() {
		return "panoramakitcore.setup.PKCModContainer";
	}

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

}
