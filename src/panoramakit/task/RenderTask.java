package panoramakit.task;

import panoramakit.task.tick.*;

/**
 * RenderTask
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public abstract class RenderTask extends Task implements ClientTick, RenderTick
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
		orientView(currentFrame);
		
	}
	
	@Override
	public void renderTick()
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
