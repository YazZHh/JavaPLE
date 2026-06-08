package game;

import engine.Entity;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public abstract class Avatar {
	
	protected Entity e;
	protected BufferedImage[] sprites;
	
	public Avatar(Entity e) {
		this.e = e;
	}
	
	public abstract void getSprites(Graphics g);
	
	public abstract void paint(Graphics g);
}

