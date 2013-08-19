package converter.projections;

import converter.PositionMapper;
import converter.data.Position;
import converter.samplers.FlatSampler;

/**
 * 
 * @author dayanto
 */
public class EquirectangularToPanorama extends PositionMapper {
	public boolean customResolution;
	public int newWidth;
	public int newHeight;

	public EquirectangularToPanorama(PositionMapper preProjection, int newWidth, int newHeight) throws Exception {
		super(preProjection, new FlatSampler());

		customResolution = true;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
	}

	public EquirectangularToPanorama(PositionMapper preProjection) throws Exception {
		this(preProjection, 0, 0);
		customResolution = false;
	}

	public EquirectangularToPanorama(int newWidth, int newHeight) throws Exception {
		this(null, newWidth, newHeight);
	}

	public EquirectangularToPanorama() throws Exception {
		this(null, 0, 0);
		customResolution = false;
	}

	@Override
	public int getNewWidth(int width, int height) {
		if (customResolution) {
			return newWidth;
		} else {
			return width;
		}
	}
	
	@Override
	public int getNewHeight(int width, int height) {
		if (customResolution) {
			return newHeight;
		} else {
			return height;
		}
	}

	@Override
	public boolean testValidProportions() {
		return true;
	}

	@Override
	public Position getProjectedPosition(double x, double y) {
		// adjust from index to pixel position
		x += 0.5;
		y += 0.5;

		// calculate y
		double scale = (double) inputWidth / (double) outputWidth;
		double centeredScaledY = (y - outputHeight / 2) * scale;

		double radius = ((double) inputHeight / Math.PI);

		double angle = Math.atan(centeredScaledY / radius);

		double relativeY = inputHeight * (angle / Math.PI);

		y = relativeY + inputHeight / 2;

		// calculate x
		x = x * scale;

		// adjust from position to pixel index
		x -= 0.5;
		y -= 0.5;

		return new Position(x, y);
	}

}
