/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task.threadedtasks;

import net.minecraft.client.settings.GameSettings;
import panoramakit.converter.ProjectionConverter;
import panoramakit.engine.task.ThreadedTask;
import panoramakit.mod.MenuKeyHandler;

public class ProjectionConverterTask extends ThreadedTask
{
	private ProjectionConverter projectionConverter;
	
	public ProjectionConverterTask(ProjectionConverter converter) {
		this.projectionConverter = converter;
	}
	
	@Override
	public void performThreaded() throws Exception
	{
		chat.print("panoramakit.process", GameSettings.getKeyDisplayString(MenuKeyHandler.MENU_KEY.keyCode));
		projectionConverter.setProgressTracker(progressTracker);
		projectionConverter.convert();
		chat.print("panoramakit.saveimage", projectionConverter.getImageName());
		
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
