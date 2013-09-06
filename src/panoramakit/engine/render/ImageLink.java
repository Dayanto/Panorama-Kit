/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.render;

import java.awt.image.BufferedImage;

/** 
 * @author dayanto
 */
public class ImageLink
{
	private BufferedImage image;

	public BufferedImage getImage()
	{
		return image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	public boolean hasImage()
	{
		return image != null;
	}
}
