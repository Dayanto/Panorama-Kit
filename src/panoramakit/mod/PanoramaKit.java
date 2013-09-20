/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod;

import java.io.File;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Configuration;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.settings.ModSettings;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * @author dayanto
 */
@Mod(
		modid = "PanoramaKit",
		name = "Panorama Kit",
		version = VersionInfo.VERSION
)
public class PanoramaKit
{
	private final Minecraft mc = Minecraft.getMinecraft();
	public final Logger L = Logger.getLogger("PanoramaKit");
	
	@Instance("PanoramaKit")
	public static PanoramaKit instance;
	
	private Configuration config;
	private ModSettings settings;
	private File renderDir;
	private File tempRenderDir;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		L.setParent(FMLLog.getLogger());
		config = ConfigLoader.getConfig(evt.getSuggestedConfigurationFile());
		settings = new ModSettings();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		TickRegistry.registerTickHandler(new OnFrameTickHandler(), Side.CLIENT);
		KeyBindingRegistry.registerKeyBinding(new MenuKeyHandler());
		renderDir = new File(mc.mcDataDir, "panoramas");
		tempRenderDir = new File(renderDir, "temp");
		
		File preview = PreviewRenderer.getPreviewFile();
		if(preview.exists()) {
			preview.delete();
		}
	}
	
	public Configuration getConfig()
	{
		return config;
	}
	
	public ModSettings getModSettings()
	{
		return settings;
	}
	
	public File getRenderDir()
	{
		return renderDir;
	}
	
	public File getTempRenderDir()
	{
		return tempRenderDir;
	}
	
	public void printChat(String msg, Object... params) {
        mc.ingameGUI.getChatGUI().addTranslatedMessage(msg, params);
    }
}