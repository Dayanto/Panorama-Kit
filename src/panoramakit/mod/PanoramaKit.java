/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.mod;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.config.Configuration;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.settings.ModSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;


/**
 * @author dayanto
 */
@Mod(
		modid = "PanoramaKit",
		name = "Panorama Kit",
		version = VersionInfo.VERSION,
		useMetadata = true
)
public class PanoramaKit
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	@Instance("PanoramaKit")
	public static PanoramaKit instance;
	
	private Configuration config;
	private ModSettings settings;
	private File renderDir;
	private File tempRenderDir;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt)
	{
		config = ConfigLoader.getConfig(evt.getSuggestedConfigurationFile());
		settings = new ModSettings();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		EventBus eventBus = FMLCommonHandler.instance().bus();
		eventBus.register(new TickHandler());
		eventBus.register(new KeyHandler());
		// TODO Add options menu to list. Something like -> GuiModOptions.registerMod("Panorama Kit", new GuiSettingsMod());
		
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

}