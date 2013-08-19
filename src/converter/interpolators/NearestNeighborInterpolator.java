package converter.interpolators;

import converter.Interpolator;
import converter.data.ColorData;

/**
 * NearestNeighborInterpolator
 * 
 * @author dayanto
 */
public class NearestNeighborInterpolator extends Interpolator {
	public NearestNeighborInterpolator() {
		super(2); // Sample size of 2x2 pixels.
	}

	@Override
	public int getPixelValue(double xFraction, double yFraction, ColorData[][] pixels) {
		ColorData pixel = pixels[(int) Math.round(xFraction)][(int) Math.round(yFraction)];

		return pixel.getIntValue();
	}
}