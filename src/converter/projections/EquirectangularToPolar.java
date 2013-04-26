package converter.projections;

import converter.PositionMapper;
import converter.data.Position;
import converter.samplers.EquirectangularSampler;

/**
 * This projection takes an equirectangular panorama and wraps it in a circle around a center point
 * to create a planetlike effect. This is of course performed in reverse, meaning that the distance
 * from the center is the same as the Y position in the equirectangular panorama and the angle 
 * around the circle translates to the the X position in the equirectangular panorama. The more
 * proper name for this projection is Azimuthal Equidistant Projection.
 */
public class EquirectangularToPolar extends PositionMapper
{
	public static final boolean PLANET = true;
	public static final boolean WELL = false;
	
	public boolean invert;
	
	public EquirectangularToPolar(PositionMapper preProjection, boolean type) throws Exception
	{
		super(preProjection, new EquirectangularSampler());
		invert = type;
	}
	
	public EquirectangularToPolar(boolean type) throws Exception
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
		if(inputWidth % 2 != 0)
		{
			return false;
		}
		if(inputWidth/2 != inputHeight/1)
		{
			return false;
		}
		return true;
	}

	@Override
	public Position getProjectedPosition(double x, double y) 
	{
		x = x - outputWidth / 2 + 0.5;
		y = y - outputHeight / 2 + 0.5;
		
		double angle = Math.atan2(y, x);
		double radius;
		if(Math.abs(x) > Math.abs(y))
		{
			radius = x / Math.cos(angle);
		}
		else
		{
			radius = y / Math.sin(angle);
		}
		
		double relativeX = inputWidth * (angle / (2 * Math.PI));
		
		double xOut = relativeX + (inputWidth / 2);
		double yOut = radius;
		
		if(yOut - 0.5 > inputHeight)
		{
			return null;
			//yOut = inputHeight;		
		}
		
		if(invert)
		{
			yOut = inputHeight - yOut;
			xOut = inputWidth - xOut;
		}
		
		return new Position(xOut - 0.5, yOut - 0.5);
	}
	
}
