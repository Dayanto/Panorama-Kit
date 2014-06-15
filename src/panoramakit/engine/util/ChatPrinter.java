/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.util;

import net.minecraft.util.IChatComponent;
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
	public void print(String msg) {
		if(!silent)
		{
			PanoramaKit.instance.printChatMessage(msg);
		}
	}

	/**
	 * Attempts to send an instance of IChatComponent to the chat, but fails if this task has been silenced.
	 */
	public void print(IChatComponent msg) {
		if(!silent)
		{
			PanoramaKit.instance.printChatMessage(msg);
		}
	}

	/**
	 * Attempts to send a translated message to the chat, but fails if this task has been silenced.
	 */
	public void printTranslated(String msg, String ... params) {
		if(!silent)
		{
			PanoramaKit.instance.printTranslatedMessage(msg, params);
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
