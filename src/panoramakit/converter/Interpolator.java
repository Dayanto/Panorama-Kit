/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter;

import panoramakit.converter.data.ColorData;

/**
 * Interpolators are the core of the converter. They take grid of pixels provided by the pixel
 * sampler and combines them into a single pixel based on the distance they are from the mapped
 * position in the original image. Different interpolators vary in complexity where the
 * "nearest neighbor" interpolator simply snatches the closest pixel it can find, while at the other
 * end of the spectrum, the "bicubic" interpolator uses an advanced mathematical formula. The best
 * compromise between speed and quality is the bilinear interpolator.
 * 
 * @author dayanto
 */
public abstract class Interpolator
{
	public int sampleSize;
	public static int sample;
	
	public Interpolator(int sampleSize)
	{
		this.sampleSize = sampleSize;
		sample = sampleSize;
	}
	
	public abstract int getPixelValue(double xFraction, double yFraction, ColorData[][] pixels);
}
