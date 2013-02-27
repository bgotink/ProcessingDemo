package me.gotink.bram.dragon.drawable;

import javax.vecmath.Point2f;

import me.gotink.bram.dragon.main.AbstractApplet;

public class Line extends Drawable {
	
	private final Point2f from, to;
	
	public Line(AbstractApplet applet, Point2f from, Point2f to) {
		super(applet);
		this.from = from;
		this.to = to;
	}

	@Override
	protected void draw_impl() {
		applet.line(from.x, from.y, to.x, to.y);
	}
	
	@Override
	public Line clone() {
		return (Line) super.clone();
	}

}
