/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.menuitems;

import net.minecraft.client.gui.GuiButton;

/** 
 * @author dayanto
 */
public class GuiCustomButton extends GuiButton implements HoverTips
{
	protected String baseString;
	protected String tipMessage;
	protected String[] options;
	protected int currentOption;
	
	public GuiCustomButton(int id, int xPos, int yPos, int width, int height, String baseString, String tipMessage, String[] options, int selectedOption)
	{
		super(id, xPos, yPos, width, height, null);
		this.baseString = baseString;
		this.tipMessage = tipMessage;
		this.options = options;
		this.currentOption = selectedOption;
		updateDisplayString();
	}
	
	public GuiCustomButton(int id, int xPos, int yPos, String baseString, String tipMessage, String[] options, int selectedOption)
	{
		this(id, xPos, yPos, 200, 20, baseString, tipMessage, options, selectedOption);
	}
	
	public GuiCustomButton(int id, int xPos, int yPos, String[] options, int selectedOption)
	{
		this(id, xPos, yPos, null, null, options, selectedOption);
	}
	
	public GuiCustomButton(int id, int xPos, int yPos, String baseString, String tipMessage)
	{
		this(id, xPos, yPos, baseString, tipMessage, null, 0);
	}
	
	/**
	 * Returns the id of the currently selected option;
	 */
	public int getCurrentOption()
	{
		return currentOption;
	}
	
	public void cycleOptions()
	{
		if(options == null) {
			return;
		}
		currentOption = (currentOption + 1) % options.length;
		updateDisplayString();
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

	/**
	 * Returns the tip message that is displayed when this button is hovered. Implements
	 * HoverTips, that is used in my custom menus.
	 */
	@Override
	public String getTipMessage()
	{
		return tipMessage;
	}

	/**
	 * Whether or not this button is hovered. Implements HoverTips, that is used in my
	 * custom menus
	 */
	@Override
	public boolean isHovered()
	{
		// TODO make sure that the variable is correct. The current variable is merely a guess
		return enabled && hovered; // Translates to 'isHovered'
	}
	
}
