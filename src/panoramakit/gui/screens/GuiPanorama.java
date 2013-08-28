/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens;

import panoramakit.gui.settings.PanoramaSettings;

/** 
 * @author dayanto
 */
public class GuiPanorama extends SettingsScreen
{
	public PanoramaSettings settings;
	
	public GuiPanorama()
	{
		settings = new PanoramaSettings();
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui()
	{
		buttonList.clear();
		textFieldList.clear();
		
		// TODO add buttons and sliders
	}
}
