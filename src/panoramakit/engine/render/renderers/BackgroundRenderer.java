/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.render.renderers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import panoramakit.engine.render.CompositeImageRenderer;
import panoramakit.mod.PanoramaKit;

/**
 * 
 * @author dayanto
 */
public class BackgroundRenderer extends CompositeImageRenderer
{
	
	private final Logger L = PanoramaKit.instance.L;
	
	// settings
	private int resolution;
	private File folderPath;
	private float orientation;
	
	public BackgroundRenderer(int resolution, File folderPath, float orientation)
	{
		super(resolution, resolution);
		this.resolution = resolution;
		this.folderPath = folderPath;
		this.orientation = orientation;
	}
	
	@Override
	public void assembleImage() throws IOException
	{
		long startTime = System.currentTimeMillis();
		
		BufferedImage image = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
		
		// render the first four images along the horizon
		for (int i = 0; i < 4; i++) {
			float yaw = orientation + (i - 1) * 90;
			rotatePlayer(yaw + 90, 0, 0);
			int[] screenshot = captureScreenshot();
			image.setRGB(0, 0, resolution, resolution, screenshot, 0, resolution);
			saveBackgroundImage(image, i);
		}
		
		// render the top and bottom
		for (int i = 0; i <= 2; i += 2) {
			float yaw = orientation;
			float pitch = (i - 1) * 90;
			rotatePlayer(yaw, pitch, 0);
			int[] screenshot = captureScreenshot();
			image.setRGB(0, 0, resolution, resolution, screenshot, 0, resolution);
			saveBackgroundImage(image, 4 + i/2);
		}
		
		L.info("Background render: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	public void saveBackgroundImage(BufferedImage image, int pieceNumber) throws IOException {
		// save the image
		File file = new File(folderPath, "panorama_" + pieceNumber + ".png");
		if (!file.exists()) {
			file.mkdirs();
			file.createNewFile();
		}
		ImageIO.write(image, "png", file);
	}
	
}
