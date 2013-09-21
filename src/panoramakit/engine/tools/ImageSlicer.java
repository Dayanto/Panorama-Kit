/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** 
 * Takes an image and slicer it into a number of images with file names based on their tile position.
 * 
 * @author dayanto
 */
public class ImageSlicer
{
	private File imageFile;
	private int xTiles;
	private int yTiles;
	
	private int tileWidth;
	private int tileHeight;
	
	public ImageSlicer(File imageFile, int xTiles, int yTiles)
	{
		this.imageFile = imageFile;
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
	
	public void createTile(BufferedImage image, int xTile, int yTile) throws IOException
	{
		BufferedImage imageTile = image.getSubimage(xTile * tileWidth, yTile * tileHeight, tileWidth, tileHeight);
		ImageIO.write(imageTile, "png", new File(getTileName(xTile,yTile)));
	}
	
	public String getTileName(int xTile, int yTile)
	{
		String fileName = imageFile.getName();
		String name = fileName.substring(0, fileName.lastIndexOf('.'));
		String extension = fileName.substring(fileName.lastIndexOf('.'));
		
		return name + "_" + xTile + "_" + yTile + extension;
	}
}
