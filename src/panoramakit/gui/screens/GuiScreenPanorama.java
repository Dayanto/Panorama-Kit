/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSmallButton;
import panoramakit.converter.ProjectionConverter;
import panoramakit.converter.projections.CubicToEquirect;
import panoramakit.converter.projections.EquirectToPanorama;
import panoramakit.engine.render.CubicRenderer;
import panoramakit.engine.task.ProjectionConverterTask;
import panoramakit.engine.task.RenderTask;
import panoramakit.engine.task.TaskManager;
import panoramakit.gui.menuitems.GuiCustomSlider;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.gui.settings.PanoramaSettings;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class GuiScreenPanorama extends SettingsScreen
{
	private static String screenTitle = "Panorama";
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;
	private static final int SAMPLE_SIZE = 2;
	private static final int ORIENTATION = 3;
	private static final int ANGLE = 4;
	private static final int FILE_PATH = 5;
	
	private static final int BACK = 6;
	private static final int CAPTURE = 7;
	
	private PanoramaSettings settings;
	
	public GuiScreenPanorama()
	{
		super("Panorama");
		settings = new PanoramaSettings();
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		buttonList.clear();
		textFieldList.clear();
		
		int x = width / 2;
		int y = 64;
		int rowHeight = 24;
		GuiCustomTextField fieldWidth = new GuiCustomTextField(fontRenderer, WIDTH, x - 12 - 64, y, 64, 20, true);
		GuiCustomTextField fieldHeight = new GuiCustomTextField(fontRenderer, HEIGHT, x + 12, y, 64, 20, true); 
		fieldWidth.setText(String.valueOf(settings.getPanoramaWidth()));
		fieldHeight.setText(String.valueOf(settings.getPanoramaHeight()));
		textFieldList.add(fieldWidth);
		textFieldList.add(fieldHeight);
		buttonList.add(new GuiCustomSlider(SAMPLE_SIZE, x - 75, y += rowHeight, this, "Sample Size", 1F, 8F, 0.5F,  settings.getSampleSize()));
		buttonList.add(new GuiCustomSlider(ORIENTATION, x - 75, y += rowHeight, this, "Orientation", 0F, 360F, 0,  settings.getOrientation()));
		buttonList.add(new GuiCustomSlider(ANGLE, x - 75, y += rowHeight, this, "Angle", -90F, 90F, 0,  settings.getAngle()));
		buttonList.add(new GuiButton(BACK, x + 75, y += rowHeight, 30, 24, "Browse"));
		
		y += 36;
		buttonList.add(new GuiSmallButton(BACK, x - 155, y, "Back"));
		buttonList.add(new GuiSmallButton(CAPTURE, x + 5, y, "Capture"));
	}
	
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		int xs = width / 2;
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, xs, 32, 0xffffff);
		
		drawCenteredString(fontRenderer, "Width", xs - 32 - 12, 50, 0xa0a0a0);
        drawCenteredString(fontRenderer, "Height", xs + 32 + 12, 50, 0xa0a0a0);
        drawString(fontRenderer, "x", xs - 1, 70, 0xffffff);
        
        super.drawScreen(x, y, z);
	}
	
	/**
	 * Called whenever a button has been pressed. If there are multiple options,
	 * the button will have cycled among them on its own.
	 */
	@Override
	public void buttonPressed(GuiButton button, int id, int value)
	{
		if(id == BACK) {
			mc.displayGuiScreen(new GuiScreenImages());
		}
		if(id == CAPTURE) {
			PanoramaKit.instance.L.info("Render panorama");
			
			// create a cubic base image
			int sampleResolution = (int)(settings.getPanoramaWidth() * settings.getSampleSize() / 4);
			TaskManager.instance.addTask(new RenderTask(new CubicRenderer(sampleResolution, settings.getFilePath(), settings.getOrientation(), settings.getAngle())));
			
			// convert it to a panorama
			try {
				EquirectToPanorama panorama = new EquirectToPanorama(new CubicToEquirect(), settings.getPanoramaWidth(), settings.getPanoramaHeight());
				ProjectionConverter converter = new ProjectionConverter(panorama, settings.getFilePath());
				TaskManager.instance.addTask(new ProjectionConverterTask(converter));
			} catch (Exception e) {
			}
			mc.displayGuiScreen(null);
		}
		
	}
	
	/**
	 * Called whenever a slider has moved. 
	 */
	@Override
	public void sliderMoved(int id, float value)
	{
		if(id == SAMPLE_SIZE) {
			settings.setSampleSize(value);
		}
		if(id == ORIENTATION) {
			settings.setOrientation(value);
		}
		if(id == ANGLE) {
			settings.setAngle(value);
		}
	}
	
	/**
	 * Called whenever a textfield has been updated
	 */
	public void textFieldUpdated(GuiCustomTextField textField, int id, String value)
	{	
		int intValue = 0;
		try{
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e){}
		
		if(id == WIDTH) {
			if(intValue > 0){
				settings.setPanoramaWidth(intValue);
			} else {
				textField.setError(true);
			}
		}
		if(id == HEIGHT) {
			if(intValue > 0){
				settings.setPanoramaHeight(intValue);
			} else {
				textField.setError(true);
			}
		}
	}
	
}
