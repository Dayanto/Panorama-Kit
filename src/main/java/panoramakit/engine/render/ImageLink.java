/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.render;

import java.awt.image.BufferedImage;

/** 
 * The image link is used to enable sending images between tasks. This improves performance 
 * as it prevents the image from being saved and immediately loaded again. Since samples are 
 * generally larger than the final image, this significantly reduces the load on the file system.
 * 
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
}
