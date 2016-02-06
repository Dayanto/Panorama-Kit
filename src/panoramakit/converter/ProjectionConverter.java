/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;
import javax.imageio.ImageIO;
import panoramakit.converter.data.Bounds;
import panoramakit.converter.data.ColorData;
import panoramakit.converter.data.PixelCoordinate;
import panoramakit.converter.data.Position;
import panoramakit.converter.interpolators.BilinearInterpolator;
import panoramakit.engine.util.ProgressTracker;
import panoramakit.mod.PanoramaKit;
import panoramakit.engine.render.ImageLink;

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
public class ProjectionConverter
{	
	private PositionMapper positionMapper;
	private Interpolator interpolator;
	
	private File imageFileInput;
	private File imageFileOutput;
	
	private BufferedImage inputImage;
	private BufferedImage outputImage;
	
	private ProgressTracker progressTracker;
	
	private ImageLink imageLink;
	
	private boolean stop = false;
	
	public ProjectionConverter(PositionMapper positionMapper, File imageFileInput, File imageFileOutput)
	{
		this.positionMapper = positionMapper;
		this.imageFileInput = imageFileInput;
		this.imageFileOutput = imageFileOutput;
		
		setInterpolator(new BilinearInterpolator());
	}
	
	public ProjectionConverter(PositionMapper positionMapper, File imageFileOverwrite)
	{
		this(positionMapper, imageFileOverwrite, imageFileOverwrite);
	}
	
	/**
	 * Attaches an image link to this converter. If no image gets sent, the converter simply
	 * ignores the link and loads an image as usual.
	 */
	public void setImageLink(ImageLink imageLink)
	{
		this.imageLink = imageLink;
	}
	
	public void setInterpolator(Interpolator interpolator)
	{
		this.interpolator = interpolator;
	}
	
	private void loadImage(File imageFile) throws IOException, IllegalArgumentException
	{
		if(imageLink != null && imageLink.getImage() != null) {
			inputImage = imageLink.getImage();
		} else {
			inputImage = ImageIO.read(imageFile);
		}
		
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		
		positionMapper.setResolution(width, height);
		positionMapper.setProjectionBounds();
		
		if (!positionMapper.hasValidProportions()) {
			throw new IllegalArgumentException("Image has bad proportions");
		}
		
		outputImage = new BufferedImage(positionMapper.getWidth(), positionMapper.getHeight(), inputImage.getType());
	}
	
	private void saveImage(File imageFile) throws IOException
	{
		// save the image
		if (!imageFile.exists()) {
			imageFile.mkdirs();
			imageFile.createNewFile();
		}
		ImageIO.write(outputImage, "png", imageFile);
	}
	
	/**
	 * Once the converter has been set up, this command executes the actual convertion.
	 */
	public void convert() throws IOException, IllegalArgumentException, InterruptedException
	{
		FMLLog.info("Converting...");
		long startTime = System.currentTimeMillis();
		
		loadImage(imageFileInput);
		
		for (int xOutput = 0; xOutput < outputImage.getWidth(); xOutput++) {
			// check once every column whether the task should stop
			if(stop) {
				throw new InterruptedException();
			}	
			
			double progress = (double) xOutput / (double) outputImage.getWidth();
			if(progressTracker != null){
				progressTracker.setCurrentProgress(progress);
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
		
		saveImage(imageFileOutput);
		
		FMLLog.info("Projection \"" + positionMapper.getClass().getSimpleName() + "\": " + (System.currentTimeMillis() - startTime) + "ms");
		FMLLog.info("Done");
	}
	
	public void setProgressTracker(ProgressTracker progressTracker)
	{
		this.progressTracker = progressTracker;
	}
	
	public void stop()
	{
		stop = true;
	}
	
	/**
	 * Retrieves the instance of the image being saved.
	 */
	public File getImage()
	{
		return imageFileOutput;
	}
}