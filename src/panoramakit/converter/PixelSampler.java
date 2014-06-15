/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter;

import panoramakit.converter.data.*;

/**
 * A pixel sampler retrieves a grid of pixels (their indices) surrounding the mapped position in the
 * original image.
 * 
 * @author dayanto
 */
public abstract class PixelSampler
{
	/*
	 * The edge bounds limits the index value from going beyond the edges of the image. This is
	 * mainly a safety precaution while developing a filter. If the position mapper is incorrectly
	 * written, so that it would try to sample from outside the image, it would instead sample from
	 * the edge of the image. This way, it's posible to get a visible indication of what the problem
	 * is instead of just an error.
	 */
	public Bounds edge;
	
	public abstract PixelCoordinate[][] getSamplePixels(Position position, int width, int height, int sampleSize);
	
	public final void setAllBounds(int width, int height)
	{
		edge = new Bounds(0, width, 0, height);
		setBounds(width, height);
	}
	
	public void setBounds(int width, int height)
	{}
}
