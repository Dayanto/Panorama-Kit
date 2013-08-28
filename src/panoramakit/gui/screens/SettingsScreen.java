/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.Configuration;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public abstract class SettingsScreen extends GuiScreen
{
	public ArrayList<GuiTextField> textFieldList = new ArrayList<GuiTextField>();
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
		for (GuiTextField textfield : textFieldList)
		{
			// increments the counter that makes the cursor blink
			textfield.updateCursorCounter();
		}
	}
	
	@Override
	protected final void actionPerformed(GuiButton guibutton)
	{	
		
	}
	
	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		for (GuiTextField textfield : textFieldList)
		{
			textfield.mouseClicked(par1, par2, par3);
		}
	}
	
	@Override
	public void onGuiClosed()
	{
		Configuration config = PanoramaKit.instance.getConfig();
		if (config.hasChanged()) {
			config.save();
		}
	}
	
	public String translate(String key)
	{
		return I18n.func_135053_a(key);
	}
	
	public String[] translate(String[] keys)
	{
		String[] translated = new String[keys.length];
		for (int i = 0; i < keys.length; i++)
		{
			translated[i] = translate(keys[i]);
		}
		return translated;
	}
}
