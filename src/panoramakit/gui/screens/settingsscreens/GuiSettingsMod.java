/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.screens.settingsscreens;

import net.minecraft.client.gui.GuiButton;
import panoramakit.gui.menuitems.GuiCustomButton;
import panoramakit.gui.screens.menuscreens.GuiMenuMain;
import panoramakit.gui.settings.ModSettings;

/** 
 * @author dayanto
 */
public class GuiSettingsMod extends GuiScreenSettings
{
	private final static String screenTitle = "Settings";
	private final static String screenLabel = "Settings";

	private static final int NUMBERING = 0;
	private static final int BACK = 1;
	
	private final ModSettings settings = new ModSettings();
	
	/**
	 * @param screenLabel
	 */
	public GuiSettingsMod()
	{
		super(screenLabel);
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	//@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		buttonList.clear();
		
		buttonList.add(new GuiCustomButton(NUMBERING, width / 2 - 75, height / 6, 150, 20, "File Numbering", "", ModSettings.fileNumberingOptions, settings.getFileNumbering()));
		buttonList.add(new GuiButton(BACK, width / 2 - 75, 5 * height / 6 - 12, 150, 20, "Back"));
	}
	
	/**
	 * Called whenever a button has been pressed.
	 */
	@Override
	public void buttonPressed(GuiButton guibutton, int id, int value)
	{
		if (id == BACK) {
			mc.displayGuiScreen(new GuiMenuMain());
			return;
		}
		if (id == NUMBERING) {
			settings.setFileNumbering(value);
			return;
		}
	}
	
	/**
	* Draws the screen and all the components in it.
	*/
	@Override
	public void drawScreen(int x, int y, float z)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, screenTitle, width / 2, height / 6 - 18, 0xffffff);
		super.drawScreen(x, y, z);
	}
	
}
