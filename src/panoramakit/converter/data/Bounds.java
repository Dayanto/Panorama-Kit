/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.data;

/**
 * The bounds limit (rectangular) areas of the original image, preventing the position from going
 * outside of it. Certain projections map different regions of the original image differently and
 * need to make sure it's not sampling from outside of the appropriate region. However, all sampling
 * use an edge boundary.
 * 
 * @author dayanto
 */
public class Bounds
{
	int x1;
	int x2;
	int y1;
	int y2;
	
	public Bounds(int x1, int x2, int y1, int y2)
	{
		this.x1 = x1;
		this.x2 = x2 - 1;
		this.y1 = y1;
		this.y2 = y2 - 1;
	}
	
	public int getCappedX(int x)
	{
		if (x < x1) {
			x = x1;
		} else if (x > x2) {
			x = x2;
		}
		return x;
	}
	
	public int getCappedY(int y)
	{
		if (y < y1) {
			y = y1;
		} else if (y > y2) {
			y = y2;
		}
		return y;
	}
}
