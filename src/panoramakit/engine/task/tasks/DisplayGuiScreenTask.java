/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task.tasks;

import java.util.logging.Level;
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
			// render a clean frame immedaitely to hide the transition.
			mc.entityRenderer.updateCameraAndRender(0);
		} catch (Exception e) {
			PanoramaKit.instance.L.log(Level.SEVERE, "Failed to swap screen: ", e);
		}
		setCompleted();
	}
}
