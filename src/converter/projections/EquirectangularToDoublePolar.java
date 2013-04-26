package converter.projections;

import converter.PositionMapper;
import converter.data.Position;
import converter.samplers.EquirectangularSampler;

public class EquirectangularToDoublePolar extends PositionMapper
{	
	public EquirectangularToDoublePolar(PositionMapper preProjection) throws Exception
	{
		super(preProjection, new EquirectangularSampler());
	}
	
	public EquirectangularToDoublePolar() throws Exception
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
		boolean invert = false;
		
		if(x >= outputWidth / 2)
		{
			x -= outputWidth / 2;
			invert = true;
		}
		
		x = x - outputHeight / 2 + 0.5;
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
		
		if(yOut - 0.5 > inputHeight / 2)
		{
			return null;	
			//yOut = inputHeight / 2;
		}
		yOut = inputHeight - yOut;
		if(invert)
		{
			yOut = inputHeight - yOut;
			xOut = inputWidth - xOut;
			xOut += inputWidth / 2;
		}
		
		return new Position(xOut - 0.5, yOut - 0.5);
	}
	
}
