package panoramakit.render;

import java.awt.image.BufferedImage;

/**
 * 
 * @author dayanto
 */
public class CubeRenderer extends CompositeImageRenderer {

	// imag settings
	private int resolution;
	private String filePath;

	// TODO add 3 axis orientation

	public CubeRenderer(int resolution, String filePath) {
		super(resolution, resolution);
		this.resolution = resolution;
		this.filePath = filePath;
	}

	@Override
	public void assembleImage() {
		BufferedImage image = new BufferedImage(4 * resolution, 3 * resolution, BufferedImage.TYPE_INT_ARGB);
		
	}

}
