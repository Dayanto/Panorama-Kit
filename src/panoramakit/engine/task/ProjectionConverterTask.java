/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task;

import panoramakit.converter.ProjectionConverter;
import panoramakit.engine.render.ImageLink;

public class ProjectionConverterTask extends ThreadedTask
{
	private ProjectionConverter projectionConverter;
	
	public ProjectionConverterTask(ProjectionConverter converter, ImageLink imagetransferer)
	{
		this(converter);
		projectionConverter.setInputImage(imagetransferer.getImage());
	}
	
	public ProjectionConverterTask(ProjectionConverter converter) {
		this.projectionConverter = converter;
	}
	
	@Override
	public void performThreaded() throws Exception
	{
		printChat("panoramakit.process");
		projectionConverter.setProgressTracker(progressTracker);
		projectionConverter.convert();
	}
	
	@Override
	public double getProgress()
	{
		return progressTracker.getCurrentProgress();
	}
	
	
	@Override
	public void init()
	{}
	
	@Override
	public void finish()
	{}
	
	@Override
	public void perform()
	{}
	
	@Override
	public void stop()
	{
		projectionConverter.stop();
	}
}
