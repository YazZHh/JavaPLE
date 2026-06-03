package game;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Game;
import engine.Grid;

public class Model {
	// FIELDS
	Grid grid;
	List<Entity> entities;

	// CONSTRUCTOR
	public Model() {
		this.grid = Game.game().grid;
		this.entities = new ArrayList<Entity>();
		initMap();
	}
	
	public void initMap() {
		this.add(new Obstacle(0, 0));
		this.add(new Obstacle(1, 0));
		this.add(new Obstacle(2, 0));
		
		PacMan pacman = new PacMan();
		this.add(pacman);
		pacman.occupy(this.grid.new Position(5, 5));
		pacman.setBounding();
		
		Gum gum = new Gum();
		this.add(gum);
		gum.occupy(this.grid.new Position(5, 6));
		gum.setBounding();

		// 3. Un Fantôme
		Ghost ghost = new Ghost();
		this.add(ghost);
		ghost.occupy(this.grid.new Position(2, 2));
		ghost.setBounding();

		Boss boss = new Boss();
		this.add(boss);
		boss.occupy(this.grid.new Position(8, 4));
		boss.setBounding();
	}

	// ADD, REMOVE Entity
	public void add(Entity e) {
		this.entities.add(e);
	}

	public void remove(Entity e) {
		this.entities.remove(e);
	}
}
