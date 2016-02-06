/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.render.renderers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;
import javax.imageio.ImageIO;
import net.minecraft.util.MathHelper;
import panoramakit.engine.render.CompositeImageRenderer;
import panoramakit.mod.PanoramaKit;

/**
 * 
 * @author dayanto
 */
public class CubicRenderer extends CompositeImageRenderer
{
	// image settings
	private int resolution;
	private File file;
	private float orientation;
	private float angle;
	
	public CubicRenderer(int resolution, File file, float orientation, float angle)
	{
		super(resolution, resolution);
		this.resolution = resolution;
		this.file = file;
		this.orientation = orientation;
		this.angle = angle;
	}
	
	@Override
	public void assembleImage() throws IOException
	{
		long startTime = System.currentTimeMillis();
		
		BufferedImage image = new BufferedImage(4 * resolution, 3 * resolution, BufferedImage.TYPE_INT_ARGB);
		
		float radianMultiplier = (float) (2 * Math.PI / 360);
		
		/* we want the center to be in the middle of the image, so we need to rotate the starting point 180 degrees */
		//float orientation = this.orientation - 180;  
		
		// render the middle row
		for (int i = 0; i < 4; i++) {
			float yaw = (i - 1) * 90;
			float pitch = -angle * MathHelper.cos(yaw * radianMultiplier);
			float roll = angle * MathHelper.sin(yaw * radianMultiplier);
			yaw += orientation;
			
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
			image.setRGB(i * resolution, resolution, resolution, resolution, screenshot, 0, resolution);
		}
		
		// render the top and bottom
		for (int i = 0; i <= 2; i += 2) {
			float yaw = orientation;
			float pitch = (i - 1) * 90 - angle;
			float roll = 0;
			
			pitch = (pitch + 180) % 360 - 180; // cap it to the -180 to + 180 range
			
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
			if (!file.exists()) {
				file.mkdirs();
				file.createNewFile();
			}
			ImageIO.write(image, "png", file);
			chat.printTranslated("panoramakit.saveimage", file.getName());
		} else {
			imageLink.setImage(image);
		}
		
		FMLLog.info("Cubic render: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
