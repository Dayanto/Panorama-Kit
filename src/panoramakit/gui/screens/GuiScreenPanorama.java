/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens;

import java.io.File;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSmallButton;
import panoramakit.converter.ProjectionConverter;
import panoramakit.converter.projections.CubicToEquirect;
import panoramakit.converter.projections.EquirectToPanorama;
import panoramakit.engine.render.CubicRenderer;
import panoramakit.engine.task.DisplayGuiScreenTask;
import panoramakit.engine.task.ProjectionConverterTask;
import panoramakit.engine.task.RenderTask;
import panoramakit.engine.task.TaskManager;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.menuitems.GuiCustomSlider;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.gui.settings.PanoramaSettings;
import panoramakit.mod.PanoramaKit;

/** 
 * TODO implement preview image
 * 
 * @author dayanto
 */
public class GuiScreenPanorama extends SettingsScreen
{
	private static Logger L = PanoramaKit.instance.L;
	private static String screenTitle = "Panorama";
	private static String screenLabel = "Panorama";
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;
	private static final int SAMPLE_SIZE = 2;
	private static final int ORIENTATION = 3;
	private static final int ANGLE = 4;
	
	private static final int PREVIEW = 5;
	
	private static final int BACK = 6;
	private static final int CAPTURE = 7;
	
	private PanoramaSettings settings;
	
	// used for displaying the overlay before rendering a preview
	private boolean renderingPreview = false;
	private boolean hasDrawn = false;
	
	// don't update the orientation and angle when we're rendering a preview
	private static boolean keepOrientation = false;
	
	public GuiScreenPanorama()
	{
		super(screenLabel);
		if(keepOrientation)	{
			settings = new PanoramaSettings();
			keepOrientation = false;
		} else {
			settings = new PanoramaSettings(mc.thePlayer.rotationYaw);
		}
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
		
		int xMid = width / 2;
		int yOffset = height / 2 - 60;
		int rowHeight = 24;
		
		int leftCol = xMid - 75 - 5;
		int rightCol = xMid + 75 + 5;
		
		GuiCustomTextField fieldWidth = new GuiCustomTextField(fontRenderer, WIDTH, leftCol - 12 - 64, yOffset, 64, 20, true);
		GuiCustomTextField fieldHeight = new GuiCustomTextField(fontRenderer, HEIGHT, leftCol + 12, yOffset, 64, 20, true);
		fieldWidth.setText(String.valueOf(settings.getPanoramaWidth()));
		fieldHeight.setText(String.valueOf(settings.getPanoramaHeight()));
		textFieldList.add(fieldWidth);
		textFieldList.add(fieldHeight);
		
		buttonList.add(new GuiCustomSlider(SAMPLE_SIZE, leftCol - 75, yOffset += rowHeight, this, "Sample Size", 1F, 8F, 0.5F, settings.getSampleSize()));
		buttonList.add(new GuiCustomSlider(ORIENTATION, leftCol - 75, yOffset += rowHeight, this, "Orientation", 0F, 360F, 0, settings.getOrientation()));
		buttonList.add(new GuiCustomSlider(ANGLE, leftCol - 75, yOffset += rowHeight, this, "Angle", -90F, 90F, 0, settings.getAngle()));
		
		buttonList.add((new GuiButton(PREVIEW, rightCol - 40, yOffset + 36, 80, 20, "Preview")));
		
		yOffset += 72;
		buttonList.add(new GuiSmallButton(BACK, leftCol - 75, yOffset, "Back"));
		buttonList.add(new GuiSmallButton(CAPTURE, rightCol - 75, yOffset, "Capture"));
	}
	
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		int xMid = width / 2;
		int yOffset = height / 2 - 60;
		
		int leftCol = xMid - 75 - 5;
		int rightCol = xMid + 75 + 5;
		
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, xMid, yOffset - 36, 0xffffff);
		
		drawCenteredString(fontRenderer, "Width", leftCol - 32 - 12, yOffset - 14, 0xa0a0a0);
		drawCenteredString(fontRenderer, "Height", leftCol + 32 + 12, yOffset - 14, 0xa0a0a0);
		drawString(fontRenderer, "x", leftCol - 1, yOffset + 6, 0xffffff);
		
//		drawRect(rightCol - 64 - 1, yOffset - 24 - 1, rightCol - 64 + 128 + 1, yOffset - 24 + 128 + 1, 0x80808080);
//		drawRect(rightCol - 64, yOffset - 24, rightCol - 64 + 128, yOffset - 24 + 128, 0x80404040);
		if(previewRenderer.previewAvailable()) {
			previewRenderer.drawCenteredImage(rightCol - 64, yOffset - 24, 128, 128);
		}
		super.drawScreen(x, y, z);
		
		// display an overlay if we're about to render a preview (it stays on the screen while it's rendering)
		if(renderingPreview){
			drawDefaultBackground();
			drawCenteredString(fontRenderer, "Rendering Preview...", width / 2, height / 2, 0xe0e0e0);
			
			// Waits until what's drawn has been displayed to the screen before letting the rendering start.
			if(hasDrawn) {
				Minecraft.getMinecraft().displayGuiScreen(null);
			}
			hasDrawn = true;
		}
	}
	
	/**
	 * Called whenever a button has been pressed. If there are multiple options,
	 * the button will have cycled among them on its own.
	 */
	@Override
	public void buttonPressed(GuiButton button, int id, int value)
	{
		if (id == BACK) 
		{
			mc.displayGuiScreen(new GuiScreenImages());
		}
		
		if (id == CAPTURE) 
		{
			L.info("Render panorama");
			
			String filePath = new File(PanoramaKit.instance.getRenderDir(), "Panorama.png").getPath();
			
			EquirectToPanorama panorama;
			ProjectionConverter converter;
			try {
				panorama = new EquirectToPanorama(new CubicToEquirect(), settings.getPanoramaWidth(), settings.getPanoramaHeight());
				converter = new ProjectionConverter(panorama, filePath);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			// create a cubic base image
			int sampleResolution = (int) (settings.getPanoramaWidth() * settings.getSampleSize() / 4);
			TaskManager.instance.addTask(new RenderTask(new CubicRenderer(sampleResolution, filePath, settings.getOrientation(), settings.getAngle())));
			
			// convert it to a panorama
			TaskManager.instance.addTask(new ProjectionConverterTask(converter));
			
			mc.displayGuiScreen(new GuiRenderNotice());
		}
		
		if(id == PREVIEW) 
		{
			L.info("Render preview panorama");
			
			String filePath = PreviewRenderer.getPreviewFile().getPath();
			
			double fullWidth = settings.getPanoramaWidth();
			double fullHeight = settings.getPanoramaHeight();
			int panoramaWidth = fullWidth > fullHeight ? 128 : (int) (128 * fullWidth / fullHeight);
			int panoramaHeight = fullHeight > fullWidth ? 128 : (int) (128 * fullHeight / fullWidth);
			
			EquirectToPanorama panorama;
			ProjectionConverter converter;
			try {
				panorama = new EquirectToPanorama(new CubicToEquirect(), panoramaWidth, panoramaHeight);
				converter = new ProjectionConverter(panorama, filePath);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			// create a cubic base image
			int sampleResolution = 256;
			TaskManager.instance.addTask(new RenderTask(new CubicRenderer(sampleResolution, filePath, settings.getOrientation(), settings.getAngle())));
			
			// restore the gui screen
			TaskManager.instance.addTask(new DisplayGuiScreenTask(this.getClass()));
			
			// convert it to a panorama
			TaskManager.instance.addTask(new ProjectionConverterTask(converter));
			
			renderingPreview = true;
			keepOrientation = true;
		}
	}
	
	/**
	 * Called whenever a slider has moved. 
	 */
	@Override
	public void sliderMoved(int id, float value)
	{
		if (id == SAMPLE_SIZE) {
			settings.setSampleSize(value);
		}
		if (id == ORIENTATION) {
			settings.setOrientation(value);
		}
		if (id == ANGLE) {
			settings.setAngle(value);
		}
	}
	
	/**
	 * Called whenever a textfield has been updated
	 */
	public void textFieldUpdated(GuiCustomTextField textField, int id, String value)
	{
		int intValue = 0;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}
		
		if (id == WIDTH) {
			if (intValue > 0) {
				settings.setPanoramaWidth(intValue);
			} else {
				textField.setError(true);
			}
		}
		if (id == HEIGHT) {
			if (intValue > 0) {
				settings.setPanoramaHeight(intValue);
			} else {
				textField.setError(true);
			}
		}
	}
	
}
