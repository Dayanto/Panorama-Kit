/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.settings;

/** 
 * @author dayanto
 */
public class SharedSettings
{
	private static float currentOrientation = 0;
	
	public static float getOrientation()
	{
		return currentOrientation;
	}
	public static void setOrientation(float orientation)
	{
		currentOrientation = ((orientation + 180) % 360 + 360) % 360 - 180;
	}
}
