/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter.projections;

import panoramakit.converter.PositionMapper;
import panoramakit.converter.data.Position;
import panoramakit.converter.samplers.FlatSampler;

/**
 * This projeciton is based on a certain type of world map where any one part of the map has the same area as it has in reality.
 * 
 * @author dayanto
 */
public class EquirectToMollweide extends PositionMapper
{
	public EquirectToMollweide(PositionMapper preProjection)
	{
		super(preProjection, new FlatSampler());
	}
	
	public EquirectToMollweide()
	{
		this(null);
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
		if (inputWidth % 2 != 0) {
			return false;
		}
		if (inputWidth / 2 != inputHeight / 1) {
			return false;
		}
		return true;
	}
	
	@Override
	public Position getProjectedPosition(double x, double y)
	{
		// adjust from index to pixel position
		x += 0.5;
		y += 0.5;
		
		double relativeX = x - outputWidth / 2;
		double relativeY = y - outputHeight / 2;
		
		double scalelessY = (2 * relativeY / outputHeight);
		double scale = Math.sqrt(1 - scalelessY * scalelessY);
		x = relativeX / scale + outputWidth / 2;
		
		// adjust from position to pixel index
		x -= 0.5;
		y -= 0.5;
		
		if (x < 0 || x > outputWidth - 1) {
			return null;
		}
		
		return new Position(x, y);
	}
	
}