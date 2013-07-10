package panoramakit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

/**
 * EntityCamera
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class EntityCamera extends EntityLiving
{

	public EntityCamera(World par1World)
	{
		super(par1World);
		moveToPlayer();
	}

	@Override
	public int getMaxHealth() { return 1; }
	
	public void moveToPlayer()
	{
		EntityLiving player = Minecraft.getMinecraft().thePlayer;
		
		moveTo(player.posX, player.posY, player.posZ);
		rotateTo(player.rotationYaw, player.rotationPitch);
	}
	
	public void moveTo(double xPos, double yPos, double zPos)
	{	
		posX = prevPosX = newPosX = lastTickPosX = xPos;
		posY = prevPosY = newPosY = lastTickPosY = yPos - 1;
		posZ = prevPosZ = newPosZ = lastTickPosZ = zPos;
	}
	
	public void rotateTo(float yaw, float pitch)
	{
		rotationYaw = prevRotationYaw = yaw;
		rotationPitch = prevRotationPitch = pitch;
	}
}

