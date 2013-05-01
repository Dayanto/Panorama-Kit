package panoramakit.task.tick;

/**
 * PostRenderTick
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public interface PostRenderTick
{
	/**
	 * This tick triggers after all the rendering has been completed.
	 */
	public abstract void postRenderTick();
}
