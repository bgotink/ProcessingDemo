package me.gotink.bram.dragon.main;

import me.gotink.bram.dragon.drawable.Dragon;
import me.gotink.bram.dragon.util.TimeScaler;

public class DragonApplet extends AbstractApplet {

	private static final long serialVersionUID = -3773977679665795360L;
	
	private static class Scaler extends TimeScaler {
		
		public static final int SCALE_SECONDS = 4;
		
		private float speed;
		private float curScale, targetScale;
		private boolean done = false;
		
		public Scaler(float old, float _new) {
			curScale = old;
			speed = (_new - old) / (SCALE_SECONDS * FPS);
			targetScale = _new;
		}
		
		public Scaler(float t) {
			this(t, t);
			done = true;
		}
		
		public float getScale() {
			return curScale;
		}
		
		public void update() {
			if (done)
				return;
			
			updateTiming();
			curScale += scale(speed);
			if (Math.signum(speed) != Math.signum(targetScale - curScale)) {
				curScale = targetScale;
				done = true;
			}
		}
		
		public void skip() {
			curScale = targetScale;
			done = true;
		}
	}

	private Dragon dragon;
	private Scaler scale;
	private boolean paused;

	@Override
	public void setup() {
		size(800, 800);
		smooth();

		scale = new Scaler(1);
		
		Dragon.Config config = new Dragon.Config();
		config.startLine.x = 5;
		config.zoomIn = true;
		
		dragon = new Dragon(this, config);
	}

	@Override
	public void draw() {
		if (paused) return;
		stroke(255, 255, 255);
		background(0, 0, 0);
		
		translate(width / 2, height / 2);
		
		scale.update();
		scale(1 / scale.getScale());
		
		dragon.draw();
	}

	@Override
	public void zoom(int width, int height) {
		double w = ((double) width) / ((double) this.width);
		double h = ((double) height) / ((double) this.height);
		
		double max = Math.max(w, h);
		scale = new Scaler(scale.getScale(), (float) max);
	}
	
	@Override
	public void zoomInstant(int width, int height) {
		zoom(width, height);
		scale.skip();
	}
	
	private long pauseStartTime;
	@Override
	public void mouseClicked() {
		paused = !paused;
		if (paused) {
			pauseStartTime = TimeScaler.time();
		} else {
			TimeScaler.paused(TimeScaler.time() - pauseStartTime);
		}
	}
	
}
