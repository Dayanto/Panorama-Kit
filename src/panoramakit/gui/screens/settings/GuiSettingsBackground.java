/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens.settings;

import panoramakit.gui.screens.menu.GuiMenuMain;
import net.minecraft.client.gui.GuiButton;


/** 
 * @author dayanto
 */
public class GuiSettingsBackground extends GuiScreenSettings
{
	private static final int BACK = 6;
	
	public GuiSettingsBackground()
	{
		super("Make a Background");
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		final int bottomRow = height / 2 + 84;
		buttonList.add(new GuiButton(BACK, width / 2 - 100, bottomRow, "Back"));
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Placeholder menu", width / 2, height / 2 - 24, 0xa0a0a0);
		drawCenteredString(fontRenderer, "Backgrounds will be added following Minecraft 1.7", width / 2, height / 2 - 12, 0xa0a0a0);
		super.drawScreen(x, y, z);
	}
	
	@Override
	public void buttonPressed(GuiButton button, int id, int value)
	{
		if (id == BACK) 
		{
			mc.displayGuiScreen(new GuiMenuMain());
		}
	}
}
