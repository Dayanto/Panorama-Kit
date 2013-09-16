/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.util;

import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class ChatPrinter
{
	private boolean silent = false;
	
	/**
	 * Attempts to send a message to the chat, but fails if this task has been silenced.
	 */
	public void print(String msg, Object... params) {
		if(!silent)
		{
			PanoramaKit.instance.printChat(msg, params);
		}
	}
	
	/**
	 * Prevents this task from sending any messages to the chat.
	 */
	public void setSilent()
	{
		silent = true;
	}
}
