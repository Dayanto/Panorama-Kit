/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;

/**
 * Prints chat messages to the player inside the game. Each instance can be silenced in order to
 * block any output.
 *
 * @author dayanto
 */
public class ChatPrinter
{
	private boolean silent = false;

	/**
	 * Prevents this task from sending any messages to the chat.
	 */
	public void setSilent()
	{
		silent = true;
	}

	/**
	 * Attempts to send a message to the chat, but fails if this task has been silenced.
	 */
	public void print(String msg) {
		if(!silent)
		{
			PrintUtil.printChatMessage(msg);
		}
	}

	/**
	 * Attempts to send an instance of IChatComponent to the chat, but fails if this task has been silenced.
	 */
	public void print(IChatComponent msg) {
		if(!silent)
		{
			PrintUtil.printChatMessage(msg);
		}
	}

	/**
	 * Attempts to send a translated message to the chat, but fails if this task has been silenced.
	 */
	public void printTranslated(String msg, String ... params) {
		if(!silent)
		{
			PrintUtil.printTranslatedMessage(msg, params);
		}
	}

}

class PrintUtil
{
	private static final Minecraft mc = Minecraft.getMinecraft();


	public static void printChatMessage(String msg)
	{
		printChatMessage(new ChatComponentText(msg));
	}

	public static void printChatMessage(IChatComponent msg)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(msg);
	}

	public static void printTranslatedMessage(String key, String ... params)
	{
		ArrayList<Object> list = new ArrayList<Object>();
		for(String s : params)
		{
			list.add(new ChatComponentText(s));
		}
		printChatMessage(new ChatComponentTranslation(key, list.toArray()));
	}
}
