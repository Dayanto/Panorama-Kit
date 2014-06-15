/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.task.tasks;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import panoramakit.engine.render.CompositeImageRenderer;
import panoramakit.engine.task.Task;
import panoramakit.mod.PanoramaKit;
import panoramakitcore.CoreStates;

/**
 * RenderTask
 * 
 * @author dayanto
 */
public class RenderTask extends Task
{
	private final Minecraft mc = Minecraft.getMinecraft();
	
	// the active image renderer
	private CompositeImageRenderer imageRenderer;
	
	public RenderTask(CompositeImageRenderer imageRenderer)
	{
		this.imageRenderer = imageRenderer;
		imageRenderer.setChatPrinter(chat);
	}
	
	@Override
	public void perform()
	{
		if (mc.currentScreen != null) {
			return;
		}

		mc.entityRenderer.updateCameraAndRender(0);

		try {
			FMLLog.info("Rendering screenshot");
			imageRenderer.render();
		} catch (Exception ex) {
			FMLLog.log(Level.ERROR, "Render failed: " + ex.getMessage(), ex);
			chat.printTranslated("panoramakit.renderfail", ex.getMessage());
		}
		// Render a clean image to hide what was just rendered.
		mc.entityRenderer.updateCameraAndRender(0);
		setCompleted();
	}
	
	@Override
	public void init()
	{
		CoreStates.setRenderState(true);
	}
	
	@Override
	public void finish()
	{
		CoreStates.setRenderState(false);
	}
	
	@Override
	public void stop()
	{
		setStopped();
	}
	
}
