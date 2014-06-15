/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter.samplers;

import panoramakit.converter.PixelSampler;
import panoramakit.converter.data.PixelCoordinate;
import panoramakit.converter.data.Position;

/**
 * 
 * @author dayanto
 */
public class FlatSampler extends PixelSampler
{
	
	@Override
	public PixelCoordinate[][] getSamplePixels(Position position, int width, int height, int sampleSize)
	{
		int xBase = (int) Math.floor(position.x) - (sampleSize / 2 - 1);
		int yBase = (int) Math.floor(position.y) - (sampleSize / 2 - 1);
		
		PixelCoordinate[][] sampledPixels = new PixelCoordinate[sampleSize][sampleSize];
		
		for (int xDelta = 0; xDelta < sampleSize; xDelta++) {
			for (int yDelta = 0; yDelta < sampleSize; yDelta++) {
				int x = edge.getCappedX(xBase + xDelta);
				int y = edge.getCappedY(yBase + yDelta);
				sampledPixels[xDelta][yDelta] = new PixelCoordinate(x, y);
			}
		}
		
		return sampledPixels;
	}
}
