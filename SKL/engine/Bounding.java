// = BOUNDING =
package engine;

import java.util.HashSet;
import java.util.Set;

public class Bounding {

	// FIELDS
	private Set<iShape> boundings;

	// CONSTRUCTOR
	public Bounding() {
		this.boundings = new HashSet<iShape>();
	}
	
	// GETTER
	public Set<iShape> boundings() {
		return this.boundings;
	}

	// BUILDER
	public void add(iShape shape) {
		if (shape != null)
			this.boundings.add(shape);
	}

	// INTERSECTION
	public boolean intersects(iShape shape) {
		if (shape == null)
			return false;
		for (iShape s : boundings) {
			if (s.intersects(shape))
				return true;
		}
		return false;
	}

	public boolean intersects(Bounding bounding) {
		if (bounding == null)
			return false;
		for (iShape s1 : this.boundings) {
			for (iShape s2 : bounding.boundings()) {
				if (s1.intersects(s2))
					return true;
			}
		}
		return false;
	}

}
