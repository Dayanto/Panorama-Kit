/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.util;

/** 
 * @author dayanto
 */
public class ProgressTracker
{
	public static final double PROGRESS_UNKNOWN = -1;
	private double progress = PROGRESS_UNKNOWN;
	
	public void setCurrentProgress(double progress)
	{
		this.progress = progress;
	}
	
	public double getCurrentProgress()
	{
		return progress;
	}
}
