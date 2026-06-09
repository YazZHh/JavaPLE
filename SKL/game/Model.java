package game;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid;
import engine.ISU;
import engine.ISU.Vector;
import engine.Physic;
import game.PacMan.PacManAvatar;

public class Model {
	// FIELDS
	private Grid grid;
	private List<Entity> entities;
	private Physic ps;

	// CONSTRUCTOR
	public Model() {
		this.grid = Game.game().grid;
		this.entities = new ArrayList<Entity>();
		this.ps = new Physic(this);
	}
	
	// ADD, REMOVE Entity
	public void add(Entity e) {
		this.entities.add(e);
	}

	public void remove(Entity e) {
		this.entities.remove(e);
	}
	
	// GETTER
	public List<Entity> entities(){
		return this.entities;
	}
	
	public Grid grid() {
		return this.grid;
	}
	
	public void tick(long elapsed) {
		if (elapsed > 100) { 
	        elapsed = 10;
	    }
		double seconds = elapsed / 1000.0;
		List<Entity> copyEntities = new ArrayList<>(this.entities);
		
		for (Entity entity : copyEntities) {
			ISU.Vector speedVector = entity.lSpeed();
			double angularSpeed = entity.aSpeed();
			
			if (angularSpeed != 0) {
				entity.turn((int) (angularSpeed * seconds));
				List<Entity> collisions = this.getCollision(entity);
				if (!collisions.isEmpty()) {
					entity.turn((int) -(angularSpeed * seconds));
					entity.setaSpeed(0);
					entity.collision(collisions);
				}
			}
			
			if (entity.haslSpeed()) {
				List<Entity> trueCollisions = this.ps.getCollisionsForMovement(entity, seconds);
				
				if (trueCollisions.isEmpty()) {
					double moveX = speedVector.targetX_cm * seconds;
					double moveY = speedVector.targetY_cm * seconds;
					entity.translate(entity.isu.new Vector(moveX, moveY));
				} else {
					entity.setlSpeed(0, 0); 
					entity.collision(trueCollisions);
				}
			}
			
//			if (entity instanceof PacMan) {
//				PacManAvatar a = ((PacMan) entity).avatar();
//				a.index++;
//				if (a.index > 2)
//					a.index = 0;
//			}
		}
	}
	
	public List<Entity> getCollision(Entity e){
		List<Entity> collisions = new ArrayList<Entity>();
		for (Entity entity : this.entities) {
			if (entity != e && entity.intersects(e))
				collisions.add(entity);
		}
		return collisions;
	}
}
