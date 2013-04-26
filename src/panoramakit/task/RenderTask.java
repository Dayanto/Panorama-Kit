package panoramakit.task;

public abstract class RenderTask implements Task
{
	private int currentFrame = 0;
	
	public abstract void orientView(int frame);
	
	public abstract void renderFrame(int frame);

	public void nextImage()
	{
		currentFrame++;
	}
	
	@Override
	public void clientTick()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void preRenderTick()
	{
		orientView(currentFrame);
	}

	@Override
	public void postRenderTick()
	{
		renderFrame(currentFrame);
	}

	@Override
	public void displayGUI()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		
	}
}
