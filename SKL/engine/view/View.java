package engine.view;

import java.util.ArrayList;
import java.util.List;

import game.Avatar;
import oop.graphics.Canvas;
import oop.graphics.Graphics;

public class View {
	Canvas canvas;
	List<Avatar> avatars;
	
	public View(Canvas canvas) {
		this.canvas = canvas;
		this.avatars = new ArrayList<Avatar>();
	}
	
	public void add(Avatar e) {
		this.avatars.add(e);
	}
	
	public void paint(Graphics g) {
		for (Avatar a : avatars)
			a.paint(g);
	}
	
}
