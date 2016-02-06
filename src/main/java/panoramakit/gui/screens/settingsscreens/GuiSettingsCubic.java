/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.screens.settingsscreens;

import java.io.File;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.client.gui.GuiButton;
import panoramakit.engine.render.renderers.CubicRenderer;
import panoramakit.engine.task.TaskManager;
import panoramakit.engine.task.tasks.DisplayGuiScreenTask;
import panoramakit.engine.task.tasks.RenderTask;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.menuitems.GuiCustomSlider;
import panoramakit.gui.menuitems.GuiCustomSliderOrientation;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.gui.screens.GuiRenderNotice;
import panoramakit.gui.screens.menuscreens.GuiMenuPanoramas;
import panoramakit.gui.settings.CubicSettings;
import panoramakit.mod.PanoramaKit;
import panoramakit.gui.settings.SharedSettings;
import panoramakit.gui.util.FileNumerator;

/**
 * @author dayanto
 */
public class GuiSettingsCubic extends GuiScreenSettings
{
	private static String screenTitle = "Cubic Panorama";
	private static String screenLabel = "Cubic (Raw)";
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;
	private static final int ORIENTATION = 2;
	private static final int ANGLE = 3;
	
	private static final int PREVIEW = 4;
	
	private static final int BACK = 5;
	private static final int CAPTURE = 6;
	
	private CubicSettings settings;
	
	public GuiSettingsCubic()
	{
		super(screenLabel);
		settings = new CubicSettings();
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
		
		// width offsets
		final int leftCol = width / 2 - 75 - 5;
		final int rightCol = width / 2 + 75 + 5;
		
		// height offsets
		final int contentStart = height / 2 - 84;
		final int bottomRow = height / 2 + 84;
		
		final int rowHeight = 24;
		
		// moving marker for stacking buttons
		int currentY = contentStart + 12;
		
		// textfields for width and height
		GuiCustomTextField fieldWidth = new GuiCustomTextField(fontRendererObj, WIDTH, leftCol - 12 - 64, currentY, 64, 20, true);
		GuiCustomTextField fieldHeight = new GuiCustomTextField(fontRendererObj, HEIGHT, leftCol + 12, currentY, 64, 20, true);
		fieldWidth.setText(String.valueOf(settings.getResolution() * 4));
		fieldHeight.setText(String.valueOf(settings.getResolution() * 3));
		textFieldList.add(fieldWidth);
		textFieldList.add(fieldHeight);
		
		// sliders beneath the textfields
		buttonList.add(new GuiCustomSliderOrientation(ORIENTATION, leftCol - 75, currentY += rowHeight, this, "Orientation", "", -180F, 180F, 0, SharedSettings.getOrientation()));
		buttonList.add(new GuiCustomSlider(ANGLE, leftCol - 75, currentY += rowHeight, this, "Angle", "", -90F, 90F, 0, settings.getAngle()));
		
		// preview button underneath the preview image
		buttonList.add((new GuiButton(PREVIEW, rightCol - 40, contentStart + 128 + 6, 80, 20, "Preview")));
		
		// bottom row buttons
		buttonList.add(new GuiButton(BACK, leftCol - 75, bottomRow, 150, 20, "Back"));
		buttonList.add(new GuiButton(CAPTURE, rightCol - 75, bottomRow, 150, 20, "Capture"));
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		// width offsets
		int leftCol = width / 2 - 75 - 5;
		int rightCol = width / 2 + 75 + 5;
		
		// height offsets
		int contentStart = height / 2 - 84;
		int bottomRow = height / 2 + 84;
		
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, screenTitle, width / 2, contentStart - 24, 0xffffff);
		
		// draw the strings related to the height and width
		drawCenteredString(fontRendererObj, "Width", leftCol - 32 - 12, contentStart, 0xa0a0a0);
		drawCenteredString(fontRendererObj, "Height", leftCol + 32 + 12, contentStart, 0xa0a0a0);
		drawString(fontRendererObj, "x", leftCol - 2, contentStart + 16, 0xffffff);
		
		// draw the preview box background
		drawRect(rightCol - 64 - 1, contentStart - 1, rightCol - 64 + 128 + 1, contentStart + 128 + 1, 0xff000000);
		drawRect(rightCol - 64, contentStart, rightCol - 64 + 128, contentStart + 128, 0xff282828);
		
		if(previewRenderer.previewAvailable()) {
			previewRenderer.drawCenteredImage(rightCol - 64, contentStart, 128, 128);
		}
		
		// draw the tip message
		drawCenteredString(fontRendererObj, tipMessage, width / 2, bottomRow - 12, 0xFFCF33);
		
		// draw buttons and texfields
		super.drawScreen(x, y, z);
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
			mc.displayGuiScreen(new GuiMenuPanoramas());
		}
		
		if (id == CAPTURE) 
		{
			FMLLog.info("Render cubic panorama");
			
			File renderFile = new File(PanoramaKit.instance.getRenderDir(), "Cubic.png");
			renderFile = FileNumerator.numberFile(renderFile);
				
			// create a cubic panorama
			CubicRenderer renderer = new CubicRenderer(settings.getResolution(), renderFile, SharedSettings.getOrientation(), settings.getAngle());
			TaskManager.instance.addTask(new RenderTask(renderer));
			
			mc.displayGuiScreen(new GuiRenderNotice());
		}
		
		if(id == PREVIEW) 
		{
			FMLLog.info("Render preview panorama");
			
			File previewFile = PreviewRenderer.getPreviewFile();
			
			int previewSize = 256;
			int resolution = previewSize / 4;
			
			// create a cubic panorama
			RenderTask renderTask = new RenderTask(new CubicRenderer(resolution, previewFile, SharedSettings.getOrientation(), settings.getAngle()));
			renderTask.setSilent();
			TaskManager.instance.addTask(renderTask);
			
			// restore the gui screen
			TaskManager.instance.addTask(new DisplayGuiScreenTask(this.getClass()));
			
			// display overlay and then close the gui
			capturePreview();
		}
	}
	
	/**
	 * Called whenever a slider has moved. 
	 */
	@Override
	public void sliderMoved(int id, float value)
	{
		if (id == ORIENTATION) {
			SharedSettings.setOrientation(value);
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
		int intValue = -1;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}
		
		if (id == WIDTH) {
			if (intValue >= 4) {
				settings.setResolution(intValue / 4);
				setTextField(HEIGHT, String.valueOf(settings.getResolution() * 3)); // update the height when the resolution changed
			} else {
				textField.setError(true);
			}
		}
		if (id == HEIGHT) {
			if (intValue >= 3) {
				settings.setResolution(intValue / 3);
				setTextField(WIDTH, String.valueOf(settings.getResolution() * 4)); // update the width when the resolution changed
			} else {
				textField.setError(true);
			}
		}
	}
}