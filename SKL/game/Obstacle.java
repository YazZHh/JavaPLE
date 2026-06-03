package game;

import engine.Entity;
import engine.Game;
import engine.Rect;

public class Obstacle extends Entity {

	// CONSTRUCTOR
	public Obstacle(int x_ncell, int y_ncell) {
		super("Obstacle");
		this.setSize(this.isu.new Dimension(Game.cmPerCell, Game.cmPerCell));
		this.setPosition(this.grid.new Position(x_ncell, y_ncell));
		this.setBounding();
		this.occupy(this.position());
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Rect(this.center(), this.size, this.orientation()));
	}

}
