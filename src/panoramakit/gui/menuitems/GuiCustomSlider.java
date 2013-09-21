/*
 ** 2012 August 11
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package panoramakit.gui.menuitems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import panoramakit.gui.screens.settingsscreens.GuiScreenSettings;

/**
 * Customizable slider button.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class GuiCustomSlider extends GuiButton implements HoverTips
{
	private GuiScreenSettings settingsScreen;
	
	private final int sliderWidth = 4;
	private final int widthOffs = sliderWidth * 2;
	
	protected float sliderValue;
	protected float min;
	protected float max;
	protected float step;
	protected String baseString;
	protected String tipMessage;
	
	public boolean hovered = false;
	public boolean dragged = false;
	
	public GuiCustomSlider(int id, int x, int y, GuiScreenSettings settingsScreen, String baseString, String tipMessage, float min, float max, float step, float value)
	{
		super(id, x, y, 150, 20, null);
		
		this.settingsScreen = settingsScreen;
		this.baseString = baseString;
		this.tipMessage = tipMessage;
		
		this.min = min;
		this.max = max;
		this.step = step;
		
		setValue(value);
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int mouseX, int mouseY)
	{
		if (drawButton && displayString == null) {
			updateDisplayString();
		}
		
		super.drawButton(par1Minecraft, mouseX, mouseY);
	}
	
	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	@Override
	protected int getHoverState(boolean par1)
	{
		return 0;
	}
	
	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	protected void mouseDragged(Minecraft mc, int x, int y)
	{
		if (drawButton) {
			if (dragged) {
				updateValue(x);
			}
			
			GL11.glColor4f(1, 1, 1, 1);
			
			int sliderPos = (int) (sliderValue * (float) (width - widthOffs));
			
			drawTexturedModalRect(xPosition + sliderPos, yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(xPosition + sliderPos + sliderWidth, yPosition, 196, 66, 4, 20);
		}
	}
	
	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	@Override
	public boolean mousePressed(Minecraft mc, int x, int y)
	{
		if (super.mousePressed(mc, x, y)) {
			updateValue(x);
			dragged = true;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(int x, int y)
	{
		dragged = false;
	}
	
	protected void updateValue(int x)
	{
		sliderValue = (float) (x - (xPosition + sliderWidth)) / (float) (width - widthOffs);
		sliderValue = MathHelper.clamp_float(sliderValue, 0, 1);
		
		if (step > 0) {
			float tmp = 1 / (step / (max - min));
			sliderValue = Math.round(sliderValue * tmp) / tmp;
		}
		
		settingsScreen.sliderMoved(id, getValue());
		updateDisplayString();
	}
	
	public float getValue()
	{
		return min + (max - min) * sliderValue;
	}
	
	private void setValue(float value)
	{
		sliderValue = (value - min) / (max - min);
	}
	
	public void updateDisplayString()
	{
		displayString = String.format(baseString + ": %.1f", getValue());
	}
	
	@Override
	public String getTipMessage()
	{
		return tipMessage;
	}
	
	@Override
	public boolean isHovered()
	{
		if(!enabled) return false;
		return field_82253_i; // field_82253_i translates to "isHovered"
	}
}