/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.samplers;

import panoramakit.converter.PixelSampler;
import panoramakit.converter.data.Bounds;
import panoramakit.converter.data.PixelCoordinate;
import panoramakit.converter.data.Position;

/**
 * 
 * @author dayanto
 */
public class CubicSampler extends PixelSampler
{
	public Bounds equator;
	public Bounds northPole;
	public Bounds southPole;
	
	@Override
	public PixelCoordinate[][] getSamplePixels(Position position, int width, int height, int sampleSize)
	{
		Bounds currentBounds;
		
		int yPos = (int) Math.round(position.y);
		
		if (yPos < (height / 3)) {
			currentBounds = northPole;
		} else if (yPos < (2 * height / 3)) {
			currentBounds = equator;
		} else {
			currentBounds = southPole;
		}
		
		PixelCoordinate[][] sampledPixels = new PixelCoordinate[sampleSize][sampleSize];
		
		int xBaseIndex = (int) Math.floor(position.x) - (sampleSize / 2 - 1);
		int yBaseIndex = (int) Math.floor(position.y) - (sampleSize / 2 - 1);
		
		for (int xDelta = 0; xDelta < sampleSize; xDelta++) {
			for (int yDelta = 0; yDelta < sampleSize; yDelta++) {
				int x = currentBounds.getCappedX(xBaseIndex + xDelta);
				int y = currentBounds.getCappedY(yBaseIndex + yDelta);
				sampledPixels[xDelta][yDelta] = new PixelCoordinate(x, y);
			}
		}
		
		return sampledPixels;
	}
	
	@Override
	public void setBounds(int width, int height)
	{
		super.setBounds(width, height);
		
		northPole = new Bounds(width / 4, 2 * width / 4, 0, height / 3);
		equator = new Bounds(0, width, height / 3, 2 * height / 3);
		southPole = new Bounds(width / 4, 2 * width / 4, 2 * height / 3, 3 * height / 3);
	}
}