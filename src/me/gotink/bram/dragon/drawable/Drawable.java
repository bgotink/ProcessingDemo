package me.gotink.bram.dragon.drawable;


import me.gotink.bram.dragon.main.AbstractApplet;
import me.gotink.bram.dragon.util.TimeScaler;

public abstract class Drawable extends TimeScaler implements Cloneable {
	
	protected final AbstractApplet applet;
	
	public Drawable(AbstractApplet applet) {
		this.applet = applet;
	}
	
	protected Drawable() {
		this(null);
	}
	
	public void draw() {
		updateTiming();
		draw_impl();
	}
	
	protected abstract void draw_impl();
	
	@Override public Drawable clone() {
		try {
			return (Drawable) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Impossible exception!", e);
		}
	}
}
