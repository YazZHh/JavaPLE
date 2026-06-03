// == GHOST ==
package game;

import engine.Circle;
import engine.Entity;
import engine.Game;
import engine.ISU.Dimension;
import engine.Rect;

public class Ghost extends Entity {

	// CONSTRUCTOR
	public Ghost() {
		super("Ghost");
		this.setSize(this.isu.new Dimension(Game.cmPerCell/2.0, Game.cmPerCell/2.0));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Rect(this.isu.new Coord(this.center().x_cm, this.center().y_cm+Game.cmPerCell/8), this.isu.new Dimension(Game.cmPerCell/2.0, Game.cmPerCell/4.0), this.orientation()));
		this.bounding().add(new Circle(this.center(), Game.cmPerCell/4.0));
	}

}
