/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.converter;

import panoramakit.converter.data.PixelCoordinate;
import panoramakit.converter.data.Position;

/**
 * A position mapper is used to track the position from one projection to another in the form of a
 * floating point position. The tracking occur backwards; it starts at the final position in the
 * output image and tracks the position to sample from in the original image. It is possible to
 * stack multiple projections sequentially. Doing this will simply carry the floating point position
 * through another projection before any pixels are sampled, so that no image quality is lost.
 * 
 * @author dayanto
 */
public abstract class PositionMapper
{
	private PositionMapper preProjection;
	private PixelSampler sampler;
	
	public int inputHeight;
	public int inputWidth;
	public int outputHeight;
	public int outputWidth;
	
	public PositionMapper(PositionMapper preProjection, PixelSampler pixelSampler)
	{
		this.preProjection = preProjection;
		if (preProjection == null) {
			sampler = pixelSampler;
			if (sampler == null) {
				throw new RuntimeException("No pixel sampler available!");
			}
		}
	}
	
	public abstract int getNewWidth(int width, int height);
	
	public abstract int getNewHeight(int width, int height);
	
	public final void setResolution(int width, int height)
	{
		if (preProjection != null) {
			preProjection.setResolution(width, height);
			
			this.outputWidth = getNewWidth(preProjection.outputWidth, preProjection.outputHeight);
			this.outputHeight = getNewHeight(preProjection.outputWidth, preProjection.outputHeight);
			
			inputWidth = preProjection.outputWidth;
			inputHeight = preProjection.outputHeight;
		} else {
			this.outputWidth = getNewWidth(width, height);
			this.outputHeight = getNewHeight(width, height);
			
			inputWidth = width;
			inputHeight = height;
		}
	}
	
	public final int getWidth()
	{
		return outputWidth;
	}
	
	public final int getHeight()
	{
		return outputHeight;
	}
	
	public abstract boolean testValidProportions();
	
	public final boolean hasValidProportions()
	{
		boolean valid = testValidProportions();
		
		if (valid && preProjection != null) {
			valid = preProjection.hasValidProportions();
		}
		
		return valid;
	}
	
	public abstract Position getProjectedPosition(double x, double y);
	
	public final Position getPosition(double x, double y)
	{
		Position pos = getProjectedPosition(x, y);
		
		if (pos == null) {
			return pos;
		}
		
		if (preProjection == null) {
			return pos;
		} else {
			return preProjection.getPosition(pos.x, pos.y);
		}
	}
	
	public final PixelCoordinate[][] getPixelCoordinates(Position position, int width, int height, int sampleSize)
	{
		if (preProjection == null) {
			return sampler.getSamplePixels(position, width, height, sampleSize);
		} else {
			return preProjection.getPixelCoordinates(position, width, height, sampleSize);
		}
	}
	
	public final void setProjectionBounds()
	{
		if (preProjection != null) {
			preProjection.setProjectionBounds();
		} else {
			setBounds();
		}
	}
	
	public final void setBounds()
	{
		sampler.setAllBounds(inputWidth, inputHeight);
	}
	
	public final PixelSampler getPixelSampler()
	{
		if (preProjection == null) {
			return sampler;
		} else {
			return preProjection.getPixelSampler();
		}
	}
	
}
