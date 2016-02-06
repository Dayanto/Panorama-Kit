/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.task.tasks;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import net.minecraftforge.fml.common.FMLLog;
import panoramakit.engine.task.Task;
import panoramakit.mod.PanoramaKit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/** 
 * @author dayanto
 */
public class DisplayGuiScreenTask extends Task
{
	Minecraft mc = Minecraft.getMinecraft();
	Class<GuiScreen> guiScreen;
	
	@SuppressWarnings("unchecked")
	public DisplayGuiScreenTask(Class<? extends GuiScreen> screen)
	{
		guiScreen = (Class<GuiScreen>) screen.asSubclass(GuiScreen.class);
	}

	@Override
	public void perform()
	{
		try {
			mc.displayGuiScreen(guiScreen.newInstance());
			// render a clean frame immediately to hide the transition.
			mc.entityRenderer.updateCameraAndRender(0, 0/*?*/);
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, "Failed to swap screen: ", e);
		}
		setCompleted();
	}
}
