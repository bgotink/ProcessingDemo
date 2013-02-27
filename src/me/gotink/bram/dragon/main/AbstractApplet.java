package me.gotink.bram.dragon.main;

import javax.vecmath.Tuple2f;

import processing.core.PApplet;

public abstract class AbstractApplet extends PApplet {

	private static final long serialVersionUID = -7861374137418118031L;

	public void translate(Tuple2f p) {
		translate(p.x, p.y);
	}
	
	public void rotate(float angle, Tuple2f axis) {
		translate(axis.x, axis.y);
		rotate(angle);
		translate(-axis.x, -axis.y);
	}

	public abstract void zoom(int width, int height);
	public abstract void zoomInstant(int width, int height);

}