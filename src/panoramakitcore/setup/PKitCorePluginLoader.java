/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakitcore.setup;

import java.util.Map;

import panoramakit.mod.VersionInfo;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

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
