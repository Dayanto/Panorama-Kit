package panoramakitcore.setup;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/**
 * PKCPluginLoader
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
@TransformerExclusions({"panoramakitcore", "panoramakitcore.asm", "panoramakitcore.setup"})
public class PKCPluginLoader implements IFMLLoadingPlugin
{
	
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] {"panoramakitcore.asm.PKCTransformer"};
	}

	@Override
	public String getModContainerClass()
	{
		return "panoramakitcore.setup.PKCModContainer";
	}
	
	@Override
	public String[] getLibraryRequestClass() { return null; }

	@Override
	public String getSetupClass() { return null; }

	@Override
	public void injectData(Map<String, Object> data) {}

}
