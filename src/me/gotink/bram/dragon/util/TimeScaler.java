package me.gotink.bram.dragon.util;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

public class TimeScaler {
	
	public static final float FPS = 60;
	
	public static long time() {
		return System.currentTimeMillis();
	}
	
	private static long pause, pauseTime;
	public static void paused(long millis) {
		pause = millis;
		pauseTime = time();
	}

	protected long prevUpdate, prevPauseTime;
	private float scale;

	public TimeScaler() {
		prevUpdate = 0;
		prevPauseTime = pauseTime;
	}

	protected final void updateTiming() {
		if (prevUpdate == 0) {
			scale = 1;
			prevUpdate = time();
			return;
		}
		
		long prev = prevUpdate;
		if (prevPauseTime != pauseTime) {
			prev += pause;
			prevPauseTime = pauseTime;
		}
		long curr = prevUpdate = time();
		
		float fps = 1000f / (curr - prev);
		scale = FPS / fps;
	}

	protected final float scale(float v) {
		return scale * v;
	}

	protected final Vector2f scale(Vector2f v) {
		v.scale(scale);
		return v;
	}

	protected final Vector2d scale(Vector2d v) {
		v.scale(scale);
		return v;
	}

}