package engine;

import java.util.ArrayList;
import java.util.List;

import game.Model;

public class Physic {
	private Model model;
	
	public Physic(Model model) {
		this.model = model;
	}
	
	public Rect superBoundingBox(Entity e, double time) {
		double moveX = e.lSpeed().targetX_cm * time;
	    double moveY = e.lSpeed().targetY_cm * time;

	    double centerX = e.center().x_cm + (moveX / 2.0);
	    double centerY = e.center().y_cm + (moveY / 2.0);
	    ISU.Coord center = e.isu.new Coord(centerX, centerY);

	    double width = e.size.x_cm + Math.abs(moveX);
	    double height = e.size.y_cm + Math.abs(moveY);
	    ISU.Dimension dimensions = e.isu.new Dimension(width, height);

	    Rect s_bb = new Rect(center, dimensions, 0);
	    return s_bb;
	}
	
	public double getCollisionEntryTime(Entity e, Entity other, double time) {
		double moveX_e = e.lSpeed().targetX_cm * time;
		double moveY_e = e.lSpeed().targetY_cm * time;
		
		double moveX_other, moveY_other;

		if (other.haslSpeed()) {
		    moveX_other = other.lSpeed().targetX_cm * time;
		    moveY_other = other.lSpeed().targetY_cm * time;
		} else {
		    moveX_other = 0;
		    moveY_other = 0;
		}
		
		double vrelx = moveX_e - moveX_other;
		double vrely = moveY_e - moveY_other;
		
		double xInvEntry, yInvEntry;
		double xInvExit, yInvExit;

		// Axe X
		if (vrelx > 0) {
			xInvEntry = other.center().x_cm - (other.size.x_cm / 2.0) - (e.center().x_cm + (e.size.x_cm / 2.0));
			xInvExit = (other.center().x_cm + (other.size.x_cm / 2.0)) - (e.center().x_cm - (e.size.x_cm / 2.0));
		} else {
			xInvEntry = (other.center().x_cm + (other.size.x_cm / 2.0)) - (e.center().x_cm - (e.size.x_cm / 2.0));
			xInvExit = other.center().x_cm - (other.size.x_cm / 2.0) - (e.center().x_cm + (e.size.x_cm / 2.0));
		}
		
		double txEntry, txExit;
		if (vrelx == 0) {
			txEntry = Double.NEGATIVE_INFINITY;
			txExit = Double.POSITIVE_INFINITY;
		} else {
			txEntry = xInvEntry / vrelx;
			txExit = xInvExit / vrelx;
		}

		// Axe Y
		if (vrely > 0) {
			yInvEntry = other.center().y_cm - (other.size.y_cm / 2.0) - (e.center().y_cm + (e.size.y_cm / 2.0));
			yInvExit = (other.center().y_cm + (other.size.y_cm / 2.0)) - (e.center().y_cm - (e.size.y_cm / 2.0));
		} else {
			yInvEntry = (other.center().y_cm + (other.size.y_cm / 2.0)) - (e.center().y_cm - (e.size.y_cm / 2.0));
			yInvExit = other.center().y_cm - (other.size.y_cm / 2.0) - (e.center().y_cm + (e.size.y_cm / 2.0));
		}
		
		double tyEntry = 0, tyExit = 0;
		if (vrely == 0) {
			txEntry = Double.NEGATIVE_INFINITY;
			txExit = Double.POSITIVE_INFINITY;
		} else {
			tyEntry = yInvEntry / vrely;
			tyExit = yInvExit / vrely;
		}

		double entryTime = Math.max(txEntry, tyEntry);
		double exitTime = Math.min(txExit, tyExit);

		if (entryTime > exitTime || (txEntry < 0.0 && tyEntry < 0.0) || txEntry > 1.0 || tyEntry > 1.0)
			return 1.0;

		return entryTime;
	}
	
	public List<Entity> getCollisionsForMovement(Entity e, double time) {
		List<Entity> finalCollisions = new ArrayList<>();
		Rect superBoxE = this.superBoundingBox(e, time);		
		double minEntryTime = 1.0;

		for (Entity other : this.model.entities()) {
			if (other != e && superBoxE.intersects(this.superBoundingBox(other, time))) {
				double collisionTime = this.getCollisionEntryTime(e, other, time);
				if (collisionTime < 1.0) {
					finalCollisions.add(other);
					if (collisionTime < minEntryTime)
						minEntryTime = collisionTime;
				}
			}
		}
		return finalCollisions;		
		
//	    List<Entity> finalCollisions = new ArrayList<>();
//	    Rect superBoxE = this.superBoundingBox(e, time);        
//	    double minEntryTime = 1.0;
//
//	    for (Entity other : this.model.entities()) {
//	        if (other != e && other.bounding().intersects(superBoxE)) {
//	            double collisionTime = this.getCollisionEntryTime(e, other, time);
//	            if (collisionTime >= 0.0 && collisionTime < 1.0) {
//	                finalCollisions.add(other);
//	                if (collisionTime < minEntryTime) {
//	                    minEntryTime = collisionTime;
//	                }
//	            }
//	        }
//	    }
//	    return finalCollisions;
		
//		List<Entity> collisions = new ArrayList<>();
//	    
//	    Rect superBox = superBoundingBox(e, time);
//
//	    for (Entity other : model.entities()) {
//	        if (other == e)
//	        	continue;
//	        
//	        if (!other.bounding().intersects(superBox)) {
//	            continue;
//	        }
//
//	        double entryTime = getCollisionEntryTime(e, other, time);
//	        
//	        if (entryTime >= 0.0 && entryTime < 1.0) {
//	            collisions.add(other);
//	        }
//	    }
//	    return collisions;		
		
	}
}
