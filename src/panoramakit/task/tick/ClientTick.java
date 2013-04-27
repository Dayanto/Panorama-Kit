package panoramakit.task.tick;

/**
 * ClientTick
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public interface ClientTick
{
	/**
	 * Triggers before a new frame on the client. 
	 */
	public abstract void clientTick();
}
