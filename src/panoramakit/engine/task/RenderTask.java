/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import panoramakit.engine.render.CompositeImageRenderer;
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
	private final Logger L = PanoramaKit.instance.L;
	
	// the active image renderer
	private CompositeImageRenderer imageRenderer;
	boolean hasRendered = false;
	
	public RenderTask(CompositeImageRenderer imageRenderer)
	{
		this.imageRenderer = imageRenderer;
	}
	
	@Override
	public void perform()
	{
		if (mc.currentScreen != null) {
			return;
		}
		
		try {
			L.info("Rendering screenshot");
			imageRenderer.render();
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Render failed: " + ex.getMessage(), ex);
			PanoramaKit.instance.printChat("panoramakit.renderfail", ex);
		}
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
	{ // TODO Auto-generated method stub
	
	}
	
	@Override
	public double getProgress()
	{
		return 0;
	}
	
}
