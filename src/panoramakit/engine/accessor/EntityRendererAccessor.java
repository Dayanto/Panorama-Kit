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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Helper class to access some of EntityRenderer's private fields.
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class EntityRendererAccessor {

	private static final Logger L = LogManager.getLogger();

	private static int FIELD_SMOOTH_CAM_FILTER_X;
	private static int FIELD_SMOOTH_CAM_FILTER_Y;
	private static int FIELD_CAM_ZOOM;
	private static int FIELD_CAM_XOFS;
	private static int FIELD_CAM_YOFS;
	private static int FIELD_PREV_FRAME_TIME;

	private static int FIELD_CAM_ROLL;
	private static int FIELD_PREV_CAM_ROLL;

	static {
		Field[] fields = EntityRenderer.class.getDeclaredFields();
		int fieldOfs = -1;

		for (int i = 0; i < fields.length - 3; i++) {
			if (fields[i].getType() == Double.TYPE
					&& fields[i + 1].getType() == Double.TYPE
					&& fields[i + 2].getType() == Double.TYPE
					&& fields[i + 3].getType() == Long.TYPE) {
				fieldOfs = i;
				break;
			}
		}

		if (fieldOfs != -1) {
			FIELD_SMOOTH_CAM_FILTER_X = fieldOfs - 21;
			FIELD_SMOOTH_CAM_FILTER_Y = fieldOfs - 20;
			FIELD_CAM_ZOOM = fieldOfs;
			FIELD_CAM_XOFS = fieldOfs + 1;
			FIELD_CAM_YOFS = fieldOfs + 2;
			FIELD_PREV_FRAME_TIME = fieldOfs + 3;

			FIELD_CAM_ROLL = fieldOfs - 16;
			FIELD_PREV_CAM_ROLL = fieldOfs - 15;
		} else {
			L.error("Couldn't find fields for class EntityRenderer!");
		}
	}

	private final EntityRenderer renderer;

	public EntityRendererAccessor() {
		this(Minecraft.getMinecraft().entityRenderer);
	}

	public EntityRendererAccessor(EntityRenderer renderer) {
		this.renderer = renderer;
	}

	public void setCameraZoom(double zoom) {
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, zoom, FIELD_CAM_ZOOM);
		} catch (Exception ex) {
			L.error("setCameraZoom() failed", ex);
		}
	}

	public double getCameraZoom() {
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_CAM_ZOOM);
		} catch (Exception ex) {
			L.error("getCameraZoom() failed", ex);
			return 0;
		}
	}

	public void setCameraOffsetX(double offset) {
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, offset, FIELD_CAM_XOFS);
		} catch (Exception ex) {
			L.error("setCameraOffsetX() failed", ex);
		}
	}

	public double getCameraOffsetX() {
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_CAM_XOFS);
		} catch (Exception ex) {
			L.error("getCameraOffsetX() failed", ex);
			return 0;
		}
	}

	public void setCameraOffsetY(double offset) {
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, offset, FIELD_CAM_YOFS);
		} catch (Exception ex) {
			L.error("setCameraOffsetY() failed", ex);
		}
	}

	public double getCameraOffsetY() {
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_CAM_YOFS);
		} catch (Exception ex) {
			L.error("getCameraOffsetY() failed", ex);
			return 0;
		}
	}

	public void setPreviousFrameTime(long time) {
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, time, FIELD_PREV_FRAME_TIME);
		} catch (Exception ex) {
			L.error("setPreviousFrameTime() failed", ex);
		}
	}

	public double getPreviousFrameTime() {
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_PREV_FRAME_TIME);
		} catch (Exception ex) {
			L.error("getPreviousFrameTime() failed", ex);
			return 0;
		}
	}

	public void setSmoothCamFilterX(float value) {
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, value, FIELD_SMOOTH_CAM_FILTER_X);
		} catch (Exception ex) {
			L.error("setSmoothCamFilterX() failed", ex);
		}
	}

	public float getSmoothCamFilterX() {
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_SMOOTH_CAM_FILTER_X);
		} catch (Exception ex) {
			L.error("getSmoothCamFilterX() failed", ex);
			return 0;
		}
	}

	public void setSmoothCamFilterY(float value) {
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, value, FIELD_SMOOTH_CAM_FILTER_Y);
		} catch (Exception ex) {
			L.error("setSmoothCamFilterY() failed", ex);
		}
	}

	public float getSmoothCamFilterY() {
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_SMOOTH_CAM_FILTER_Y);
		} catch (Exception ex) {
			L.error("getSmoothCamFilterY() failed", ex);
			return 0;
		}
	}

	public void setCameraRoll(float cameraRoll)
	{
		try {
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, cameraRoll, FIELD_CAM_ROLL);
			ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, cameraRoll, FIELD_PREV_CAM_ROLL);
		} catch (Exception ex) {
			L.error("setCameraRoll() failed", ex);
		}
	}

	public float getCameraRoll()
	{
		try {
			return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_CAM_ROLL);
		} catch (Exception ex) {
			L.error("getCameraRoll() failed", ex);
			return 0;
		}
	}
}
