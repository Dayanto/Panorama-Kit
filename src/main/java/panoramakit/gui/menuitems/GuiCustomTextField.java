/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.menuitems;

import static org.lwjgl.input.Keyboard.*;
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
	
	// store variables for drawing
	protected int xPos;
	protected int yPos;
	protected int width;
	protected int height;
	
	private static int[] specialCharacters = {KEY_ESCAPE,KEY_BACK,KEY_HOME,KEY_LEFT,KEY_RIGHT,KEY_END,KEY_DELETE};
	
	public GuiCustomTextField(FontRenderer fontRenderer, int id, int xPos, int yPos, int width, int height, boolean onlyNumbers)
	{
		super(0/*?*/, fontRenderer, xPos, yPos, width, height);
		this.id = id;
		this.onlyNumbers = onlyNumbers;
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
	
	
	/**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
	@Override
	public boolean textboxKeyTyped(char character, int keyCode)
	{
		boolean specialCharacter = false;		
		for(int specialKeyCode : specialCharacters) {
			if(specialKeyCode == keyCode){
				specialCharacter = true;
				break;
			}
		}
		
		if(onlyNumbers && !(character >= 48 && character <= 57) && !specialCharacter)
		{
			return false;
		}
		else
		{
			return super.textboxKeyTyped(character, keyCode); 
		}
	}
	
	@Override
	public void drawTextBox()
	{
		if (error)
        {
			// draw textfield background
            drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
            drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, 0xff800000);
        }
		super.drawTextBox();
	}
	
	/**
	 * If there has been an error, this turns the background red until it's turned back.
	 */
	public void setError(boolean error)
	{
		this.error = error;
		setEnableBackgroundDrawing(!error);
	}
}
