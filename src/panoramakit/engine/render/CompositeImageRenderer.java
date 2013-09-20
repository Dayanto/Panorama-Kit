/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.render;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import panoramakit.engine.accessor.EntityRendererAccessor;
import panoramakit.engine.util.ChatPrinter;
import panoramakit.engine.util.LockableMouseHelper;

/**
 * CompositeImage
 * 
 * @author dayanto
 */
public abstract class CompositeImageRenderer
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	// accessor
	private EntityRendererAccessor era = new EntityRendererAccessor();
	
	// saved game settings
	private boolean hideGui;
	private boolean advancedOpengl;
	private MouseHelper mouseHelper;
	private float fieldOfView;
	private float pitch;
	private float yaw;
	
	// screenshot size
	private int screenshotWidth;
	private int screenshotHeight;
	
//	// unmodified display size
//	private int displayWidth;
//	private int displayHeight;
//
//	// modified display size
//	private int renderWidth;
//	private int renderHeight;
	
	private TiledScreenshot screenshot;
	
	protected ImageLink imageLink;
	
	protected ChatPrinter chat;
	
	public CompositeImageRenderer(int screenshotWidth, int screenshotHeight)
	{
		this.screenshotWidth = screenshotWidth;
		this.screenshotHeight = screenshotHeight;
	}
	
	/**
	 * Attaches an image link to this renderer. It is however up to the renderer to actually make use of it.
	 */
	public void setImageLink(ImageLink imageLink) {
		this.imageLink = imageLink;
	}
	
	/** 
	 * Enables the ability to print messages to chat. It can however also be silenced afterwards. 
	 */
	public void setChatPrinter(ChatPrinter chat)
	{
		this.chat = chat;
	}
	
	public final void render() throws IOException
	{ // store the game resolution
//		displayWidth = mc.displayWidth;
//		displayHeight = mc.displayHeight;
//
//		// calculate the optimal resolution not greater than the window size (to avoid excess rendering)
//		double tilesX = Math.ceil((double) screenshotWidth / (double) displayWidth);
//		double tilesY = Math.ceil((double) screenshotHeight / (double) displayHeight);	
//		renderWidth = (int) Math.ceil(screenshotWidth / tilesX);
//		renderHeight = (int) Math.ceil(screenshotHeight / tilesY);
//
//		screenshot = new TiledScreenshot(screenshotWidth, screenshotHeight, renderWidth, renderHeight);
	
		screenshot = new TiledScreenshot(screenshotWidth, screenshotHeight, mc.displayWidth, mc.displayHeight);
		
		applyMods();
		
		try {
			assembleImage();
		} finally {
			restoreMods();
		}
	}
	
	public abstract void assembleImage() throws IOException;
	
	public int[] captureScreenshot()
	{
		screenshot.capture();
		return screenshot.getScreenshot();
	}
	
	public void rotatePlayer(float yaw, float pitch, float roll)
	{
		mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw = yaw;
		mc.thePlayer.rotationPitch = mc.thePlayer.prevRotationPitch = pitch;
		era.setCameraRoll(roll);
	}
	
	/**
	 * Copied method from Mineshot
	 */
	private void applyMods()
	{
		// TODO Implement some sort of renderActive = true;
		
		// hide GUI elements, they would appear on each tile otherwise
		hideGui = mc.gameSettings.hideGUI;
		mc.gameSettings.hideGUI = true;
		
		// disable advanced OpenGL features, they cause flickering on render chunks
		advancedOpengl = mc.gameSettings.advancedOpengl;
		// mc.gameSettings.advancedOpengl = false;
		
		// change the field of view to a quarter circle (90 degrees)
		fieldOfView = mc.gameSettings.fovSetting; 
		mc.gameSettings.fovSetting = (90F / mc.thePlayer.getFOVMultiplier() - 70F) / (110F - 70F); // fov 90 adjusted to a 0-1.0 scale representing 70-110
		
		// save player rotation
		this.yaw = mc.thePlayer.rotationYaw;
		this.pitch = mc.thePlayer.rotationPitch;
		
		// lock the mouse to prevent misaligned tiles
		LockableMouseHelper mouseHelperLocked = new LockableMouseHelper();
		mouseHelperLocked.setGrabbing(false);
		mouseHelperLocked.setLocked(true);
		mouseHelper = mc.mouseHelper;
		mc.mouseHelper = mouseHelperLocked;
		
		// disable entity frustum culling for all loaded entities
		for (Object obj : mc.theWorld.loadedEntityList) {
			Entity ent = (Entity) obj;
			ent.ignoreFrustumCheck = true;
			ent.renderDistanceWeight = 16;
		}
		
		
		
		// resize display to optimal resolution (to avoid excess rendering)
//		mc.displayWidth = renderWidth;
//		mc.displayHeight = renderHeight;
	}
	
	/**
	 * Copied method from Mineshot
	 */
	private void restoreMods()
	{ // restore camera settings
		if (era != null) {
			era.setCameraZoom(1);
			era.setCameraOffsetX(0);
			era.setCameraOffsetY(0);
			era.setCameraRoll(0);
		}
		
		mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw = yaw;
		mc.thePlayer.rotationPitch = mc.thePlayer.prevRotationPitch = pitch;
		
		// restore game resolution
//		mc.displayWidth = displayWidth;
//		mc.displayHeight = displayHeight;
		
		// restore user settings
		mc.gameSettings.hideGUI = hideGui;
		mc.gameSettings.advancedOpengl = advancedOpengl;
		mc.gameSettings.fovSetting = fieldOfView;
		
		// unlock mouse
		mc.mouseHelper = mouseHelper;
		
		// enable entity frustum culling
		for (Object obj : mc.theWorld.loadedEntityList) {
			Entity ent = (Entity) obj;
			ent.ignoreFrustumCheck = false;
			ent.renderDistanceWeight = 1;
		}
		
		// TODO Implement renderActive = false;
	}
}
