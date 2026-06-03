// == PAC MAN ==
package game;

import engine.Circle;
import engine.Entity;
import engine.Game;
import engine.ISU.Dimension;

public class PacMan extends Entity {

	// CONSTRUCTOR
	public PacMan() {
		super("PacMan");
		this.setSize(this.isu.new Dimension(Game.cmPerCell/2.0, Game.cmPerCell/2.0));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Circle(this.center(), Game.cmPerCell/4.0));
	}

}
