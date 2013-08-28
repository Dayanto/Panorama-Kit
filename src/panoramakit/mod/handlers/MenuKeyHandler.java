/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod.handlers;

import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import panoramakit.converter.ProjectionConverter;
import panoramakit.converter.projections.CubicToEquirect;
import panoramakit.converter.projections.EquirectToPanorama;
import panoramakit.engine.render.CubicRenderer;
import panoramakit.engine.task.ProjectionConverterTask;
import panoramakit.engine.task.RenderTask;
import panoramakit.engine.task.TaskManager;
import panoramakit.gui.screens.GuiProgressScreen;
import panoramakit.mod.PanoramaKit;

/**
 * This class acts as the connection between the tick handlers and the task manager.
 * 
 * @author dayanto
 */
public class MenuKeyHandler extends KeyHandler
{
	private static final KeyBinding MENU_KEY = new KeyBinding("key.panoramakit.menu", Keyboard.KEY_P);
	private static final KeyBinding RENDER_KEY = new KeyBinding("key.panoramakit.rendertest", Keyboard.KEY_K);
	private static final KeyBinding[] KEYS = new KeyBinding[] { MENU_KEY, RENDER_KEY };
	private static final boolean[] REPEAT = new boolean[] { false, false };
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static TaskManager taskManager = TaskManager.instance;
	
	public MenuKeyHandler()
	{
		super(KEYS, REPEAT);
	}
	
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if (kb == RENDER_KEY) {
			if (!taskManager.hasTasks()) {
				PanoramaKit.instance.L.info("Render panorama");
				
				// create a cubic base image
				String filePath = "C:/Users/Bertil/Desktop/RenderTest.png";
				taskManager.addTask(new RenderTask(new CubicRenderer(1000, filePath, 157, 60)));
				
				// convert it to a panorama
				try {
					EquirectToPanorama panorama = new EquirectToPanorama(new CubicToEquirect(), 1680, 1050);
					ProjectionConverter converter = new ProjectionConverter(panorama, filePath);
					taskManager.addTask(new ProjectionConverterTask(converter));
				} catch (Exception e) {
				}
			}
		}
		
		if (kb == MENU_KEY) {
			if (taskManager.hasTasks()) {
				GuiProgressScreen progress = new GuiProgressScreen();
				mc.displayGuiScreen(progress);
			} else if (mc.currentScreen != null) {
				// TODO Display menu screen.
			}
		}
	}
	
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{}
	
	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public String getLabel()
	{
		return getClass().getSimpleName();
	}
}
