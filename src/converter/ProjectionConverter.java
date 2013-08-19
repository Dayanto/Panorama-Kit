package converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import converter.data.Bounds;
import converter.data.ColorData;
import converter.data.PixelCoordinate;
import converter.data.Position;
import converter.interpolators.BilinearInterpolator;

/**
 * This converter loads a panorama made with one projection and converts it to another projection.
 * The position of a pixel is traced to a location in the original image, but with all the warping
 * involved, most of the time, pixels in one of the projections will not line up perfectly with the
 * pixels in the other. Instead, the pixels in the output image are being mapped to a floating point
 * position perfectly matching where a pixel should have been. The color of this virtual pixel is
 * determined by looking at the surrounding pixels and sampling their colors. How close the
 * surrounding pixels are to the virtual pixel determines their influence.
 * 
 * @author dayanto
 */
public class ProjectionConverter {
	private PositionMapper positionMapper;
	private Interpolator interpolator;

	private String imagePathInput;
	private String imagePathOutput;

	private BufferedImage inputImage;
	private BufferedImage outputImage;

	private double progress;

	public ProjectionConverter(PositionMapper positionMapper, Interpolator interpolator, String imagePathInput, String imagePathOutput) {
		this.positionMapper = positionMapper;
		this.interpolator = interpolator;
		this.imagePathInput = imagePathInput;
		this.imagePathOutput = imagePathOutput;
	}

	public ProjectionConverter(PositionMapper positionMapper, String imagePathInput, String imagePathOutput) {
		this(positionMapper, new BilinearInterpolator(), imagePathInput, imagePathOutput);
	}

	private void loadImage(String imagePath) throws IOException, Exception {
		inputImage = ImageIO.read(new File(imagePath));

		int width = inputImage.getWidth();
		int height = inputImage.getHeight();

		positionMapper.setResolution(width, height);
		positionMapper.setProjectionBounds();

		if (!positionMapper.hasValidProportions()) {
			throw new Exception("Image has bad proportions");
		}

		outputImage = new BufferedImage(positionMapper.getWidth(), positionMapper.getHeight(), inputImage.getType());
	}

	private void saveImage(String imagePath) throws IOException {
		ImageIO.write(outputImage, "png", new File(imagePath));
	}

	/**
	 * Once the converter has been set up, this command executes the actual convertion.
	 */
	public void convert() throws IOException, Exception {
		System.out.println("Converting...");
		long startTime = System.currentTimeMillis();

		loadImage(imagePathInput);

		for (int xOutput = 0; xOutput < outputImage.getWidth(); xOutput++) {
			if (xOutput % 100 == 0) {
				progress = 100D * xOutput / outputImage.getWidth();
				System.out.println((int) Math.round(progress) + "%");
			}
			for (int yOutput = 0; yOutput < outputImage.getHeight(); yOutput++) {
				Position position = positionMapper.getPosition(xOutput, yOutput);

				if (position == null) {
					outputImage.setRGB(xOutput, yOutput, 0x00000000);
					continue;
				}

				double xFraction = position.getXFraction();
				double yFraction = position.getYFraction();

				PixelCoordinate[][] pixelData = positionMapper.getPixelCoordinates(position, inputImage.getWidth(), inputImage.getHeight(),
						interpolator.sampleSize);

				ColorData[][] pixels = new ColorData[pixelData.length][pixelData.length];
				for (int i = 0; i < pixels.length; i++) {
					for (int j = 0; j < pixels.length; j++) {
						Bounds edge = positionMapper.getPixelSampler().edge;
						int x = edge.getCappedX(pixelData[i][j].x);
						int y = edge.getCappedY(pixelData[i][j].y);
						int pixel = inputImage.getRGB(x, y);
						pixels[i][j] = new ColorData(pixel);
					}
				}

				int pixelValue = interpolator.getPixelValue(xFraction, yFraction, pixels);

				outputImage.setRGB(xOutput, yOutput, pixelValue);
			}
		}

		saveImage(imagePathOutput);

		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
		System.out.println("Done");
	}
}