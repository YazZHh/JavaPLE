package game;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid;
import engine.ISU;
import engine.ISU.Vector;

public class Model {
	// FIELDS
	private Grid grid;
	private List<Entity> entities;

	// CONSTRUCTOR
	public Model() {
		this.grid = Game.game().grid;
		this.entities = new ArrayList<Entity>();
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
	
	public void tick(long elapsed) {
		double seconds = elapsed/1000.0;
		for (Entity entity : entities) {
			ISU.Vector speedVector = entity.lSpeed();
			double angularSpeed = entity.aSpeed();
			
			if (angularSpeed != 0) {
		        entity.turn((int) (angularSpeed*seconds));
		        List<Entity> collisions = this.getCollision(entity);
		        if (!collisions.isEmpty()) {
		        	entity.turn((int) -(angularSpeed*seconds));
		        	entity.setaSpeed(0);
		        	entity.collision(collisions);
		        }
			}
			
			if (entity.haslSpeed()) {
				ISU.Coord oldPos = entity.center().mkCopy();
				double moveX = speedVector.targetX_cm * seconds;
		        double moveY = speedVector.targetY_cm * seconds;
		        entity.translate(entity.isu.new Vector(moveX, moveY));
		        List<Entity> collisions = this.getCollision(entity);
		        if (!collisions.isEmpty()) {
		        	entity.translate(entity.center().mkVectorToward(oldPos));
		        	entity.setlSpeed(0, 0);	
		        	entity.collision(collisions);
		        }
			}
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
