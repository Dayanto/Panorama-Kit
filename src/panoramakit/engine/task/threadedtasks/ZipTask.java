/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task.threadedtasks;

import java.io.File;
import panoramakit.engine.task.ThreadedTask;
import panoramakit.engine.tools.Zipper;

/** 
 * @author dayanto
 */
public class ZipTask extends ThreadedTask
{
	private File folder;
	private File zipFile;
	
	public ZipTask(File folderToZip, File zipDest)
	{
		folder = folderToZip;
		zipFile = zipDest;
	}
	
	@Override
	public void performThreaded() throws Exception
	{
		chat.print("Zipping...");
		Zipper.zipFolder(folder, zipFile);
		chat.print("Saved as " + zipFile.getName());
	}
}