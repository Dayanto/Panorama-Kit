/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import panoramakit.engine.task.TaskManager;
import panoramakit.gui.screens.menuscreens.GuiMenuMain;
import panoramakit.gui.screens.GuiScreenProgress;
import panoramakit.gui.settings.SharedSettings;

/**
 * This class acts as the connection between the tick handlers and the task manager.
 * 
 * @author dayanto
 */
public class KeyHandler
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	private static TaskManager taskManager = TaskManager.instance;
	
	public static final KeyBinding MENU_KEY = new KeyBinding("key.panoramakit.menu", Keyboard.KEY_P, "key.categories.gui"); // TODO Fix placeholder categories
	public static final KeyBinding RENDER_KEY = new KeyBinding("key.panoramakit.rendertest", Keyboard.KEY_K, "key.categories.gui");

	public KeyHandler()
	{
		ClientRegistry.registerKeyBinding(MENU_KEY);
		ClientRegistry.registerKeyBinding(RENDER_KEY);
	}
	
	@SubscribeEvent
	public void keyDown(KeyInputEvent keyInputEvent)
	{
		if (MENU_KEY.isPressed())
		{
			if (taskManager.hasTasks())
			{
				mc.displayGuiScreen(new GuiScreenProgress());
			}
			else if(mc.currentScreen == null)
			{
				mc.displayGuiScreen(new GuiMenuMain());
				SharedSettings.setOrientation(mc.thePlayer.rotationYaw);
				String s = "Something";
			}
		}
		
		if (RENDER_KEY.isPressed())
		{
			if (!taskManager.hasTasks())
			{
				// TODO Needs an implementation or it should be removed!
			}
		}	
	}

}
