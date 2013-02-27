package me.gotink.bram.dragon.drawable;

import javax.vecmath.Point2f;
import javax.vecmath.Vector2f;

import me.gotink.bram.dragon.main.AbstractApplet;

public class TransformedDrawable extends Drawable {

	protected Drawable original;

	private float rotation;
	private Vector2f translation;
	private Point2f pivot, antiPivot;

	public TransformedDrawable(AbstractApplet applet, Drawable original) {
		super(applet);
		this.original = original;
		translation = new Vector2f();
		antiPivot = new Point2f();
	}

	public Vector2f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector2f translation) {
		this.translation = translation;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public Point2f getPivot() {
		return pivot;
	}
	
	public void setPivot(Point2f pivot) {
		this.pivot = pivot;
		if (pivot != null) antiPivot.negate(pivot);
	}
	
	@Override
	protected void draw_impl() {
		throw new RuntimeException("Impossible!");
	}

	@Override
	public void draw() {
		applet.pushMatrix();

		if (pivot == null) 
			applet.rotate(rotation);
		else {
			applet.translate(pivot);
			applet.rotate(rotation);
			applet.translate(antiPivot);
		}

		if (translation != null)
			applet.translate(translation.x, translation.y);

		original.draw();

		applet.popMatrix();
	}

	@Override
	public TransformedDrawable clone() {
		TransformedDrawable clone = (TransformedDrawable) super.clone();

		clone.translation = new Vector2f(translation);
		clone.original = original.clone();

		return clone;
	}

}
