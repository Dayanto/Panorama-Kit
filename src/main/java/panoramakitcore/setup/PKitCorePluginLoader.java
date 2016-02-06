/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakitcore.setup;

import java.util.Map;

import panoramakit.mod.VersionInfo;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/**
 * PKCPluginLoader
 * 
 * @author dayanto
 */
@TransformerExclusions({ "panoramakitcore" })
@MCVersion(VersionInfo.MC_VERSION)
public class PKitCorePluginLoader implements IFMLLoadingPlugin
{
	
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "panoramakitcore.asm.CodeTransformer" };
	}
	
	@Override
	public String getModContainerClass()
	{
		return "panoramakitcore.setup.PKitCoreModContainer";
	}
	
	@Override
	public String getSetupClass()
	{
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data)
	{}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
	
}
