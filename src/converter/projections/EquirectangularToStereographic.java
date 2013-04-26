package converter.projections;

import converter.PositionMapper;
import converter.data.Position;
import converter.samplers.EquirectangularSampler;

/**
 * The stereographic projection is based on the polar projection, but gives it a more realistic look
 * by treating the equirectangular panorama as a quarter circle surface curving away from the flat 
 * output image plane. The image is being projected through the curved survace onto the plane, so 
 * that the further towards the edges of the image you get, the more stretched it will be. The field 
 * of view decides the maximum angle visible within image vertically and is used to decide the 
 * height of the image output plane in relation to the curving surface. In difference to the polar 
 * projection, no matter how far away from the center you get, it's impossible to go outside the 
 * boundaries of the equirectangular panorama since the absolute edge of it is curving away at 90 
 * degrees, which you would have to go to infinity on the plane to reach.
 */
public class EquirectangularToStereographic extends PositionMapper
{
	public static final boolean PLANET = true;
	public static final boolean WELL = false;
	
	public boolean invert;
	public double scale;
	
	public boolean customResolution;
	public int newWidth;
	public int newHeight;
	
	public EquirectangularToStereographic(PositionMapper preProjection, boolean type, double fieldOfView, int newWidth, int newHeight) throws Exception
	{
		super(preProjection, new EquirectangularSampler());
		invert = type;
		scale = Math.tan(fieldOfView / 2 * (Math.PI / 180));
		
		customResolution = true;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
	}
	
	public EquirectangularToStereographic(PositionMapper preProjection, boolean type, double fieldOfView) throws Exception
	{
		this(preProjection, type, fieldOfView, 0, 0);
		customResolution = false;
	}
	
	public EquirectangularToStereographic(boolean type, double fieldOfView, int newWidth, int newHeight) throws Exception
	{
		this(null, type, fieldOfView, newWidth, newHeight);
	}
	
	public EquirectangularToStereographic(boolean type, double fieldOfView) throws Exception
	{
		this(null, type, fieldOfView, 0, 0);
		customResolution = false;
	}

	@Override
	public int getNewWidth(int width, int height) 
	{
		if(customResolution)
		{
			return newWidth;
		}
		else
		{
			return width;
		}
	}

	@Override
	public int getNewHeight(int width, int height) 
	{
		if(customResolution)
		{
			return newHeight;
		}
		else
		{
			return height;
		}
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
		
		double scalelessRadius = radius / outputHeight;
		double width = scale * scalelessRadius;
		
		double angle2 = Math.atan(width);
		double radius2 = (angle2 * 2 * inputHeight / Math.PI);
		
		double relativeX = inputWidth * (angle / (2 * Math.PI));
		
		double xOut = -relativeX + (inputWidth / 2);
		double yOut = radius2;
		
		if(yOut > inputHeight)
		{
			yOut = inputHeight;		
		}
		
		if(invert)
		{
			yOut = inputHeight - yOut;
			xOut = inputWidth - xOut;
		}
		
		return new Position(xOut - 0.5, yOut - 0.5);
	}
	
}
