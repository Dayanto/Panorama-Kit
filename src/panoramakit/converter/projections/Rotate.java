/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.projections;

import panoramakit.converter.PositionMapper;
import panoramakit.converter.data.Position;
import panoramakit.converter.samplers.FlatSampler;

/** 
 * @author dayanto
 */
public class Rotate extends PositionMapper
{
	private int rotations;
	
	public Rotate(PositionMapper preProjection, int clockwiseRotations)
	{
		super(preProjection, new FlatSampler());
		rotations = clockwiseRotations;
	}
	
	@Override
	public int getNewWidth(int width, int height)
	{
		return width;
	}

	@Override
	public int getNewHeight(int width, int height)
	{
		return height;
	}

	@Override
	public boolean testValidProportions()
	{
		return true;
	}

	@Override
	public Position getProjectedPosition(double x, double y)
	{
		// adjust from index to pixel position
		x += 0.5;
		y += 0.5;
		
		double centeredX = x - outputWidth / 2 - 0.5;
		double centeredY = y - outputHeight / 2 - 0.5;
		
		x = rotate(centeredX, centeredY);
		y = rotate(centeredY, centeredX);
		
		x += outputWidth / 2 + 0.5;
		y += outputHeight / 2 + 0.5;
		
		return new Position(x, x);
	}
	
	public double rotate(double a, double b)
	{
		double tempB;
		for(int i = 0; i < (rotations % 4); i++) {
			tempB = b;
			b = a;
			a = -tempB;
		}
		return a;
	}
	
}
