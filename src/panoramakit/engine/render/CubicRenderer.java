/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author dayanto
 */
public class CubicRenderer extends CompositeImageRenderer {
	
	// imag settings
	private int resolution;
	private String filePath;
	
	// TODO add 3 axis orientation
	
	public CubicRenderer(int resolution, String filePath) {
		super(resolution, resolution);
		this.resolution = resolution;
		this.filePath = filePath;
	}
	
	@Override
	public void assembleImage() throws IOException {
		long startTime = System.currentTimeMillis();
		
		BufferedImage image = new BufferedImage(4 * resolution, 3 * resolution, BufferedImage.TYPE_INT_ARGB);
		
		// render the middle row
		for (int i = 0; i < 4; i++) {
			float yaw = i * 90;
			rotatePlayer(yaw, 0);
			int[] screenshot = captureScreenshot();
			image.setRGB(i * resolution, resolution, resolution, resolution, screenshot, 0, resolution);
		}
		
		// render the top and bottom
		float yaw = 90;
		for (int i = 0; i <= 2; i += 2) {
			float pitch = (i - 1) * 90;
			rotatePlayer(yaw, pitch);
			int[] screenshot = captureScreenshot();
			image.setRGB(resolution, i * resolution, resolution, resolution, screenshot, 0, resolution);
		}
		
		// save the image
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		ImageIO.write(image, "png", file);
		
		System.out.println("Cubic: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
