/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.minecraft.util.MathHelper;
import panoramakit.mod.PanoramaKit;

/**
 * 
 * @author dayanto
 */
public class CubicRenderer extends CompositeImageRenderer
{
	
	private final Logger L = PanoramaKit.instance.L;
	
	// image settings
	private int resolution;
	private String filePath;
	private float orientation;
	private float angle;
	
	public CubicRenderer(int resolution, String filePath, float orientation, float angle)
	{
		super(resolution, resolution);
		this.resolution = resolution;
		this.filePath = filePath;
		this.orientation = orientation;
		this.angle = angle;
	}
	
	@Override
	public void assembleImage() throws IOException
	{
		long startTime = System.currentTimeMillis();
		
		BufferedImage image = new BufferedImage(4 * resolution, 3 * resolution, BufferedImage.TYPE_INT_ARGB);
		
		float radianMultiplier = (float) (2 * Math.PI / 360);
		
		// render the middle row
		for (int i = 0; i < 4; i++) {
			float yaw = (i - 1) * 90;
			float pitch = -angle * MathHelper.cos(yaw * radianMultiplier);
			float roll = angle * MathHelper.sin(yaw * radianMultiplier);
			rotatePlayer(yaw + orientation, pitch, roll);
			int[] screenshot = captureScreenshot();
			image.setRGB(i * resolution, resolution, resolution, resolution, screenshot, 0, resolution);
		}
		
		// render the top and bottom
		for (int i = 0; i <= 2; i += 2) {
			float yaw = orientation;
			float pitch = (i - 1) * 90 - angle;
			float roll = 0;
			
			if (pitch > 90) {
				pitch = 180 - pitch;
				yaw += 180;
				roll += 180;
			}
			if (pitch < -90) {
				pitch = -180 - pitch;
				yaw += 180;
				roll += 180;
			}
			
			rotatePlayer(yaw, pitch, roll);
			int[] screenshot = captureScreenshot();
			image.setRGB(resolution, i * resolution, resolution, resolution, screenshot, 0, resolution);
		}
		
		if(imageLink == null){
			// save the image
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
				file.createNewFile();
			}
			ImageIO.write(image, "png", file);
		} else {
			imageLink.setImage(image);
		}
		
		L.info("Cubic render: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
