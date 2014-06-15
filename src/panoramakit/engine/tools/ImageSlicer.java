/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** 
 * Takes an image and slices it into a number of images with file names based on their tile position.
 * 
 * @author dayanto
 */
public class ImageSlicer
{
	private File imageFile;
	private File outputFolder;
	
	private int xTiles;
	private int yTiles;
	
	// local working variables
	private int tileWidth;
	private int tileHeight;
	
	public ImageSlicer(File imageFile, File outputFolder, int xTiles, int yTiles)
	{
		this.imageFile = imageFile;
		this.outputFolder = outputFolder;
		this.xTiles = xTiles;
		this.yTiles = yTiles;
	}
	
	public void slice() throws IOException, Exception
	{
		BufferedImage image = ImageIO.read(imageFile);
		tileWidth = image.getWidth() / xTiles;
		tileHeight = image.getHeight() / yTiles;
		
		if(tileWidth * xTiles != image.getWidth() || tileHeight * yTiles != image.getHeight()) {
			throw new IllegalArgumentException("Image not evenly divisible by the number of tiles!");
		}
		
		for(int xTile = 0; xTile < xTiles; xTile++) {
			for(int yTile = 0; yTile < yTiles; yTile++) {
				createTile(image, xTile, yTile);
			}
		}
	}
	
	private void createTile(BufferedImage image, int xTile, int yTile) throws IOException
	{
		BufferedImage imageTile = image.getSubimage(xTile * tileWidth, yTile * tileHeight, tileWidth, tileHeight);
		File imageTileFile = new File(outputFolder, getTileName(xTile,yTile));
		if(imageTileFile.getParentFile().exists()) imageTileFile.mkdirs();
		if(imageTileFile.exists()) imageTileFile.createNewFile();
		ImageIO.write(imageTile, "png", imageTileFile);
	}
	
	private String getTileName(int xTile, int yTile)
	{
		return xTile + "_" + yTile + ".png";
	}
}
