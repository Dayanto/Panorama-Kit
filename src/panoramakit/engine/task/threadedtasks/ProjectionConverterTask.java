/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task.threadedtasks;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import panoramakit.converter.ProjectionConverter;
import panoramakit.engine.task.ThreadedTask;
import panoramakit.mod.KeyHandler;

import java.io.File;

public class ProjectionConverterTask extends ThreadedTask
{
	private ProjectionConverter projectionConverter;
	
	public ProjectionConverterTask(ProjectionConverter converter) {
		this.projectionConverter = converter;
	}
	
	@Override
	public void performThreaded() throws Exception
	{
		chat.printTranslated("panoramakit.process", GameSettings.getKeyDisplayString(KeyHandler.MENU_KEY.getKeyCode()));
		projectionConverter.setProgressTracker(progressTracker);
		projectionConverter.convert();

		File imageFile = projectionConverter.getImage();
		ChatComponentText chatcomponenttext = new ChatComponentText(imageFile.getName());
		chatcomponenttext.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, imageFile.getAbsolutePath()));
		chatcomponenttext.getChatStyle().setUnderlined(Boolean.valueOf(true));
		chat.print(new ChatComponentTranslation("panoramakit.saveimage", new Object[] {chatcomponenttext}));

	}
	
	@Override
	public double getProgress()
	{
		return progressTracker.getCurrentProgress();
	}
	
	@Override
	public void stop()
	{
		projectionConverter.stop();
	}
}
