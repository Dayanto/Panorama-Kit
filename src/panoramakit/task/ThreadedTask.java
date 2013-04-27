package panoramakit.task;

/**
 * Threaded tasks are tasks that run in a separate thread in the background. Some
 * tasks run for a long time without any natural pauses, so to avoid freezing up
 * Minecraft, it's better to put them in a separate thread. Threaded tasks are
 * executed automatically after the init method has been called, so there is no
 * need to manually execute them. All threaded code goes into run() implemented
 * from Runnable.
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public abstract class ThreadedTask extends Task implements Runnable
{
	/**
	 * This method is automatically called when it's time to run the threaded task.
	 * It should not be used manually.
	 */
	public void start()
	{
		Thread task = new Thread(this);
		task.start();
	}
}
