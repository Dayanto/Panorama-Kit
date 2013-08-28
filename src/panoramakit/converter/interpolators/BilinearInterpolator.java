/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.interpolators;

import panoramakit.converter.Interpolator;
import panoramakit.converter.data.ColorData;

/**
 * BilinearInterpolator
 * 
 * @author dayanto
 */
public class BilinearInterpolator extends Interpolator
{
	public BilinearInterpolator()
	{
		super(2); // Sample size of 2x2 pixels.
	}
	
	@Override
	public int getPixelValue(double xFraction, double yFraction, ColorData[][] pixels)
	{
		ColorData p00 = pixels[0][0];
		ColorData p01 = pixels[0][1];
		ColorData p10 = pixels[1][0];
		ColorData p11 = pixels[1][1];
		
		multiply(p00, (1 - xFraction) * (1 - yFraction));
		multiply(p01, (1 - xFraction) * yFraction);
		multiply(p10, xFraction * (1 - yFraction));
		multiply(p11, xFraction * yFraction);
		
		ColorData[] componentColorData = { p00, p01, p10, p11 };
		ColorData sum = getSum(componentColorData);
		
		return sum.getIntValue();
	}
	
	private void multiply(ColorData colorData, double intensity)
	{
		for (int i = 0; i < colorData.colorChannels.length; i++) {
			colorData.colorChannels[i] = colorData.colorChannels[i] * intensity;
		}
	}
	
	private ColorData getSum(ColorData[] componentColorData)
	{
		double colorChannelSum[] = new double[4];
		
		for (ColorData colorData : componentColorData) {
			for (int i = 0; i < colorData.colorChannels.length; i++) {
				colorChannelSum[i] += colorData.colorChannels[i];
			}
		}
		
		return new ColorData(colorChannelSum);
	}
}
