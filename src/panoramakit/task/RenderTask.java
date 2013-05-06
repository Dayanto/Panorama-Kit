package panoramakit.task;

import org.lwjgl.opengl.GL11;

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
	
	public void finish()
	{
		// TODO Remove render entity.
	}
	
	@Override
	public boolean isWaiting()
	{
		return false;
	}
	
	public void rotate(float pitch, float yaw, float spin)
	{
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		// TODO Reverse all the translation and apply the custom rotation.
		
		//GL11.glRotatef(entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * par1, 1.0F, 0.0F, 0.0F);
        //GL11.glRotatef(entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * par1 + 180.0F, 0.0F, 1.0F, 0.0F);
        
        //GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        
        
	}
	
}
