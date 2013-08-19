package panoramakitcore;

/**
 * ControlPanel
 * 
 * @author dayanto
 */
public class Activator {
	private static boolean render;

	public static void setRenderState(boolean state) {
		render = state;
	}

	public static boolean isRendering() {
		return render;
	}

}