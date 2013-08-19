package converter.projections;

import converter.PositionMapper;
import converter.data.Position;
import converter.samplers.FlatSampler;

/**
 * This projection works much like the Mollweide projection, but halves the distance
 * that is rounded to make things less distorted.
 * 
 * @author dayanto
 */

public class EquirectangularToEckerIV extends PositionMapper {
	public EquirectangularToEckerIV(PositionMapper preProjection) throws Exception {
		super(preProjection, new FlatSampler());
	}

	public EquirectangularToEckerIV() throws Exception {
		this(null);
	}

	@Override
	public int getNewWidth(int width, int height) {
		return width;
	}

	@Override
	public int getNewHeight(int width, int height) {
		return height;
	}

	@Override
	public boolean testValidProportions() {
		if (inputWidth % 2 != 0) {
			return false;
		}
		if (inputWidth / 2 != inputHeight / 1) {
			return false;
		}
		return true;
	}

	@Override
	public Position getProjectedPosition(double x, double y) {
		// adjust from index to pixel position
		x += 0.5;
		y += 0.5;

		double relativeX = x - outputWidth / 2;
		double relativeY = y - outputHeight / 2;

		double scalelessY = (2 * relativeY / outputHeight);
		double scale = 0.5 + Math.sqrt(1 - scalelessY * scalelessY) / 2;
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