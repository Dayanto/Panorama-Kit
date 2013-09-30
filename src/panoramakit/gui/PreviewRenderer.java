/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import panoramakit.engine.task.TaskManager;
import panoramakit.mod.PanoramaKit;

/** 
 * Takes care of loading and drawing preview to the screen.
 * @author dayanto
 */
public class PreviewRenderer
{
	private BufferedImage image;
	private DynamicTexture previewTexture;
	private ResourceLocation resourceLocation;
	private TextureManager textureManager;
	
	public PreviewRenderer(TextureManager textureManager)
	{
		this.textureManager = textureManager;
	}
	
	/**
	 * Returns the image file that can be used for preview images.
	 */
	public static File getPreviewFile()
	{
		return new File(PanoramaKit.instance.getTempRenderDir(), "Preview.png");
	}
	
	
	
	public void clearPreview()
	{
		File preview = getPreviewFile();
		if(preview.exists()) {
			preview.delete();
		}
	}
	
	public boolean previewAvailable()
	{
		if(getPreviewFile().exists()) {
			// while the preview is being modified, it shouldn't be loaded
			if(!TaskManager.instance.hasTasks()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean loadPreview()
	{
		try {
			image = ImageIO.read(getPreviewFile());
			previewTexture = new DynamicTexture(image);
			resourceLocation = textureManager.getDynamicTextureLocation("preivew", previewTexture);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Draws an image centered in the rectangle supplied.
	 */
	public void drawCenteredImage(int xPos, int yPos, int width, int height)
	{
		if(previewTexture == null) {
			boolean successful = loadPreview();
			if(!successful) return;
		}
		double ratio = (double) image.getWidth() / (double) image.getHeight();
		double boxRatio = (double) width / (double) height;
		int imageWidth = ratio > boxRatio ? width : (int)(width * ratio);
		int imageHeight = ratio < boxRatio ? height : (int)(height / ratio);
		drawImage(xPos + (width - imageWidth) / 2, yPos + (height - imageHeight) / 2, imageWidth, imageHeight);
	}
	
	private void drawImage(int xPos, int yPos, int width, int height)
	{
		previewTexture.updateDynamicTexture();
		Tessellator tessellator = Tessellator.instance;
		textureManager.bindTexture(resourceLocation);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1, 1, 1, 1);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(xPos        , yPos + height, 0, 0.0, 1.0);
        tessellator.addVertexWithUV(xPos + width, yPos + height, 0, 1.0, 1.0);
        tessellator.addVertexWithUV(xPos + width, yPos         , 0, 1.0, 0.0);
        tessellator.addVertexWithUV(xPos        , yPos         , 0, 0.0, 0.0);
        tessellator.draw();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
	}
}
