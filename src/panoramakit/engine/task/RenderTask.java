/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.gui.GuiScreen;
import panoramakit.engine.render.CompositeImageRenderer;

/**
 * RenderTask
 * 
 * @author dayanto
 */
public class RenderTask extends Task {
	
	private static final Logger L = Logger.getLogger(RenderTask.class.getName());
	
	// the active image renderer
	private CompositeImageRenderer imageRenderer;
	boolean hasRendered = false;
	
	public RenderTask(CompositeImageRenderer imageRenderer) {
		this.imageRenderer = imageRenderer;
	}
	
	@Override
	public void perform() {
		try {
			System.out.println("Rendering screenshot");
			imageRenderer.render();
		} catch (Exception ex) {
			System.out.println("Render failed:"); ex.printStackTrace();
			L.log(Level.SEVERE, "Render failed", ex);
		}
		setCompleted();
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void finish() {
		
	}
	
	/**
	 * Defaults to false.
	 */
	@Override
	public boolean isWaiting() {
		return false;
	}
	
	@Override
	public void stop() { // TODO Auto-generated method stub
	
	}
	
	@Override
	public GuiScreen getStatusGUI() { // TODO Auto-generated method stub
		return null;
	}
	
}
