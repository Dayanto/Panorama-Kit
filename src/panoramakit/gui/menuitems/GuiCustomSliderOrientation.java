/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.menuitems;

import panoramakit.gui.screens.settingsscreens.GuiScreenSettings;

/**
 * @author dayanto
 */
public class GuiCustomSliderOrientation extends GuiCustomSlider
{
	public static final String[] POINTS_OF_COMPASS = {"South","West","North","East"};
	
	public GuiCustomSliderOrientation(int id, int x, int y, GuiScreenSettings settingsScreen, String baseString, String tipMessage, float min, float max, float step, float value)
	{
		super(id, x, y, settingsScreen, baseString, tipMessage, min, max, step, value);
	}
	
	public void updateDisplayString()
	{
		// limit the value to the 0 -> 360 range
		double value = (getValue() % 360 + 360) % 360;
		int index = (int) Math.round(POINTS_OF_COMPASS.length * value / 360) % POINTS_OF_COMPASS.length;
		displayString = String.format(baseString + ": %.1f %s", getValue(), POINTS_OF_COMPASS[index]);
	}
}