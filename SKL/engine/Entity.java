// == ENTITY ==
package engine;

import java.io.PrintStream;

import engine.ISU.Coord;
import engine.ISU.Dimension;
import engine.ISU.Vector;

class Entity {

	// FIELDS
	public Grid grid;
	public ISU isu;
	public String name;
	public Game game;

	// FIELDS
	public ISU.Dimension size; // dimension de l'entité
	public ISU.Dimension step; // dimension d'un pas de déplacement
	public Grid.Position position; // position dans la grille
	public ISU.Coord center; // coordonnées en cm du centre de l'entité

	// FIELDS
	public 	int orientation_degree; // orientation par rapport à l'axe des x

	// CONSTRUCTOR
	public Entity(String name) {
		this.name = name;
		this.game = Game.game();
		this.grid = game.grid;
		this.isu = game.isu;
		this.orientation_degree = 0;
	}

	// SETTER
	public void setPosition(Grid.Position position) {
		this.position = position;
	}

	public void setCoord(ISU.Coord center) {
		this.center = center;
	}

	public void setSize(Grid.Dimension dimension) {
		this.size = dimension.toISUDimension();
	}

	public void setSize(ISU.Dimension dimension) {
		this.size = dimension;
	}

	// GETTER
	public ISU.Coord center() {
		return this.center;
	}

	public Grid.Position position() {
		return this.position;
	}

	public int orientation() {
		return this.orientation_degree;
	}

	// TRANSLATION
	public void translate(Grid.Vector v) {
		if (this.position != null)
			this.grid.cellAt(this.position).remove(this);
		this.position.translate(v);
		this.center = this.position.toISUCoordCentered();
		this.grid.cellAt(this.position).add(this);
	}

	public void translate(ISU.Vector v) {
		if (this.position != null)
			this.grid.cellAt(this.position).remove(this);
		this.center.x_cm = this.isu.xAxis.normalize(this.center.x_cm + v.targetX_cm);
		this.center.y_cm = this.isu.yAxis.normalize(this.center.y_cm + v.targetY_cm);
		this.position = this.center.toGridPosition();
		this.grid.cellAt(this.position).add(this);
	}

	// TURN
	/**
	 * @apiNote turn is a rotation around the center of the entity.
	 * @param angle_degree
	 */
	public void turn(int angle_degree) {
		this.orientation_degree = (this.orientation_degree + angle_degree) % 360;
		if (this.orientation_degree < 0)
			this.orientation_degree += 360;
	}

	// SHOW
	public void show(PrintStream ps) {
		ps.printf("Entity '%s' -> ", this.name);
		if (this.position != null) {
			ps.printf("Grid: ");
			this.position.show(ps);
		}
		if (this.center != null) {
			ps.printf(" | Center: ");
			this.center.show(ps);
		}
		ps.printf(" | Orientation: %d°\n", this.orientation_degree);
	}

	// === MOVE ===
	/**
	 * @apiNote déplacement vers le nord en nombre de pas
	 * @param nStep
	 */
	public void moveNorth(int nStep) {
		Grid.Vector v = this.grid.new Vector(0, -nStep);
		this.translate(v);
	}

	public void moveSouth(int nStep) {
		Grid.Vector v = this.grid.new Vector(0, nStep);
		this.translate(v);
	}
	
	public void moveEast(int nStep) {
		Grid.Vector v = this.grid.new Vector(nStep, 0);
		this.translate(v);
	}

	public void moveWest(int nStep) {
		Grid.Vector v = this.grid.new Vector(-nStep, 0);
		this.translate(v);
	}

	/**
	 * @apiNote déplacement vers l'est en cm
	 * @param length_cm
	 */
	public void moveNorth(double length_cm) {
		ISU.Vector v = this.isu.new Vector(0.0, length_cm);
		this.translate(v);
	}

	public void moveSouth(double length_cm) {
		ISU.Vector v = this.isu.new Vector(0.0, length_cm);
		this.translate(v);
	}
	
	public void moveEast(double length_cm) {
		ISU.Vector v = this.isu.new Vector(length_cm, 0.0);
		this.translate(v);
	}

	public void moveWest(double length_cm) {
		ISU.Vector v = this.isu.new Vector(-length_cm, 0.0);
		this.translate(v);
	}

}
