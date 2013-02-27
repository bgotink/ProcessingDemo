package me.gotink.bram.dragon.drawable;

import javax.vecmath.Matrix3f;
import javax.vecmath.Point2f;
import javax.vecmath.Vector3f;

import me.gotink.bram.dragon.main.AbstractApplet;

public class Dragon extends Drawable {

	public static final float ANGLE = 0.5f;
	
	private static final Matrix3f PIVOT_STEP;
	
	static {
		PIVOT_STEP = new Matrix3f();
		PIVOT_STEP.setZero();
		
		double cos = Math.cos(ANGLE * Math.PI);
		double sin = Math.sin(ANGLE * Math.PI);
		
		PIVOT_STEP.m00 = PIVOT_STEP.m11 = (float) cos;
		PIVOT_STEP.m01 = (float) sin;
		PIVOT_STEP.m10 = (float) -sin;
	}
	
	public static class Config {
		
		public float speed = 0.0025f;
		public Point2f startLine = new Point2f(10, 0);
		public boolean zoomIn = false;
		
	}

	private class DragonPart extends TransformedDrawable {

		private float _rotation;
		private RotationListener listener;
		private boolean done;

		public DragonPart(AbstractApplet applet, Drawable root, RotationListener listener, Point2f pivot) {
			super(applet, root);
			this.listener = listener;
			setPivot(pivot);

			done = false;
			_rotation = 0;
		}

		@Override
		public void draw() {
			updateTiming();
			if (!done) {
				_rotation += scale(config.speed);
				if (_rotation >= ANGLE) {
					_rotation = ANGLE;
					done = true;

					listener.onFinishRotation();
					listener = null;
				}
				setRotation(_rotation * (float) Math.PI);
			}
			super.draw();
		}

	}

	private static interface RotationListener {
		void onFinishRotation();
	}

	private Config config;
	private Drawable root;
	private RotationListener listener;
	private Point2f pivot;
	private boolean rotationFinished;

	public Dragon(AbstractApplet applet, Config config) {
		super(applet);

		listener = new RotationListener() {

			@Override
			public void onFinishRotation() {
				rotationFinished = true;
			}
		};

		this.config = config;
		pivot = new Point2f(config.startLine);
		if (config.zoomIn)
			zoomApplet(true);

		root = new Line(applet, new Point2f(0, 0), new Point2f(config.startLine));
		rotationFinished = true;
	}

	@Override
	public void draw() {

		root.draw();

		if (rotationFinished) {
			rotationFinished = false;
			onFinishRotation();
		}
	}

	@Override
	protected void draw_impl() {
		throw new RuntimeException("Impossible error!");
	}

	private void onFinishRotation() {
		root = new CompositeDrawable(root, new DragonPart(applet, root, listener, pivot));
		
		Vector3f tmp, tmp2;
		tmp = new Vector3f(pivot.x, pivot.y, 0);
		tmp2 = new Vector3f(tmp);
		
		PIVOT_STEP.transform(tmp2);
		
		tmp.add(tmp2);
		pivot = new Point2f(tmp.x, tmp.y);
		
		zoomApplet(false);
	}
	
	private void zoomApplet(boolean instant) {
		int width = (int) Math.ceil(Math.abs(pivot.x * 3));
		int height = (int) Math.ceil(Math.abs(pivot.y * 3));
		
		if (instant) {
			applet.zoomInstant(width, height);
		} else {
			applet.zoom(width, height);
		}
	}

}
