/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.common.Configuration;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.menuitems.GuiCustomButton;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public abstract class GuiSettingsScreen extends GuiScreen
{
	protected Minecraft mc = Minecraft.getMinecraft();
	
	private String screenLabel;
	protected ArrayList<GuiCustomTextField> textFieldList = new ArrayList<GuiCustomTextField>();
	protected PreviewRenderer previewRenderer = new PreviewRenderer(Minecraft.getMinecraft().renderEngine);
	protected boolean disableInput = false;
	
	public GuiSettingsScreen(String screenLabel) {
		this.screenLabel = screenLabel;
	}
	
	/**
	 * Called whenever a button has been pressed. If there are multiple options,
	 * the button will have cycled among them on its own.
	 */
	public void buttonPressed(GuiButton button, int id, int currentOption)
	{}
	
	/**
	 * Called whenever a slider has moved. 
	 */
	public void sliderMoved(int id, float value)
	{}
	
	/**
	 * Called whenever a textfield has been updated
	 */
	public void textFieldUpdated(GuiCustomTextField textField, int id, String value)
	{}
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
		for (GuiTextField textfield : textFieldList) {
			// increments the counter that makes the cursor blink
			textfield.updateCursorCounter();
		}
	}
	
	@Override
	protected final void actionPerformed(GuiButton guibutton)
	{
		int value = 0;
		if (guibutton instanceof GuiCustomButton) {
			value = ((GuiCustomButton) guibutton).getValue();
		}
		buttonPressed(guibutton, guibutton.id, value);
	}
	
	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char character, int keyCode)
	{
		if (textFieldList.isEmpty()) {
			return;
		}
		
		// switch text fields with tab #DA570B
		if (character == '\t') {
			int activeTextField = -1;
			for (int i = 0; i < textFieldList.size(); i++) {
				if (textFieldList.get(i).isFocused()) {
					activeTextField = i;
					break;
				}
			}
			if (activeTextField >= 0)
			{
				textFieldList.get(activeTextField).setFocused(false);
				int nextTextField = (activeTextField + 1) % textFieldList.size();
				textFieldList.get(nextTextField).setFocused(true);
			}
		} else {
			for (GuiCustomTextField textfield : textFieldList) {
				boolean successfullyTyped = textfield.textboxKeyTyped(character, keyCode);
				if (successfullyTyped) {
					textfield.setError(false);
					textFieldUpdated(textfield, textfield.id, textfield.getText());
					break;
				}
			}
		}
	}
	
	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		for (GuiTextField textfield : textFieldList) {
			textfield.mouseClicked(par1, par2, par3);
		}
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		for (GuiCustomTextField textfield : textFieldList) {
			textfield.drawTextBox();
		}
		super.drawScreen(x, y, z);
	}
	
	/**
	 * Called when the screen is unloaded. 
	 * 
	 * Saves the current settings. This method doesn't need to have access to 
	 * the actual settings used in the menus, since they are using references 
	 * to the original configuration.
	 */
	@Override
	public void onGuiClosed()
	{
		Configuration config = PanoramaKit.instance.getConfig();
		if (config.hasChanged()) {
			config.save();
		}
		previewRenderer.clearPreview();
	}
	
	/**
	 * The label that the menus will put on the button opening this GUI.
	 */
	@Override
	public String toString()
	{
		return screenLabel;
	}
}
