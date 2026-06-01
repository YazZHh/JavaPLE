// = Rect =
package engine;

import engine.ISU.Vector;

public class Rect extends Shape implements iShape{

	// FIELDS
	private double halfWidth, halfHeight;
	private int angle_degree;

	// CONSTRUCTOR
	public Rect(ISU.Coord center, ISU.Dimension size, int angle_degree) {
		super(center);
		this.halfWidth = size.x_cm/2;
		this.halfHeight = size.y_cm/2;	
		this.angle_degree = angle_degree;
	}
	
	// GETTER
	public double getHalfWidth() {
		return this.halfWidth;
	}
	
	public double getHalfHeight() {
		return this.halfHeight;
	}

	// TRANSLATION ?

	// ROTATION
	private void rotate(int angle_degree) {
		this.angle_degree = angle_degree;
	}

	// == INTERSECTION ==
	public boolean intersects(iShape shape) {
		return shape.intersects(this);
	}

	// === Rect/Circle Intersection ===
	public boolean intersects(Circle circle) {
		RectCircleIntersection rc = new RectCircleIntersection(this, circle);
		return rc.intersects();
	}

	// === Helping inner class ===
	/**
	 * @implNote Principe
	 *           <UL>
	 *           <LI>translate le centre du cercle vers le repère formé par les axes
	 *           du rectangle,</LI>
	 *           <LI>redresse le repère du rectangle en annulant la rotation du
	 *           rectangle,</LI>
	 *           <LI>détermine le point <i>P</i> du rectangle le plus proche du
	 *           centre <i>C</i> du cercle de façon efficace car le rectangle est
	 *           aligné sur les axes X,Y.</LI>
	 *           </UL>
	 * @implNote Il y a intersection si distance(P,C) < rayon du cercle</LI>
	 */
	public class RectCircleIntersection {

		// FIELDS
		private Rect outer;
		private Circle circle;

		// CONSTRUCTOR
		public RectCircleIntersection(Rect outer, Circle circle) {
			this.outer = new Rect(outer.center.mkCopy(), isu.new Dimension(outer.getHalfWidth()*2, outer.getHalfHeight()*2), outer.angle_degree);
			this.circle = new Circle(circle.center.mkCopy(), circle.getRadius()); 
			remedy();
		}

		// REMEDY means `set right an undesirable situation`
		/**
		 * @apiNote
		 * @implNote Translate virtuellement Rect et Circle dans un repère centré sur le
		 *           centre du rectangle donc les axes sont ceux du rectangle.
		 * @implNote Les coordonnées du centre du rectangle deviennent alors (0,0)
		 * @implNote On translate le centre du cercle
		 * @implNote On déplace par rotation le centre du cercle de -Rect.angle.
		 */
		public void remedy() {
			Vector v = isu.new Vector(-this.outer.center.x_cm, -this.outer.center.y_cm);
			this.outer.center.translate(v);
			this.circle.center.translate(v);
			this.circle.center.rotateAround(this.outer.center, -this.outer.angle_degree);
		}

		// INTERSECTION in the easy case
			public boolean intersects() {
				ISU.Coord p = closestRectpoint();
			    double deltaX = this.circle.center.x_cm - p.x_cm;
			    double deltaY = this.circle.center.y_cm - p.y_cm;
			    return Math.sqrt(deltaY*deltaX + deltaY*deltaY) < this.circle.getRadius();
			}

//		return circle.center.distanceTo(p) < circle.getRadius();
		
		/**
		 * @apiNote POINT LE PLUS PROCHE DU CENTRE DU CERCLE
		 * @implNote on projette les coins du rectange (c1,c2) et le centre (c) du
		 *           cercle sur l'axe des <i>x<i>, la coordonnées en x du point le plus
		 *           proche est parmi {c1.x, c2.x, c.x}
		 * @implNote on projette les coins du rectange (c1,c2) et le centre (c) du
		 *           cercle sur l'axe des <i>y<i>, la coordonnées en x du point le plus
		 *           proche est parmi {c1.y, c2.y, c.y}
		 *
		 */
		public ISU.Coord closestRectpoint() {
			double closestX = clamp(this.circle.center.x_cm, -this.outer.getHalfWidth(), this.outer.getHalfWidth());
			double closestY = clamp(this.circle.center.y_cm, -this.outer.getHalfHeight(), this.outer.getHalfHeight());
			return this.outer.isu.new Coord(closestX, closestY);
		}

		/**
		 * @return ∈ {p, l, r}
		 * @implNote la position dans l'interval [l,r] la plus proche de p est :
		 * @implNote p si p ∈ [l,r]
		 * @implNote l si p < l
		 * @implNote r si r < p
		 * @param p = position
		 * @param l = borne inférieure de l'intervalle
		 * @param r = borne supérieure de l'intervalle
		 */
		public double clamp(double p, double l, double r) {
			if (l <= p && p <= r)
				return p;
			else if (p < l)
				return l;
			return r;
		}

	}

	// === Rect/Rect Intersection ===
	public boolean intersects(Rect rect) {
		RectRectIntersection rr = new RectRectIntersection(this, rect);
		// instancier deux fois ??
		return rr.intersects();
	}

	// === Helping inner class ===
	public class RectRectIntersection {
		private Rect rect1, rect2;
		
		public RectRectIntersection(Rect rect1, Rect rect2) {
			this.rect1 = rect1;
			this.rect2 = rect2;
		}
		
		public boolean intersects() {
			throw new UnsupportedOperationException("UNIMPLEMENTED METHOD `clamp`");
		}
	}
}
