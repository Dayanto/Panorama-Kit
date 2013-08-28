/*
 ** 2012 March 9
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
import net.minecraft.client.renderer.EntityRenderer;

/**
 * Helper class to access some of EntityRenderer's private fields.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityRendererAccessor
{
	
	private static final Logger L = Logger.getLogger(EntityRendererAccessor.class.getName());
	
	private final static int FIELD_SMOOTH_CAM_FILTER_X;
	private final static int FIELD_SMOOTH_CAM_FILTER_Y;
	private final static int FIELD_CAM_ZOOM;
	private final static int FIELD_CAM_XOFS;
	private final static int FIELD_CAM_YOFS;
	private final static int FIELD_PREV_FRAME_TIME;
	
	private final static int FIELD_CAM_ROLL;
	private final static int FIELD_PREV_CAM_ROLL;
	
	static {
		Field[] fields = EntityRenderer.class.getDeclaredFields();
		int fieldOfs = -1;
		
		for (int i = 0; i < fields.length - 3; i++) {
			if (fields[i].getType() == Double.TYPE && fields[i + 1].getType() == Double.TYPE && fields[i + 2].getType() == Double.TYPE
					&& fields[i + 3].getType() == Long.TYPE) {
				fieldOfs = i;
				break;
			}
		}
		
		if (fieldOfs == -1) {
			L.severe("Couldn't find fields for EntityRenderer!");
			
			// default offsets
			FIELD_SMOOTH_CAM_FILTER_X = 24;
			FIELD_SMOOTH_CAM_FILTER_Y = 25;
			FIELD_CAM_ZOOM = 39;
			FIELD_CAM_XOFS = 40;
			FIELD_CAM_YOFS = 41;
			FIELD_PREV_FRAME_TIME = 42;
			
			FIELD_CAM_ROLL = 28;
			FIELD_PREV_CAM_ROLL = 27;
		} else {
			FIELD_SMOOTH_CAM_FILTER_X = fieldOfs - 15;
			FIELD_SMOOTH_CAM_FILTER_Y = fieldOfs - 14;
			FIELD_CAM_ZOOM = fieldOfs;
			FIELD_CAM_XOFS = fieldOfs + 1;
			FIELD_CAM_YOFS = fieldOfs + 2;
			FIELD_PREV_FRAME_TIME = fieldOfs + 3;
			
			FIELD_CAM_ROLL = fieldOfs - 11;
			FIELD_PREV_CAM_ROLL = fieldOfs - 10;
		}
	}
	
	private final Minecraft mc = Minecraft.getMinecraft();
	
	public void setCameraRoll(float cameraRoll)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, cameraRoll, FIELD_CAM_ROLL);
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, cameraRoll, FIELD_PREV_CAM_ROLL);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public float getCameraRoll()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_CAM_ROLL);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public void setCameraZoom(double zoom)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, zoom, FIELD_CAM_ZOOM);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public double getCameraZoom()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_CAM_ZOOM);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public void setCameraOffsetX(double offset)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, offset, FIELD_CAM_XOFS);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public double getCameraOffsetX()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_CAM_XOFS);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public void setCameraOffsetY(double offset)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, offset, FIELD_CAM_YOFS);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public double getCameraOffsetY()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_CAM_YOFS);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public void setPreviousFrameTime(long time)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, time, FIELD_PREV_FRAME_TIME);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public double getPreviousFrameTime()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_PREV_FRAME_TIME);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public void setSmoothCamFilterX(float value)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, value, FIELD_SMOOTH_CAM_FILTER_X);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public float getSmoothCamFilterX()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_SMOOTH_CAM_FILTER_X);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
	
	public void setSmoothCamFilterY(float value)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, value, FIELD_SMOOTH_CAM_FILTER_Y);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
		}
	}
	
	public float getSmoothCamFilterY()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, mc.entityRenderer, FIELD_SMOOTH_CAM_FILTER_Y);
		} catch (Exception ex) {
			L.log(Level.SEVERE, null, ex);
			return 0;
		}
	}
}
