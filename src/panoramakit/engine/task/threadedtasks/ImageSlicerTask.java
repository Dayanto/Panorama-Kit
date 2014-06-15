/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.task.threadedtasks;

import java.io.File;
import panoramakit.engine.task.ThreadedTask;
import panoramakit.engine.tools.ImageSlicer;

/** 
 * Slices an image into a grid of tiles that are saved as individual files.
 * 
 * @author dayanto
 */
public class ImageSlicerTask extends ThreadedTask
{	
	private ImageSlicer imageSlicer;
	
	public ImageSlicerTask(File imageFile, File outputFolder, int xTiles, int yTiles)
	{
		imageSlicer = new ImageSlicer(imageFile, outputFolder, xTiles, yTiles);
	}

	@Override
	public void performThreaded() throws Exception
	{
		imageSlicer.slice();
	}
}