/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.task;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.FMLLog;
import panoramakit.engine.util.ProgressTracker;

/**
 * Threaded tasks are tasks that run in a separate thread in the background. Some tasks run for a
 * long time without any natural pauses, so to avoid freezing up Minecraft, it's better to put them
 * in a separate thread. Threaded tasks are executed automatically after the init method has been
 * called, so there is no need to manually execute them. All threaded code goes into performThreaded
 * which gets executed once you run start(). Threaded tasks can also utilize the perform() which 
 * triggers every frame just like with normal tasks.
 * 
 * @author dayanto
 */
public abstract class ThreadedTask extends Task implements Runnable
{
	/**
	 * Used to keep track of the current progress. It's useful when the task executes an external
	 * process which can't report it's progress directly.
	 */
	public final ProgressTracker progressTracker = new ProgressTracker();
	
	/**
	 * This method is automatically called when it's time to run the threaded task. It is never
	 * used manually.
	 */
	void start()
	{
		Thread task = new Thread(this);
		task.start();
	}
	
	@Override
	public final void run()
	{
		try {
			performThreaded();
		} 
		catch (InterruptedException ex) {
			FMLLog.warning("Threaded task stopped.");
			setStopped();
			return;
		}
		catch (Exception ex) {
			FMLLog.log(Level.ERROR, ex, "Threaded task has failed");
			chat.print("Current task failed: " + ex);
			TaskManager.instance.halt();
			setStopped();
			return;
		}
		
		setCompleted();
	}
	
	/**
	 * This is where all the threaded code goes. It gets executed once and is then left to handle itself.
	 * Once it has finished, it will automatically be marked as completed. 
	 */
	public abstract void performThreaded() throws Exception;

	/**
	 * Threaded tasks rarely use this method, so it's made optional through this empty implementation.
	 */
	@Override
	public final void perform()
	{}
}
