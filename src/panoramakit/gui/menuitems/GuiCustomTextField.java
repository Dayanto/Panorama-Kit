/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.menuitems;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

/** 
 * @author dayanto
 */
public class GuiCustomTextField extends GuiTextField
{
	public final int id;
	private boolean onlyNumbers;
	private boolean error = false;
	
	public GuiCustomTextField(FontRenderer fontRenderer, int id, int xPos, int yPos, int width, int height, boolean onlyNumbers)
	{
		super(fontRenderer, xPos, yPos, width, height);
		this.id = id;
		this.onlyNumbers = onlyNumbers;
	}
	
	
	/**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
	@Override
	public boolean textboxKeyTyped(char character, int keyCode)
	{
		if(onlyNumbers && !((character >= 48 && character <= 57) || character == '\b'))
		{
			return false;
		}
		else
		{
			return super.textboxKeyTyped(character, keyCode);
		}
	}
	
	public void setError(boolean error)
	{
		error = false;
		setTextColor(error ? 0xff0000 : 14737632);
	}
}
