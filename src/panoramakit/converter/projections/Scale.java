/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.projections;

import panoramakit.converter.PositionMapper;
import panoramakit.converter.data.Position;
import panoramakit.converter.samplers.FlatSampler;

/** 
 * Performes a simple type of scaling. Changing the aspect ratio will simply stretch
 * the image into the new shape.
 * 
 * @author dayanto
 */
public class Scale extends PositionMapper
{
	private int width;
	private int height;
	
	public Scale(PositionMapper preProjection, int newWidth, int newHeight)
	{
		super(preProjection, new FlatSampler());
		this.width = newWidth;
		this.height = newHeight;
	}
	
	@Override
	public int getNewWidth(int width, int height)
	{
		return this.width;
	}

	@Override
	public int getNewHeight(int width, int height)
	{
		return this.height;
	}

	@Override
	public boolean testValidProportions()
	{
		return true;
	}

	@Override
	public Position getProjectedPosition(double x, double y)
	{
		x = x * (double) inputWidth / (double) outputWidth;
		y = y * (double) inputHeight / (double) outputHeight;
		
		return new Position(x, x);
	}
}
