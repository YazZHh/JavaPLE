package engine;

import game.Model;

public class Physic {
	private Model model;
	
	public Physic(Model model) {
		this.model = model;
	}
	
	public Rect superBoundingBox(Entity e, double time) {
		ISU.Coord center = e.isu.new Coord(e.center().x_cm+(e.lSpeed().targetX_cm*time)/2, e.center().y_cm+(e.lSpeed().targetY_cm*time)/2);
		ISU.Dimension dimensions = e.isu.new Dimension(Math.abs(e.lSpeed().targetX_cm*time)+e.size.x_cm, Math.abs(e.lSpeed().targetY_cm*time)+e.size.y_cm);
		Rect s_bb = new Rect(center, dimensions, 0);
		return s_bb;
	}
}
