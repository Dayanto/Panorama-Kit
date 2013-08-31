/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.util;

import net.minecraft.client.resources.I18n;

/** 
 * @author dayanto
 */
public class DisplayStringFormatter
{
	public static String shortenString(String text, int limit, int cutIndex)
	{
		if(text.length() > limit) {
			return text.substring(0, cutIndex - 3) + "... ..." + text.substring(text.length() - limit + cutIndex + 3, text.length());
		}
		return text;
	}
	
	public static String translate(String key)
	{
		return I18n.func_135053_a(key);
	}
	
	public static String[] translate(String[] keys)
	{
		String[] translated = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			translated[i] = translate(keys[i]);
		}
		return translated;
	}
}
