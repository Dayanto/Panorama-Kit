/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.menuitems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/** 
 * @author dayanto
 */
public class GuiCustomButton extends GuiButton
{
	private String baseString;
	private String[] options;
	private int currentOption;
	
	public GuiCustomButton(int id, int xPos, int yPos, String baseString, String[] options, int selectedOption)
	{
		super(id, xPos, yPos, null);
		currentOption = selectedOption;
		updateDisplayString();
	}
	
	public GuiCustomButton(int id, int xPos, int yPos, String[] options, int selectedOption)
	{
		this(id, xPos, yPos, null, options, selectedOption);
	}
	
	public GuiCustomButton(int id, int xPos, int yPos, String baseString)
	{
		this(id, xPos, yPos, baseString, null, 0);
	}
	
	public boolean mousePressed(Minecraft mc, int xPos, int yPos)
	{
		boolean pressed = super.mousePressed(mc, xPos, yPos);
		if(pressed) {
			cycleOptions();
			updateDisplayString();
		}
		return pressed;
	}
	
	/**
	 * Returns the id of the currently selected option;
	 */
	public int getValue()
	{
		return currentOption;
	}
	
	private void cycleOptions()
	{
		if(options == null) {
			return;
		}
		currentOption = (currentOption + 1) % options.length;
	}
	
	private void updateDisplayString()
	{
		displayString = "";
		if(baseString != null) {
			displayString += baseString;
			if(options != null) {
				displayString += ": ";
			}
		}
		if(options != null) {
			displayString += options[currentOption];
		}
	}
	
}
