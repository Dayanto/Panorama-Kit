/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.converter.projections;

import panoramakit.converter.PositionMapper;
import panoramakit.converter.data.Position;
import panoramakit.converter.samplers.CubicSampler;

/**
 * This projection takes any point in an equirectangular projection and returns the position that point would match in the cubic projection.
 * The cubic projection uses two different positioning systems. The first, what I call the "equator" positioning uses mostly straight lines
 * where moving along the x or y axis in the equrectangular projection mostly follows the same axis in the cubic. In the "polar" positioning
 * system instead, the x and y positions are determined by the distance from a center point in one of two specific regions of the image, as
 * well as the rotation around that point.
 * 
 * Here's an image that can be used for reference: http://primegraphics.co.uk/images/cubic.jpg
 * 
 * @author dayanto
 */
public class CubicToEquirect extends PositionMapper
{
	int resolution = -1;
	
	public CubicToEquirect(PositionMapper preProjection)
	{
		super(preProjection, new CubicSampler());
	}
	
	public CubicToEquirect()
	{
		this(null);
	}
	
	public CubicToEquirect(PositionMapper preProjection, int newResolution)
	{
		this(preProjection);
		resolution = newResolution;
	}
	
	public CubicToEquirect(int newResolution)
	{
		this(null, newResolution);
	}
	
	@Override
	public Position getProjectedPosition(double x, double y)
	{
		// adjust from index to the pixel's center point position
		x += 0.5;
		y += 0.5;
		
		// the height and width of any of the faces on the cube in the outputImage.
		double inPieceSize = inputWidth / 4;
		double outPieceSize = outputWidth / 4; 
		
		double radius = inPieceSize / 2;
		
		// offset the image to place the center of the cross in the center of the image
		x -= outPieceSize / 2;
		// make sure it stays within the original range
		x = (x + inputWidth) % inputWidth;
		
		int cardinalDirection = (int) Math.floor(x / outPieceSize) % 4;
		
		double xCenteredLocal = (x % outPieceSize) - outPieceSize / 2;
		double yCentered = y - outPieceSize;
		
		// in equirectangular panoramas, the position directly corresponds to the current angle. (longitude & latitude, yaw & pitch, etc.)
		double horizontalAngle = xCenteredLocal * ((2 * Math.PI) / outputWidth);
		double verticalAngle = yCentered * ((2 * Math.PI) / outputWidth);
		
		// the trigonometric calculations used are pre-calculated to save some time
		double sinHorizontal = Math.sin(horizontalAngle);
		double cosHorizontal = Math.cos(horizontalAngle);
		double tanHorizontal = sinHorizontal / cosHorizontal; // Math.tan(horizontalAngle); This is purely for optimization.
		double tanVertical = Math.tan(verticalAngle);
		
		// the calculations are based on the idea of a sphere inside of a cube and following the straight lines
		// from the center through the sphere and to the matching point on the outside cube.
		
		// Calculate the relative position from the equator.
		double x1 = tanHorizontal * radius;
		double y1 = tanVertical * radius / cosHorizontal;
		
		// Apply offsets to get the absolute position.
		x1 = (x1 + inPieceSize / 2 + (inPieceSize * cardinalDirection)) % inputWidth;
		y1 = y1 + inPieceSize * 1.5;
		
		// adjust from position to pixel index
		x1 -= 0.5;
		y1 -= 0.5;
		
		Position equator = new Position(x1, y1);
		
		if (inPieceSize <= Math.round(equator.y) && Math.round(equator.y) < inPieceSize * 2) {
			return equator;
		} else {
			// Which pole we're at currently. Represents a value of 1 or -1 where -1 is the pole on top and 1 is the one on the bottom. Since
			// 1 doesn't change anything when multiplied, all calculations will be based off of the bottom pole and flipped for the top.
			int hemisphere = (int) (verticalAngle / Math.abs(verticalAngle));
			
			// Calculate the relative position from the pole center. The rotation is counter clockwise and starts pointing straight left.
			double distanceFromCenter = radius / Math.abs(tanVertical);
			double x2 = -cosHorizontal * distanceFromCenter;
			double y2 = -sinHorizontal * distanceFromCenter * hemisphere;
			
			Position temp = rotate(x2, y2, cardinalDirection * hemisphere);
			x2 = temp.x;
			y2 = temp.y;
			
			// Apply offsets to get the actual position in pixels.
			x2 = x2 + inPieceSize * 1.5;
			y2 = y2 + inPieceSize * 1.5 + (hemisphere * inPieceSize);
			
			// adjust from position to pixel index
			x2 -= 0.5;
			y2 -= 0.5;
			
			Position polar = new Position(x2, y2);
			
			return polar;
		}
	}
	
	/**
	 * Rotates the position 90 degrees to the right around the origin. This is repeated the number of times given.
	 */
	public static Position rotate(double x, double y, int rotations)
	{
		rotations = (rotations + 4) % 4;
		double temp;
		
		for (int i = 0; i < rotations; i++) {
			temp = y;
			y = x;
			x = -temp;
		}
		
		return new Position(x, y);
	}
	
	@Override
	public boolean testValidProportions()
	{
		// The image has to be able to be divided into a grid of 4 by 3 pieces
		// and requires an aspect ratio of 4:3.
		
		if (inputWidth % 4 != 0) {
			return false;
		}
		if (inputHeight % 3 != 0) {
			return false;
		}
		if (inputWidth / 4 != inputHeight / 3) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int getNewWidth(int originalWidth, int originalHeight)
	{
		if(resolution == -1)
			return originalWidth;
		else
			return resolution * 4;
	}
	
	@Override
	public int getNewHeight(int originalWidth, int originalHeight)
	{
		if(resolution == -1)
			return 2 * originalHeight / 3;
		else
			return resolution * 2;
	}
}
