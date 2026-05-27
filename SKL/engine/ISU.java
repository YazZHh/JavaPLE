// = ISU =
package engine;

import java.io.PrintStream;

import engine.Grid.Dimension;

public class ISU {

	// FIELDS
	Axis xAxis, yAxis;
	ISU isu;
	Grid grid;

	// CONSTRUCTOR
	ISU(Game game) {
		this.xAxis = new Axis(game.torusOnXaxis, game.width_ncell * game.cmPerCell);
		this.yAxis = new Axis(game.torusOnYaxis, game.height_ncell * game.cmPerCell);
	}

	// SETTER
	void set(Grid grid) {
		this.grid = grid;
	}

	// == DIMENSION (cm) ==
	public class Dimension {
		double x_cm, y_cm;

		// CONSTRUCTOR
		Dimension(double x_cm, double y_cm) {
			this.x_cm = x_cm;
			this.y_cm = y_cm;
		}

		// GEOMETRY
		void normalize() {
			this.x_cm = xAxis.normalize(this.x_cm);
			this.y_cm = yAxis.normalize(this.y_cm);
		}

		// SETTER

		void setxy(double x_cm, double y_cm) {
			this.x_cm = x_cm;
			this.y_cm = y_cm;
		}

		// GETTER
		ISU isu() {
			return ISU.this;
		}

		// EQUALS / EQUIV
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o instanceof Dimension)
				return this.x_cm == ((Dimension) o).x_cm && this.y_cm == ((Dimension) o).y_cm;
			return false;
		}

		boolean equiv(Dimension d) {
			if (d == null)
				return false;
			return xAxis.normalize(this.x_cm) == xAxis.normalize(d.x_cm) && yAxis.normalize(this.y_cm) == yAxis.normalize(d.y_cm);
		}

		// GETTER
		double x() {
			return x_cm;
		}

		double y() {
			return y_cm;
		}

		// FACTORY
		ISU.Vector mkScaledVector(double factor) {
			return ISU.this.new Vector(this.x_cm * factor, this.y_cm * factor);
		}

		ISU.Vector mkScaledVector(double xFactor, double yFactor) {
			return ISU.this.new Vector(this.x_cm * xFactor, this.y_cm * yFactor);
		}

		ISU.Vector mkVector() {
			return ISU.this.new Vector(this.x_cm, this.y_cm);
		}

		// SHOW
		void show(PrintStream ps) {
			ps.printf("ISUDim(%.2fcm, %.2fcm)", this.x_cm, this.y_cm);
		}

	}

	// == POINT ==
	public class Coord {

		// CONSTRUCTOR
		Coord(double x_cm, double y_cm) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		// SHOW
		void show(PrintStream ps) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		// EQUALS
		public boolean equals(Object o) {
			return false;
		}

		// FACTORY
		ISU.Vector mkVectorToward(Coord target) {
			return null;
		}

		// CONVERSION
		Grid.Position toGridPosition() {
			return null;
		}

		// TRANSLATION
		void translate(ISU.Vector v) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		ISU.Coord mkTranslated(ISU.Vector v) {
			return null;
		}

		// COPY
		ISU.Coord mkCopy() {
			return null;
		}

		// ROTATION
		/**
		 * @apiNote rotation around the origin (0,0)
		 * @param angle_degree
		 */
		void rotation(int angle_degree) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		/**
		 * @apiNote rotation around the given center
		 * @param center
		 * @param angle_degree
		 */
		void rotateAround(Coord center, int angle_degree) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		// DISTANCE
		double distanceTo(Coord pt) {
			return 0.0;
		}

	}

	// == VECTOR ==
	/**
	 * @apiNote The Vector class defines canonical vectors with origin in (0,0)
	 *          poiting at a target coordinate.
	 * @apiNote Canonocal vectors are defined by their target Coord.
	 */
	public class Vector {

		// CONSTRUCTOR
		Vector(double targetX_cm, double targetY_cm) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		// OPERATOR
		void add(Vector v) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		void scale(double factor) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		void scale(double xFactor, double yFactor) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		/**
		 * @apiNote produit scalaire
		 * @param v
		 * @return le produit scalaire de `this` et du vecteur v
		 */
		double dot(ISU.Vector v) {
			return 0.0;
		}

		double norm() {
			return 0.0;
		}

		/**
		 * @apiNote rend le vecteur unitaire, ie. de norme = 1
		 */
		void unity() {
			throw new UnsupportedOperationException("Unimplemented method");
		}

		// TURN
		/**
		 * @apiNote turn the vector itself
		 * @implNote the center of the rotation is the origin of the vector
		 * @param angle_degree
		 */
		void turn(int angle_degree) {
			throw new UnsupportedOperationException("Unimplemented method");
		}

	}

}
