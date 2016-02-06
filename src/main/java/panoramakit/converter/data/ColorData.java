/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter.data;

/**
 * ColorData
 * 
 * @author dayanto
 */
public class ColorData
{
	public double[] colorChannels;
	
	public ColorData(int colorValue)
	{
		colorChannels = new double[] { (colorValue >>> 24) & 0xff, (colorValue >>> 16) & 0xff, (colorValue >>> 8) & 0xff, colorValue & 0xff };
	}
	
	public ColorData(double[] colorChannels)
	{
		this.colorChannels = new double[4];
		System.arraycopy(colorChannels, 0, this.colorChannels, 0, colorChannels.length);
	}
	
	public int getIntValue()
	{
		for (int i = 0; i < colorChannels.length; i++) {
			if (colorChannels[i] > 255) {
				colorChannels[i] = 255;
			}
			if (colorChannels[i] < 0) {
				colorChannels[i] = 0;
			}
		}
		
		int pixelValue = 0;
		
		pixelValue += (int) (Math.round(colorChannels[0])) << 24;
		pixelValue += (int) (Math.round(colorChannels[1])) << 16;
		pixelValue += (int) (Math.round(colorChannels[2])) << 8;
		pixelValue += (int) (Math.round(colorChannels[3]));
		
		return pixelValue;
	}
}
