/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.render;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import panoramakit.engine.accessor.EntityRendererAccessor;
import net.minecraft.client.Minecraft;

/**
 * Based on Mineshot's TiledScreenshot
 * 
 * @author Dayanto
 */
public class TiledScreenshot
{
	private final Minecraft mc = Minecraft.getMinecraft();
	
	// accessor
	private EntityRendererAccessor era = new EntityRendererAccessor();
	
	private int fullWidth;
	private int fullHeight;
	private int tileWidth;
	private int tileHeight;
	
	// image buffers
	private IntBuffer captureBuffer;
	private int[] screenshot;
	
	// camera settings
	private double camZoom;
	private float partialTicks = 0.0F;
	
	public TiledScreenshot(int screenshotWidth, int screenshotHeight, int tileWidth, int tileHeight)
	{
		this.fullWidth = screenshotWidth;
		this.fullHeight = screenshotHeight;
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		// allocate buffers
		captureBuffer = BufferUtils.createIntBuffer(tileWidth * tileHeight);
		screenshot = new int[fullWidth * fullHeight];
		
		camZoom = (double) fullHeight / (double) tileHeight;
		
		// if the zoom is 1.0, Minecraft ignores camera offsets, so... :/
		if(camZoom == 1.0)
			camZoom = 1.000000000001; 
	}
	
	public void capture()
	{	// make sure the display hasn't changed in size after buffer allocation
		if (mc.displayWidth != tileWidth || mc.displayHeight != tileHeight) {
			throw new IllegalStateException("Display size changed");
		}
		
		for (int tileOfsX = 0; tileOfsX < fullWidth; tileOfsX += tileWidth) {
			for (int tileOfsY = 0; tileOfsY < fullHeight; tileOfsY += tileHeight)
			{
				if (era != null) {
					double camOfsX = fullWidth - tileWidth - tileOfsX * 2;
					double camOfsY = fullHeight - tileHeight - tileOfsY * 2;
					
					camOfsX /= tileWidth;
					camOfsY /= tileHeight;
					
					era.setCameraZoom(camZoom);
					era.setCameraOffsetX(camOfsX);
					era.setCameraOffsetY(camOfsY);
				}
				
				mc.entityRenderer.updateCameraAndRender(partialTicks);
				
				// reset buffer position
				captureBuffer.clear();
				
				// read pixels
				GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
				GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
				GL11.glReadPixels(0, 0, tileWidth, tileHeight, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, captureBuffer);
				
				// clip the captured frame if too big
				int captureWidth = tileWidth;
				int captureHeight = tileHeight;
				
				if (captureWidth > fullWidth - tileOfsX)
					captureWidth = fullWidth - tileOfsX;
				if (captureHeight > fullHeight - tileOfsY)
					captureHeight = fullHeight - tileOfsY;
				
				// copy captured frame to the screenshot
				for (int i = 0; i < captureHeight; i++) {
					int bufferPosition = (captureHeight - i - 1) * tileWidth + (tileHeight - captureHeight) * tileWidth;
					int arrayOffset = tileOfsX + tileOfsY * fullWidth + i * fullWidth;
					
					captureBuffer.position(bufferPosition);
					captureBuffer.get(screenshot, arrayOffset, captureWidth);
				}
			}
		}
		
		// remove any transparency
		for (int x = 0; x < fullWidth; x++) {
			for (int y = 0; y < fullHeight; y++) {
				screenshot[x + y * fullWidth] = screenshot[x + y * fullWidth] | 0xFF000000;
			}
		}
	}
	
	public int[] getScreenshot()
	{
		return screenshot;
	}
	
}
