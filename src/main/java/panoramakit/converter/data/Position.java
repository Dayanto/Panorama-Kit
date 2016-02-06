/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter.data;

/**
 * The position is a floating point based position representing a pixel hovering at an abstract
 * location in between pixels. (The color value of this pixel is then calculated by looking at the
 * distance to the surrounding pixels.)
 * 
 * @author dayanto
 */
public class Position
{
	public double x;
	public double y;
	
	public Position(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getXFraction()
	{
		return (x % 1 + 1) % 1;
	}
	
	public double getYFraction()
	{
		return (y % 1 + 1) % 1;
	}
}
