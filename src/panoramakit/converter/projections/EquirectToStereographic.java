/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter.projections;

import panoramakit.converter.PositionMapper;
import panoramakit.converter.data.Position;
import panoramakit.converter.samplers.FlatSampler;

/**
 * The stereographic projection is based on the polar projection, but gives it a more realistic look by treating the equirectangular
 * panorama as surface curving in a half circle away from the flat output image plane. The image is being projected through the curved
 * survace onto the plane, so that the further towards the edges of the image you get, the more stretched it will be. The field of view
 * decides the maximum angle visible within image vertically and is used to decide the height of the image output plane in relation to the
 * curving surface. In difference to the polar projection, no matter how far away from the center you get, it's impossible to go outside the
 * boundaries of the equirectangular panorama since the absolute edge of it is curving away at 90 degrees, which you would have to go to
 * infinity on the plane to reach.
 * 
 * @author dayanto
 */

public class EquirectToStereographic extends PositionMapper
{
	public double relativeViewPlaneSize;
	
	public boolean customResolution;
	public int newWidth;
	public int newHeight;
	
	public EquirectToStereographic(PositionMapper preProjection, double fieldOfView, int newWidth, int newHeight)
	{
		super(preProjection, new FlatSampler());
		relativeViewPlaneSize = Math.tan(fieldOfView / 2 * (Math.PI / 180));
		
		customResolution = true;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
	}
	
	public EquirectToStereographic(double fieldOfView, int newWidth, int newHeight)
	{
		this(null, fieldOfView, newWidth, newHeight);
	}
	
	public EquirectToStereographic(PositionMapper preProjection, double fieldOfView)
	{
		this(preProjection, fieldOfView, 0, 0);
		customResolution = false;
	}
	
	public EquirectToStereographic(double fieldOfView)
	{
		this(null, fieldOfView);
	}
	
	@Override
	public int getNewWidth(int width, int height)
	{
		if (customResolution) {
			return newWidth;
		} else {
			return width;
		}
	}
	
	@Override
	public int getNewHeight(int width, int height)
	{
		if (customResolution) {
			return newHeight;
		} else {
			return height;
		}
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
	
	/**
	 * 
	 */
	@Override
	public Position getProjectedPosition(double x, double y)
	{
		// adjust from index to pixel position
		x += 0.5;
		y += 0.5;
		
		x = x - outputWidth / 2;
		y = y - outputHeight / 2;
		
		double polarAngle = Math.atan2(y, x);
		
		// calculate the distance to the center of the image
		double distanceToCenter; // 
		if (Math.abs(x) > Math.abs(y)) {
			distanceToCenter = x / Math.cos(polarAngle);
		} else {
			distanceToCenter = y / Math.sin(polarAngle);
		}
		
		// the distance of the current pixel to the center of the image expressed in units of half the image height
		double relativeDistanceToCenter = distanceToCenter / (outputHeight / 2);
		
		/* The view plane (whose relative size was pre-calculated) represents an infinite plane tangent to the equirectangular
		 * panorama (that we're sampling from) when it's bent along the y-axis as a quarter-circle and then rotated around a 
		 * point (think of the polar coordinates filter in Photoshop), forming a dome. */
		
		/* The relative size of the view plane is calculated based off of the field of view and is defined as a multiple of it's 
		 * vertical size when the field of view is 90 degrees. */
		
		double planarRadius = relativeViewPlaneSize * relativeDistanceToCenter;
		
		// If we imagine the equirectangular panorama as a dome with an infinite plane on top, this is the vertical angle that
		// you would get if a position on the plane was projected through the dome towards it's center point.
		double domeVerticalAngle = Math.atan(planarRadius);
		
		// Map the angle of the view to the y coordinate of the equirectangular panorama. The equirectangular panorama normally
		// represents 180 degrees vertically, but in order to cause the planet-like effect we shrink it to 90 degrees. However,
		// since we're mapping in reverse, we stretch the sampled position to the double instead.
		double equirectangularYPos = (2 * domeVerticalAngle * (inputHeight / Math.PI));
		
		double relativeX = inputWidth * (-polarAngle / (2 * Math.PI)); // it's minus because the angle counts counter-clockwise
		
		double xOut = relativeX + (inputWidth / 2);
		double yOut = equirectangularYPos;
		
		if (yOut > inputHeight) {
			yOut = inputHeight;
		}
		
		yOut = inputHeight - yOut;
		xOut = inputWidth - xOut;
		
		// rotate it 90 degrees to straighten it up (not sure why this is necessary, but it's a solution)
		xOut = ((xOut - inputWidth / 4) % inputWidth + inputWidth) % inputWidth;
		
		// adjust from position to pixel index
		x -= 0.5;
		y -= 0.5;
		
		return new Position(xOut, yOut);
	}
	
}
