/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.screens.settingsscreens;

import java.io.File;
import java.util.logging.Logger;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSmallButton;
import panoramakit.converter.ProjectionConverter;
import panoramakit.converter.projections.CubicToEquirect;
import panoramakit.converter.projections.EquirectToCylindrical;
import panoramakit.engine.render.CubicRenderer;
import panoramakit.engine.task.Task;
import panoramakit.engine.task.TaskManager;
import panoramakit.engine.task.tasks.DisplayGuiScreenTask;
import panoramakit.engine.task.tasks.RenderTask;
import panoramakit.engine.task.threadedtasks.ProjectionConverterTask;
import panoramakit.gui.PreviewRenderer;
import panoramakit.gui.menuitems.GuiCustomSlider;
import panoramakit.gui.menuitems.GuiCustomSliderOrientation;
import panoramakit.gui.menuitems.GuiCustomSliderSample;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.gui.screens.GuiRenderNotice;
import panoramakit.gui.screens.menuscreens.GuiMenuPanoramas;
import panoramakit.gui.settings.CylindricalSettings;
import panoramakit.mod.PanoramaKit;
import panoramakit.engine.render.ImageLink;

/**
 * @author dayanto
 */
public class GuiSettingsCylindrical extends GuiScreenSettings
{
	private static Logger L = PanoramaKit.instance.L;
	private static String screenTitle = "Cylindrical Panorama";
	private static String screenLabel = "Cylindrical";
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;
	private static final int SAMPLE_SIZE = 2;
	private static final int ORIENTATION = 3;
	private static final int ANGLE = 4;
	
	private static final int PREVIEW = 5;
	
	private static final int BACK = 6;
	private static final int CAPTURE = 7;
	
	private CylindricalSettings settings;
	
	// make sure we don't update the orientation and angle after rendering a preview
	private static boolean keepOrientation = false;
	
	public GuiSettingsCylindrical()
	{
		super(screenLabel);
		if(keepOrientation)	{
			settings = new CylindricalSettings();
			keepOrientation = false;
		} else {
			settings = new CylindricalSettings(mc.thePlayer.rotationYaw);
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
		GuiCustomTextField fieldWidth = new GuiCustomTextField(fontRenderer, WIDTH, leftCol - 12 - 64, currentY, 64, 20, true);
		GuiCustomTextField fieldHeight = new GuiCustomTextField(fontRenderer, HEIGHT, leftCol + 12, currentY, 64, 20, true);
		fieldWidth.setText(String.valueOf(settings.getWidth()));
		fieldHeight.setText(String.valueOf(settings.getHeight()));
		textFieldList.add(fieldWidth);
		textFieldList.add(fieldHeight);
		
		// sliders beneath the textfields
		buttonList.add(new GuiCustomSliderSample(SAMPLE_SIZE, leftCol - 75, currentY += rowHeight, this, "Sample Size", "Warning! Large samples eat lots of RAM!", 1F, 8F, 0.5F, settings.getSampleSize()));
		buttonList.add(new GuiCustomSliderOrientation(ORIENTATION, leftCol - 75, currentY += rowHeight, this, "Orientation", "", 0F, 360F, 0, settings.getOrientation()));
		buttonList.add(new GuiCustomSlider(ANGLE, leftCol - 75, currentY += rowHeight, this, "Angle", "", -90F, 90F, 0, settings.getAngle()));
		
		// preview button underneath the preview image
		buttonList.add((new GuiButton(PREVIEW, rightCol - 40, contentStart + 128 + 6, 80, 20, "Preview")));
		
		// bottom row buttons
		buttonList.add(new GuiSmallButton(BACK, leftCol - 75, bottomRow, "Back"));
		buttonList.add(new GuiSmallButton(CAPTURE, rightCol - 75, bottomRow, "Capture"));
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
		drawCenteredString(fontRenderer, screenTitle, width / 2, contentStart - 24, 0xffffff);
		
		// draw the strings related to the height and width
		drawCenteredString(fontRenderer, "Width", leftCol - 32 - 12, contentStart, 0xa0a0a0);
		drawCenteredString(fontRenderer, "Height", leftCol + 32 + 12, contentStart, 0xa0a0a0);
		drawString(fontRenderer, "x", leftCol - 2, contentStart + 16, 0xffffff);
		
		// draw the preview box background
		drawRect(rightCol - 64 - 1, contentStart - 1, rightCol - 64 + 128 + 1, contentStart + 128 + 1, 0xff000000);
		drawRect(rightCol - 64, contentStart, rightCol - 64 + 128, contentStart + 128, 0xff282828);
		
		if(previewRenderer.previewAvailable()) {
			previewRenderer.drawCenteredImage(rightCol - 64, contentStart, 128, 128);
		}
		
		// draw the sample resolution
		int sampleResolution = (int)(settings.getWidth() / 4 * settings.getSampleSize());
		int sampleWidth = sampleResolution * 4;
		int sampleHeight = sampleResolution * 3;
		drawCenteredString(fontRenderer, "Sampled image: " + sampleWidth + "x" + sampleHeight, leftCol, bottomRow - 24 - 4, 0xa0a0a0);
		
		// draw the tip message
		drawCenteredString(fontRenderer, tipMessage, width / 2, bottomRow - 12, 0xFFCF33);
		
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
			L.info("Render cylindrical panorama");
			
			File renderFile = new File(PanoramaKit.instance.getRenderDir(), "Cylindrical.png");
			renderFile = numberFile(renderFile);
			
			EquirectToCylindrical panorama = new EquirectToCylindrical(new CubicToEquirect(), settings.getWidth(), settings.getHeight());;
			ProjectionConverter converter = new ProjectionConverter(panorama, renderFile);
			
			// create a cubic base image
			int sampleResolution = (int) (settings.getWidth() / 4 * settings.getSampleSize());
			CubicRenderer renderer = new CubicRenderer(sampleResolution, renderFile, settings.getOrientation(), settings.getAngle());
			TaskManager.instance.addTask(new RenderTask(renderer));
			
			// convert it to a panorama
			TaskManager.instance.addTask(new ProjectionConverterTask(converter));
			
			// create an image link for sending images between tasks and attach it to the two sides
			ImageLink imageLink = new ImageLink();
			renderer.setImageLink(imageLink);
			converter.setImageLink(imageLink);
			
			mc.displayGuiScreen(new GuiRenderNotice());
		}
		
		if(id == PREVIEW) 
		{
			L.info("Render preview panorama");
			
			File previewFile = PreviewRenderer.getPreviewFile();
			
			int previewSize = 256;
			double fullWidth = settings.getWidth();
			double fullHeight = settings.getHeight();
			int panoramaWidth = fullWidth > fullHeight ? previewSize : (int) (previewSize * fullWidth / fullHeight);
			int panoramaHeight = fullHeight > fullWidth ? previewSize : (int) (previewSize * fullHeight / fullWidth);
			
			EquirectToCylindrical panorama;
			ProjectionConverter converter;
			try {
				panorama = new EquirectToCylindrical(new CubicToEquirect(), panoramaWidth, panoramaHeight);
				converter = new ProjectionConverter(panorama, previewFile);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			// create a cubic base image
			int sampleResolution = 256;
			CubicRenderer renderer = new CubicRenderer(sampleResolution, previewFile, settings.getOrientation(), settings.getAngle());
			TaskManager.instance.addTask(new RenderTask(renderer));
			
			// restore the gui screen
			TaskManager.instance.addTask(new DisplayGuiScreenTask(this.getClass()));
			
			// convert it to a panorama
			Task projectionTask = new ProjectionConverterTask(converter);
			projectionTask.setSilent();
			TaskManager.instance.addTask(projectionTask);
			
			// create an image link for sending images between tasks and attach it to the two sides
			ImageLink imageLink = new ImageLink();
			renderer.setImageLink(imageLink);
			converter.setImageLink(imageLink);
			
			// display overlay and then close the gui
			capturePreview();
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
		int intValue = -1;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}
		
		if (id == WIDTH) {
			if (intValue >= 4) {
				settings.setWidth(intValue);
			} else {
				textField.setError(true);
			}
		}
		if (id == HEIGHT) {
			if (intValue >= 3) {
				settings.setHeight(intValue);
			} else {
				textField.setError(true);
			}
		}
	}
}
