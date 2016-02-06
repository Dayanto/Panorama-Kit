/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.gui.screens;

import panoramakit.engine.task.Task;
import panoramakit.engine.task.TaskManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/** 
 * @author dayanto
 */
public class GuiScreenProgress extends GuiScreen
{
	protected String screenTitle = "Current Progress";
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, 126, "Stop All"));
		buttonList.add(new GuiButton(1, width / 2 - 100, 150, "Back"));
	}
	
	/**
	 * Fired when a control is clicked. This is the equivalent of
	 * ActionListener.actionPerformed(ActionEvent e).
	 */
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 0){
			TaskManager.instance.halt();
		}
		if(button.id == 1){
			mc.displayGuiScreen(null);
		}
	}
	
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int x, int y, float z)
	{
		Task currentTask = TaskManager.instance.getCurrentTask();
		if(currentTask == null) {
			mc.displayGuiScreen(null);
			return;
		}
		int xs = width / 2;
		
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, screenTitle, xs - 10, 32, 0xffffff);
		
		int ys = 60;
		int line = 12;
		
		double progress = currentTask.getProgress();
		String percent = progress == -1 ? "Unknown" : (Math.round(100 * progress) + "%");
		int tasksRemaining = TaskManager.instance.tasksInQueue();
		
		drawString(fontRendererObj, "Current progress: " + percent, xs - 80, ys += line, 0xa0a0a0);
		drawString(fontRendererObj, "Tasks in queue: " + tasksRemaining, xs - 80, ys += line, 0xa0a0a0);

		
		super.drawScreen(x, y, z);
	}
}
