/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.mod;

import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import panoramakit.engine.task.TaskManager;
import panoramakit.gui.screens.menuscreens.GuiMenuMain;
import panoramakit.gui.screens.GuiScreenProgress;
import panoramakit.gui.settings.SharedSettings;

/**
 * This class acts as the connection between the tick handlers and the task manager.
 * 
 * @author dayanto
 */
public class MenuKeyHandler extends KeyHandler
{
	public static final KeyBinding MENU_KEY = new KeyBinding("key.panoramakit.menu", Keyboard.KEY_P);
	public static final KeyBinding RENDER_KEY = new KeyBinding("key.panoramakit.rendertest", Keyboard.KEY_K);
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
				
			}
		}
		
		if (kb == MENU_KEY) {
			if (taskManager.hasTasks()) {
				mc.displayGuiScreen(new GuiScreenProgress());
			} else if(mc.currentScreen == null) {
				mc.displayGuiScreen(new GuiMenuMain());
				SharedSettings.setOrientation(mc.thePlayer.rotationYaw);
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
