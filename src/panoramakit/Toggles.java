package panoramakit;

/**
 * Toggles
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class Toggles
{
	private static boolean render;
	
	public static void setRenderState(boolean state)
	{
		render = state;
	}
	
	public static boolean isRendering()
	{
		return render;
	}
}