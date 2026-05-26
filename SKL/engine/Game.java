// == GAME ==
package engine;

import java.io.PrintStream;

public abstract class Game {

	// CONSTANT
	protected boolean torusOnXaxis = true; // vrai si l'axe X est une boucle fermée
	protected boolean torusOnYaxis = true; // vrai si l'axe Y est une boucle fermée
	protected double cmPerCell = 3.7; // échelle qui relie l'unité ncell à cm
	protected int pixelPerCm = 2; // échelle qui relie l'unité pixel à cm

	// FIELDS
	protected int width_ncell; // largeur du monde en nombre de cellules
	protected int height_ncell; // hauteur du monde en nombre de celluls
	protected double width_cm; // largeur du monde en cm
	protected double height_cm; // hauteur du monde en cm
	protected Grid grid; // permet la création de coordonnées en unités ncell
	protected ISU isu; // permet la création de coordonnées en unités cm
	// protected Picture pict; // permet la création de coordonnées en unités pixel, ne sera utilisé qu'à
					// partir de Task2

	// CONSTRUCTORS

	Game(int w_ncell, int h_ncell) {
		if (game != null)
			throw new IllegalStateException();
		game = this;
		
		assert(w_ncell > 0 && h_ncell > 0);
		this.width_ncell = w_ncell;
		this.height_ncell = h_ncell;
		this.width_cm = w_ncell * this.cmPerCell;
		this.height_cm = h_ncell * this.cmPerCell;
		this.grid = new Grid(this);
		this.isu = new ISU(this);
	}

	Game(double w_cm, double h_cm) {
		if (game != null)
			throw new IllegalStateException();
		game = this;
		
		assert(w_cm > 0 && h_cm > 0);
		this.width_ncell = (int) (w_cm / this.cmPerCell);
		this.height_ncell = (int) (h_cm / this.cmPerCell);
		this.width_cm = w_cm;
		this.height_cm = h_cm;
		this.grid = new Grid(this);
		this.isu = new ISU(this);
	}

	// GETTER

	private static Game game;

	public Game game() {
		return game;
	}

	// SHOW

	void show(PrintStream ps) {
		throw new UnsupportedOperationException("Unimplemented method");
	}
}
