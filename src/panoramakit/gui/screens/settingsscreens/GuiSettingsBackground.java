/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.screens.settingsscreens;

import java.io.File;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.client.gui.GuiButton;
import panoramakit.engine.render.renderers.BackgroundRenderer;
import panoramakit.engine.task.TaskManager;
import panoramakit.engine.task.tasks.RenderTask;
import panoramakit.engine.task.threadedtasks.ZipTask;
import panoramakit.gui.menuitems.GuiCustomButton;
import panoramakit.gui.menuitems.GuiCustomSliderOrientation;
import panoramakit.gui.menuitems.GuiCustomTextField;
import panoramakit.gui.screens.GuiRenderNotice;
import panoramakit.gui.screens.menuscreens.GuiMenuMain;
import panoramakit.gui.settings.BackgroundSettings;
import panoramakit.gui.settings.SharedSettings;
import panoramakit.mod.PanoramaKit;
import panoramakit.gui.util.FileNumerator;

/**
 * @author dayanto
 */
public class GuiSettingsBackground extends GuiScreenSettings
{
	private static String screenTitle = "Main Menu Background";
	private static String screenLabel = "Make a Background";
	
	private static final int RESOLUTION = 0;
	private static final int ORIENTATION = 1;
	private static final int MODE = 2;
	
	private static final int BACK = 3;
	private static final int CREATE = 4;
	
	private BackgroundSettings settings;
	
	public GuiSettingsBackground()
	{
		super(screenLabel);
		settings = new BackgroundSettings();
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
		final int contentStart = height / 2 - 60;
		final int bottomRow = height / 2 + 60;
		
		final int rowHeight = 24;
		
		// moving marker for stacking buttons
		int currentY = contentStart + 12;
		
		// resolution
		GuiCustomTextField fieldWidth = new GuiCustomTextField(fontRendererObj, RESOLUTION, width / 2 - 10, currentY, 80, 20, true);
		fieldWidth.setText(String.valueOf(settings.getResolution()));
		textFieldList.add(fieldWidth);
		
		// orientation
		buttonList.add(new GuiCustomSliderOrientation(ORIENTATION, width / 2 - 75, currentY += rowHeight, this, "Orientation", "What you can see is where it will start rotating.", -180F, 180F, 0, SharedSettings.getOrientation()));
		
		// mode
		buttonList.add(new GuiCustomButton(MODE, width / 2 - 75, currentY += rowHeight, 150, 20, null, "The option \"Add To The Game\" will be added after Minecraft 1.7", BackgroundSettings.modes, settings.getMode()));
		
		// bottom row buttons
		buttonList.add(new GuiButton(BACK, leftCol - 75, bottomRow, 150, 20, "Back"));
		buttonList.add(new GuiButton(CREATE, rightCol - 75, bottomRow, 150, 20, "Create"));
		
		// straighten the camera so we can see where the background rotation would begin
		updateView(SharedSettings.getOrientation());
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		// height offsets
		int contentStart = height / 2 - 60;
		int bottomRow = height / 2 + 60;
		
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, screenTitle, width / 2, contentStart - 24, 0xffffff);
		
		// draw the strings related to the height and width
		drawString(fontRendererObj, "Resolution", width / 2 - 75 + 5, contentStart + 12 + 6, 0xa0a0a0);
		
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
	public void buttonPressed(GuiButton button, int id, int currentOption)
	{
		if (id == MODE) {
			settings.setMode(currentOption);
		}
		
		if (id == BACK) 
		{
			mc.displayGuiScreen(new GuiMenuMain());
		}
		
		if (id == CREATE) 
		{
			if(BackgroundSettings.modes[currentOption] == BackgroundSettings.saveBackground) {
				FMLLog.info("Create new background");
				
				File renderDir = new File(PanoramaKit.instance.getTempRenderDir(), "assets/minecraft/textures/gui/title/background/");
					
				// create the background images
				BackgroundRenderer renderer = new BackgroundRenderer(settings.getResolution(), renderDir, SharedSettings.getOrientation());
				TaskManager.instance.addTask(new RenderTask(renderer));
				
				// put the images in a resource pack
				File bgDir = new File(PanoramaKit.instance.getTempRenderDir(), "assets/");
				File zipFile = new File(PanoramaKit.instance.getRenderDir(), "backgrounds/CustomBackground.zip");
				TaskManager.instance.addTask(new ZipTask(bgDir, FileNumerator.increment(zipFile)));
				
				mc.displayGuiScreen(new GuiRenderNotice());
			}
			if(BackgroundSettings.modes[currentOption] == BackgroundSettings.replaceBackground) {
				// L.info("Replace current background");
				// TODO replace current background
			}
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
			updateView(value);
		}
	}
	
	public void updateView(float orientation)
	{
		mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw = orientation;
		mc.thePlayer.rotationPitch = mc.thePlayer.prevRotationPitch = 0;
	}
	
	/**
	 * Called whenever a textfield has been updated
	 */
	@Override
	public void textFieldUpdated(GuiCustomTextField textField, int id, String value)
	{
		int intValue = -1;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}
		
		if (id == RESOLUTION) {
			if(intValue > 0) {
				settings.setResolution(intValue);
			} else {
				textField.setError(true);
			}
		}
	}
}