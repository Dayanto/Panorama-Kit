package panoramakit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import converter.ProjectionConverter;
import converter.projections.CubicToEquirectangular;
import converter.projections.EquirectangularToPanorama;
import panoramakit.render.CubeRenderer;
import panoramakit.render.ScreenshotRenderer;
import panoramakit.task.ProjectionConverterTask;
import panoramakit.task.RenderTask;
import panoramakit.task.Task;
import panoramakit.task.TaskManager;

/**
 * This class acts as the connection between the tick handlers and the task manager.
 * 
 * Original #C37A13, #5A4C37 New #D28751 #292929 #333333
 * 
 * @author dayanto
 */
public class Dispatcher {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static TaskManager taskManager = new TaskManager();

	public static void runTick() {
		if(mc.currentScreen != null) {
			return;
		}
		
		if (taskManager.hasTasks()) {
			Task currentTask = taskManager.getCurrentTask();

			// TODO make sure stopped tasks don't perform. (?)
			currentTask.perform();

			taskManager.runNextTaskIfCompleted();
			taskManager.clearTaskIfStopped();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
			if (!taskManager.hasTasks()) {
				System.out.println("Render Screenshot");
				taskManager.addTask(new RenderTask(new ScreenshotRenderer(1680, 1050)));
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
			if(!taskManager.hasTasks())
			{
				System.out.println("Render panorama");
				
				// create a cubic base image
				String filePath = "C:/Users/Bertil/Desktop/RenderTest.png";
				taskManager.addTask(new RenderTask(new CubeRenderer(1000, filePath)));
				
				// convert it to a panorama
				try{
					EquirectangularToPanorama panorama = new EquirectangularToPanorama(new CubicToEquirectangular(), 1680, 1050);
					ProjectionConverter converter = new ProjectionConverter(panorama, filePath);
					taskManager.addTask(new ProjectionConverterTask(converter));
				} catch (Exception e) {}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_P)) // TODO Replace with keykind
		{
			if (taskManager.hasTasks()) {
				GuiScreen statusGUI = taskManager.getCurrentTask().getStatusGUI();
				mc.displayGuiScreen(statusGUI);
			} else if (mc.currentScreen != null) {
				// TODO Display menu screen.
			}
		}
	}
}