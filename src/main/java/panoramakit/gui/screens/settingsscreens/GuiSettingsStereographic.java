/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.screens.settingsscreens;

import java.io.File;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.client.gui.GuiButton;
import panoramakit.converter.ProjectionConverter;
import panoramakit.converter.projections.CubicToEquirect;
import panoramakit.converter.projections.EquirectToStereographic;
import panoramakit.engine.render.ImageLink;
import panoramakit.engine.render.renderers.CubicRenderer;
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
import panoramakit.gui.settings.StereographicSettings;
import panoramakit.mod.PanoramaKit;
import panoramakit.gui.settings.SharedSettings;
import panoramakit.gui.util.FileNumerator;

/**
 * @author dayanto
 */
public class GuiSettingsStereographic extends GuiScreenSettings
{
	private static String screenTitle = "Stereographic \"Little planet\" Panorama";
	private static String screenLabel = "Stereographic \"Little planet\"";
	
	private static final int WIDTH = 0;
	private static final int HEIGHT = 1;
	private static final int SAMPLE_SIZE = 2;
	private static final int ORIENTATION = 3;
	private static final int ANGLE = 4;
	private static final int FIELD_OF_VIEW = 5;
	
	private static final int PREVIEW = 6;
	
	private static final int BACK = 7;
	private static final int CAPTURE = 8;
	
	private StereographicSettings settings;
	
	public GuiSettingsStereographic()
	{
		super(screenLabel);
		settings = new StereographicSettings();
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
		fieldWidth.setText(String.valueOf(settings.getWidth()));
		fieldHeight.setText(String.valueOf(settings.getHeight()));
		textFieldList.add(fieldWidth);
		textFieldList.add(fieldHeight);
		
		// sliders beneath the textfields
		buttonList.add(new GuiCustomSliderSample(SAMPLE_SIZE, leftCol - 75, currentY += rowHeight, this, "Sample Size", "Warning! Large samples eat lots of RAM!", 1F, 8F, 0.5F, settings.getSampleSize()));
		buttonList.add(new GuiCustomSliderOrientation(ORIENTATION, leftCol - 75, currentY += rowHeight, this, "Orientation", "", -180F, 180F, 0, SharedSettings.getOrientation()));
		buttonList.add(new GuiCustomSlider(ANGLE, leftCol - 75, currentY += rowHeight, this, "Angle", "", -90F, 90F, 0, settings.getAngle()) {
			public void updateDisplayString() { 
				displayString = String.format(baseString + ": %.1f", getValue());
				if(getValue() == 90) displayString = "Planet";
				if(getValue() == -90) displayString = "Inverted";
			}
		});
		buttonList.add(new GuiCustomSlider(FIELD_OF_VIEW, leftCol - 75, currentY += rowHeight, this,"Field of View", "", 0F, 180F, 0, settings.getFieldOfView()));
		
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
		
		// draw the sample resolution
		int sampleResolution = (int)((settings.getWidth() > settings.getHeight() ? settings.getWidth() : settings.getHeight()) / 4 * settings.getSampleSize());
		int sampleWidth = sampleResolution * 4;
		int sampleHeight = sampleResolution * 3;
		drawCenteredString(fontRendererObj, "Sampled image: " + sampleWidth + "x" + sampleHeight, leftCol, bottomRow - 24 - 4, 0xa0a0a0);
		
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
			FMLLog.info("Render stereographic panorama");
			
			File renderFile = new File(PanoramaKit.instance.getRenderDir(), "Stereographic.png");
			renderFile = FileNumerator.numberFile(renderFile);
			
			EquirectToStereographic panorama = new EquirectToStereographic(new CubicToEquirect(), settings.getFieldOfView(), settings.getWidth(), settings.getHeight());
			ProjectionConverter converter = new ProjectionConverter(panorama, renderFile);
			
			// create a cubic base image
			int sampleResolution = (int)(((settings.getWidth() > settings.getHeight() ? settings.getWidth() : settings.getHeight()) / 4) * settings.getSampleSize());
			// since the orientation isn't centered in the middle of the image, but instead the side, we rotate it 180 degrees
			// also, since the converter takes a normal equirectangular panorama and makes it into a planet, we need to offset the angle by 90 degrees to make it an edge case
			CubicRenderer renderer = new CubicRenderer(sampleResolution, renderFile, SharedSettings.getOrientation() - 180, settings.getAngle() - 90);
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
			FMLLog.info("Render preview panorama");
			
			File previewFile = PreviewRenderer.getPreviewFile();
			
			int previewSize = 256;
			double fullWidth = settings.getWidth();
			double fullHeight = settings.getHeight();
			int panoramaWidth = fullWidth > fullHeight ? previewSize : (int) (previewSize * fullWidth / fullHeight);
			int panoramaHeight = fullHeight > fullWidth ? previewSize : (int) (previewSize * fullHeight / fullWidth);
			
			EquirectToStereographic panorama = new EquirectToStereographic(new CubicToEquirect(), settings.getFieldOfView(), panoramaWidth, panoramaHeight);;
			ProjectionConverter converter = new ProjectionConverter(panorama, previewFile);
			
			// create a cubic base image
			int sampleResolution = 256;
			CubicRenderer renderer = new CubicRenderer(sampleResolution, previewFile, SharedSettings.getOrientation() - 180, settings.getAngle() - 90);
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
			SharedSettings.setOrientation(value);
		}
		if (id == ANGLE) {
			settings.setAngle(value);
		}
		if(id == FIELD_OF_VIEW){
			settings.setFieldOfView(value);
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
			if (intValue >= 4) {
				settings.setHeight(intValue);
			} else {
				textField.setError(true);
			}
		}
	}
}
