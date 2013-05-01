package panoramakit.task;

import panoramakit.task.tick.*;

/**
 * RenderTask
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public abstract class RenderTask extends Task implements ClientTick, PostRenderTick
{
	private int currentPiece = -1;
	
	public abstract void orientView(int frame);
	
	public abstract void renderFrame(int frame);
	
	public void nextPiece()
	{
		currentPiece++;
	}
	
	@Override
	public void clientTick()
	{
		if(!isWaiting()) 
			currentPiece++;
		if(currentPiece >= 0)
			orientView(currentPiece);
	}
	
	@Override
	public void postRenderTick()
	{
		if(currentPiece >= 0)
			renderFrame(currentPiece);
	}

	@Override
	public void init()
	{
		// TODO Add render entity.
	}
	
	public void end()
	{
		// TODO Remove render entity.
	}
	
	@Override
	public boolean isWaiting()
	{
		return false;
	}
	
}
