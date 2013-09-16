/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter.projections;

import panoramakit.converter.PositionMapper;
import panoramakit.converter.data.Position;
import panoramakit.converter.samplers.FlatSampler;

/**
 * This projection takes an equirectangular panorama and wraps it in a circle around a center point to create a planetlike effect. As with
 * all projections, it's performed in reverse, meaning that the distance from the center is the same as the Y position in the
 * equirectangular panorama and the angle around the circle translates to the the X position in the equirectangular panorama.
 * 
 * The more proper name for this projection is Azimuthal Equidistant Projection. You can also make one by applying the polar coordinates
 * filter on an equirectangular panorama in Photoshop.
 * 
 * @author dayanto
 */
public class EquirectToPolar extends PositionMapper
{
	public static final boolean PLANET = true;
	public static final boolean WELL = false;
	
	public boolean invert;
	
	public EquirectToPolar(PositionMapper preProjection, boolean type)
	{
		super(preProjection, new FlatSampler());
		invert = type;
	}
	
	public EquirectToPolar(boolean type)
	{
		this(null, type);
	}
	
	@Override
	public int getNewWidth(int width, int height)
	{
		return width;
	}
	
	@Override
	public int getNewHeight(int width, int height)
	{
		return width;
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
		
		x = x - outputWidth / 2;
		y = y - outputHeight / 2;
		
		double angle = Math.atan2(y, x);
		double radius;
		if (Math.abs(x) > Math.abs(y)) {
			radius = x / Math.cos(angle);
		} else {
			radius = y / Math.sin(angle);
		}
		
		double relativeX = inputWidth * (angle / (2 * Math.PI));
		
		double xOut = relativeX + (inputWidth / 2);
		double yOut = radius;
		
		if (yOut - 0.5 > inputHeight) {
			return null;
			// yOut = inputHeight;
		}
		
		if (invert) {
			yOut = inputHeight - yOut;
			xOut = inputWidth - xOut;
		}
		
		// adjust from position to pixel index
		x -= 0.5;
		y -= 0.5;
		
		return new Position(xOut, yOut);
	}
	
}
