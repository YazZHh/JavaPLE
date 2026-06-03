// == GUM ==
package game;

import engine.Circle;
import engine.Entity;
import engine.Game;
import engine.Grid.Position;
import engine.ISU.Dimension;

public class Gum extends Entity{

	// CONSTRUCTOR
	public Gum() {
		super("Gum");
		this.setSize(this.isu.new Dimension(Game.cmPerCell/3, Game.cmPerCell/3));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Circle(this.center(), 0.1));
	}

}
