/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.screens.settingsscreens;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.common.config.Configuration;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.menuitems.GuiCustomButton;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.gui.menuitems.HoverTips;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public abstract class GuiScreenSettings extends GuiScreen
{
	protected static Minecraft mc = Minecraft.getMinecraft();
	
	private String screenLabel;
	protected ArrayList<GuiCustomTextField> textFieldList = new ArrayList<GuiCustomTextField>();
	
	// used for rendering previews to the gui screen (does not actually create the previews themselves as might be implied)
	protected PreviewRenderer previewRenderer = new PreviewRenderer(Minecraft.getMinecraft().renderEngine);
	
	// used for displaying the overlay before rendering a preview
	private boolean capturingPreview = false;
	private boolean hasDrawnOverlayMessage = false;
	
	// disables all input if the user is browsing for files.
	protected boolean disableInput = false;
	
	protected String tipMessage = "";
	
	public GuiScreenSettings(String screenLabel) {
		this.screenLabel = screenLabel;
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		int currentOption = 0;
		if (guibutton instanceof GuiCustomButton) {
			GuiCustomButton customButton = (GuiCustomButton) guibutton;
			customButton.cycleOptions();
			currentOption = customButton.getCurrentOption();
		}
		buttonPressed(guibutton, guibutton.id, currentOption);
	}
	
	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char character, int keyCode) throws IOException
	{
		// make sure it's always possible to escape out of a menu that lacks a back button
		super.keyTyped(character, keyCode);
		
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
	protected void mouseClicked(int par1, int par2, int par3) throws IOException
	{
		super.mouseClicked(par1, par2, par3);
		for (GuiTextField textfield : textFieldList) {
			textfield.mouseClicked(par1, par2, par3);
		}
	}
	
	/**
	 * Called whenever a button has been pressed. If there are multiple options,
	 * the button will have cycled among them on its own. If the button is a
	 * vanilla GuiButton, it will 
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
	 * Returns a textfield with the id or null if none was found.
	 */
	public GuiCustomTextField getTextField(int id)
	{
		for(GuiCustomTextField textfield : textFieldList) {
			if(textfield.id == id) {
				return textfield;
			}
		}
		return null;
	}
	
	/**
	 * Attempts to change the value of a textfield with the chosen id. Returns
	 * whether it was successful.
	 */
	public boolean setTextField(int id, String value)
	{
		GuiCustomTextField textfield = getTextField(id);
		if(textfield == null) {
			return false;
		} else {
			textfield.setText(value);
			return true;
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
		
		// display an overlay if we're about to render a preview (it stays on the screen while it's rendering)
		if(capturingPreview){
			drawDefaultBackground();
			drawCenteredString(fontRendererObj, "Rendering Preview...", width / 2, height / 2, 0xe0e0e0);
			
			// waits one frame, so that what's drawn has been displayed to the screen before letting the rendering start.
			if(hasDrawnOverlayMessage) {
				Minecraft.getMinecraft().displayGuiScreen(null);
			}
			hasDrawnOverlayMessage = true;
		}
	}
	
	protected void capturePreview()
	{
		capturingPreview = true;
	}
	
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
		updateTipMessage();
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
	
	protected void updateTipMessage()
	{
		for(Object button : buttonList) {
			if(button instanceof HoverTips && ((HoverTips)button).isHovered()){
				tipMessage = ((HoverTips)button).getTipMessage();
				return;
			}
		}
		
		for(GuiCustomTextField textfield : textFieldList) {
			if(textfield instanceof HoverTips && ((HoverTips)textfield).isHovered()){
				tipMessage = ((HoverTips)textfield).getTipMessage();
				return;
			}
		}
		
		tipMessage = "";
	}
}
