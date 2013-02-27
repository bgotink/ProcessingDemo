package me.gotink.bram.dragon.drawable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CompositeDrawable extends Drawable implements Iterable<Drawable> {

	private final Queue<Drawable> content;
	
	public CompositeDrawable() {
		content = new LinkedList<Drawable>();
	}
	
	public CompositeDrawable(Drawable... drawables) {
		this();
		
		Collections.addAll(content, drawables);
	}
	
	public void add(Drawable d) {
		content.add(d);
	}
	
	public void remove(Drawable d) {
		content.remove(d);
	}
	
	public void clear() {
		content.clear();
	}
	
	@Override
	protected void draw_impl() {
		throw new RuntimeException("Impossible to reach!");
	}

	@Override
	public void draw() {
		for (Drawable d: this)
			d.draw();
	}

	@Override
	public Iterator<Drawable> iterator() {
		return content.iterator();
	}
	
	@Override
	public CompositeDrawable clone() {
		CompositeDrawable clone = (CompositeDrawable) super.clone();
		clone.clear();
		for (Drawable d: this) {
			clone.add(d.clone());
		}
		return clone;
	}

}
