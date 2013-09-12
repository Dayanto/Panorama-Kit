/*
 ** 2012 March 7
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package panoramakit.engine.util;

import net.minecraft.util.MouseHelper;

/**
 * Mouse helper that may refuse mouse delta updates.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class LockableMouseHelper extends MouseHelper
{
	
	private boolean locked;
	private boolean grab = true;
	
	@Override
	public void mouseXYChange()
	{
		if (!locked) {
			super.mouseXYChange();
		} else {
			deltaX = 0;
			deltaY = 0;
		}
	}
	
	public boolean isLocked()
	{
		return locked;
	}
	
	public void setLocked(boolean locked)
	{
		if (grab) {
			if (this.locked && !locked) {
				grabMouseCursor();
			} else if (!this.locked && locked) {
				ungrabMouseCursor();
			}
		}
		this.locked = locked;
	}
	
	/**
	 * @return the ungrab
	 */
	public boolean isGrabbing()
	{
		return grab;
	}
	
	/**
	 * @param ungrab
	 *            the ungrab to set
	 */
	public void setGrabbing(boolean ungrab)
	{
		this.grab = ungrab;
	}
}
