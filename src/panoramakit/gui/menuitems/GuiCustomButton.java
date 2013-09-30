/* 
 * This code isn't copyrighted. Do what you want with it. :) 
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

	/* (non-Javadoc)
	 * @see panoramakit.gui.menuitems.HoverTips#getTipMessage()
	 */
	@Override
	public String getTipMessage()
	{
		return tipMessage;
	}

	/* (non-Javadoc)
	 * @see panoramakit.gui.menuitems.HoverTips#isHovered()
	 */
	@Override
	public boolean isHovered()
	{
		if(!enabled) return false;
		return field_82253_i; // field_82253_i translates to "isHovered"
	}
	
}
