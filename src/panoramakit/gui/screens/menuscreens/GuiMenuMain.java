/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens.menuscreens;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import panoramakit.gui.screens.settingsscreens.GuiSettingsBackground;

/** 
 * @author dayanto
 */
public class GuiMenuMain extends GuiScreen
{
	public static ArrayList<Class<GuiScreen>> menuLinks = new ArrayList<Class<GuiScreen>>();
	
	static
	{
		addMenuLink(GuiSettingsBackground.class);
		addMenuLink(GuiMenuPanoramas.class);
	}
	
	private Minecraft mc = Minecraft.getMinecraft();
	public String screenTitle = "Panorama Kit";
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		buttonList.clear();
		
		for(int i = 0; i < menuLinks.size(); i++) {
			buttonList.add(new GuiButton(i, width / 2 - 75, height / 6 + 24 * i, 150, 20, getScreen(i).toString()));
		}
		
		buttonList.add(new GuiButton(100, width / 2 - 75, 5 * height / 6 - 12, 150, 20, "Back"));
	}
	
	/**
	 * Called whenever a button has been pressed.
	 */
	@Override
	public void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 100) {
			mc.displayGuiScreen(null);
			return;
		}
		mc.displayGuiScreen(getScreen(guibutton.id));
	}
	
	/**
	* Draws the screen and all the components in it.
	*/
	@Override
	public void drawScreen(int x, int y, float z)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, height / 6 - 18, 0xffffff);
		super.drawScreen(x, y, z);
	}
	
	/**
	 * Adds a gui to this menu. The gui will be labeled by its toString() method. 
	 */
	@SuppressWarnings("unchecked")
	public static void addMenuLink(Class<? extends GuiScreen> screen)
	{
		menuLinks.add((Class<GuiScreen>) screen.asSubclass(GuiScreen.class));
	}
	
	/**
	 * Retrieves an instance of the GuiScreen from the list.
	 */
	public GuiScreen getScreen(int index)
	{
		Class<GuiScreen> guiscreen = menuLinks.get(index);
		try {
			GuiScreen screen = guiscreen.newInstance();
			return screen;
		} catch (Exception e) {
			return null;
		}
	}
}
