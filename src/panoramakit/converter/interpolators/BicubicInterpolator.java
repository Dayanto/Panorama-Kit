/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.interpolators;

import panoramakit.converter.Interpolator;
import panoramakit.converter.data.ColorData;

/**
 * BicubicInterpolator
 * 
 * @author dayanto
 */
public class BicubicInterpolator extends Interpolator
{
	public BicubicInterpolator()
	{
		super(4); // Sample size of 4x4 pixels.
	}
	
	@Override
	public int getPixelValue(double xFraction, double yFraction, ColorData[][] pixels)
	{
		double[] colorChannelData = new double[4];
		
		for (int colorChannel = 0; colorChannel < 4; colorChannel++) {
			double[][] componentColorChannels = getColorChannelData(pixels, colorChannel);
			colorChannelData[colorChannel] = new BicubicInterpolation().getValue(componentColorChannels, xFraction, yFraction);
		}
		
		ColorData outputPixel = new ColorData(colorChannelData);
		return outputPixel.getIntValue();
	}
	
	public double[][] getColorChannelData(ColorData[][] pixels, int colorChannel)
	{
		double[][] colorChannels = new double[pixels.length][pixels.length];
		
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels.length; y++) {
				colorChannels[x][y] = pixels[x][y].colorChannels[colorChannel];
			}
		}
		
		return colorChannels;
	}
	
}

/**
 * @see http://www.paulinternet.nl/?page=bicubic
 */
class CubicInterpolation
{
	public static double getValue(double[] p, double x)
	{
		return p[1] + 0.5 * x * (p[2] - p[0] + x * (2.0 * p[0] - 5.0 * p[1] + 4.0 * p[2] - p[3] + x * (3.0 * (p[1] - p[2]) + p[3] - p[0])));
	}
}

/**
 * @see http://www.paulinternet.nl/?page=bicubic
 */
class BicubicInterpolation extends CubicInterpolation
{
	private double[] arr = new double[4];
	
	public double getValue(double[][] p, double x, double y)
	{
		arr[0] = getValue(p[0], y);
		arr[1] = getValue(p[1], y);
		arr[2] = getValue(p[2], y);
		arr[3] = getValue(p[3], y);
		return getValue(arr, x);
	}
}
