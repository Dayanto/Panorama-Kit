/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakitcore;

/**
 * Controls the coremod by activating and deactivating features.
 * 
 * @author dayanto
 */
public class CoreStates
{
	private static boolean render = false;
	
	/**
	 * Enables the view correction to make sure panoramas are captured without misalignments.
	 */
	public static void setRenderState(boolean state)
	{
		render = state;
	}
	
	public static boolean isRendering()
	{
		return render;
	}
	
}