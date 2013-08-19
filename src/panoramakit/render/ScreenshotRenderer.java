package panoramakit.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * @author dayanto
 */
public class ScreenshotRenderer extends CompositeImageRenderer {

	// panorama settings
	private int imageWidth;
	private int imageHeight;

	// private int time;
	// private int fileName;

	// TODO add 3 axis orientation

	public ScreenshotRenderer(int imageWidth, int imageHeight) {
		super(1, imageHeight);
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	@Override
	public void assembleImage() throws IOException {
		int[] screenshot;
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, 1);

		for (int x = 0; x < imageWidth; x++) {
			screenshot = captureScreenshot();
			image.setRGB(x, 0, 1, imageHeight, screenshot, 0, 1);
		}

		// save image
		File file = new File("C:/Users/Bertil/Desktop/RenderTest.png");
		if (!file.exists())
			file.createNewFile();
		ImageIO.write(image, "png", file);
	}
}