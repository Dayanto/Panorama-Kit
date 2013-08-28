/*
 ** 2012 March 30
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package panoramakit.engine.accessor;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.Timer;

/**
 * Helper class to access private fields from the Minecraft class.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class MinecraftAccessor
{
	
	private static final Logger L = Logger.getLogger(MinecraftAccessor.class.getName());
	
	private static final int FIELD_TIMER;
	private static final int FIELD_SNDMANAGER;
	private static final int FIELD_EFFECTRENDERER;
	
	static {
		Field[] fields = Minecraft.class.getDeclaredFields();
		
		int fieldTimer = 8;
		int fieldSndManager = 0;
		int fieldEffectRenderer = 0;
		
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType() == Timer.class) {
				fieldTimer = i;
			}
			
			if (fields[i].getType() == SoundManager.class) {
				fieldSndManager = i;
			}
			
			if (fields[i].getType() == EffectRenderer.class) {
				fieldEffectRenderer = i;
			}
		}
		
		FIELD_TIMER = fieldTimer;
		FIELD_SNDMANAGER = fieldSndManager;
		FIELD_EFFECTRENDERER = fieldEffectRenderer;
	}
	
	private final Minecraft mc;
	
	public MinecraftAccessor(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public MinecraftAccessor()
	{
		this(Minecraft.getMinecraft());
	}
	
	public Minecraft getMinecraftInstance()
	{
		return mc;
	}
	
	public void setTimer(Timer timer)
	{
		try {
			ReflectionHelper.setPrivateValue(Minecraft.class, mc, timer, FIELD_TIMER);
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Couldn't set timer!", ex);
		}
	}
	
	public Timer getTimer()
	{
		try {
			return ReflectionHelper.getPrivateValue(Minecraft.class, mc, FIELD_TIMER);
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Couldn't get timer!", ex);
			return null;
		}
	}
	
	public void setSoundManager(SoundManager sndManager)
	{
		try {
			ReflectionHelper.setPrivateValue(Minecraft.class, mc, sndManager, FIELD_SNDMANAGER);
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Couldn't set sound manager!", ex);
		}
	}
	
	public SoundManager getSoundManager()
	{
		try {
			return (SoundManager) ReflectionHelper.getPrivateValue(Minecraft.class, mc, FIELD_SNDMANAGER);
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Couldn't get sound manager!", ex);
			return null;
		}
	}
	
	public void setEffectRenderer(EffectRenderer effectRenderer)
	{
		try {
			ReflectionHelper.setPrivateValue(Minecraft.class, mc, effectRenderer, FIELD_EFFECTRENDERER);
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Couldn't set effect renderer!", ex);
		}
	}
	
	public EffectRenderer getEffectRenderer()
	{
		try {
			return ReflectionHelper.getPrivateValue(Minecraft.class, mc, FIELD_EFFECTRENDERER);
		} catch (Exception ex) {
			L.log(Level.SEVERE, "Couldn't get effect renderer!", ex);
			return null;
		}
	}
}
