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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.screens.settingsscreens.GuiSettingsMod;
import panoramakit.gui.settings.ModSettings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.relauncher.Side;

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
	private final Minecraft mc = Minecraft.getMinecraft();
	
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
	
	public void printChatMessage(String msg)
	{
		printChatMessage(new ChatComponentText(msg));
    }

	public void printChatMessage(IChatComponent msg)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(msg);
	}

	public void printTranslatedMessage(String key, String ... params)
	{
		ArrayList<Object> list = new ArrayList<Object>();
		for(String s : params)
		{
			list.add(new ChatComponentText(s));
		}
		printChatMessage(new ChatComponentTranslation(key, list.toArray()));
	}
}