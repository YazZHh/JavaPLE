package game;

import java.awt.Dimension;
import java.util.List;

import engine.BasicStunt;
import engine.Entity;
import engine.Game;
import engine.Ticker;
import engine.view.PaintListener;
import engine.view.View;
import oop.graphics.Canvas;
import oop.tasks.Runnable;
import oop.tasks.Runtime;
import oop.tasks.Task;


public class Main implements Runnable {
	
	Canvas canvas;
	Task task;
	List<Entity> entities;
	public static final double windowScale = 3.0;
	public static final int headerSize = 61;
	

	public static void main(String[] args) {
		Dimension d = new Dimension((int) (224*windowScale), (int) (248*windowScale)+headerSize);
		Runtime.boot(d, new Main(), true);
	}

	@Override
	public void run() throws Exception {
		Game game = new Game((int) (30*windowScale), (int) (35*windowScale));
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model();
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
		
		PacMan pacman = new PacMan();
//		pacman.setPosition(model.grid().new Position((int) (1*windowScale), (int) (1*windowScale)));
		pacman.setCoord(Game.game().isu.new Coord(Game.cmPerCell*windowScale, Game.cmPerCell*windowScale));
		pacman.setBounding();
		model.add(pacman);
		view.add(pacman.new PacManAvatar());
		BasicStunt PacManStunt = new BasicStunt(model, pacman);
		pacman.setStunt(PacManStunt);
//		PacManStunt.walk(0);
		
		Ghost blinky = new Ghost();
//		blinky.setPosition(model.grid().new Position((int) (16*windowScale), (int) (16*windowScale)));
		blinky.setCoord(Game.game().isu.new Coord(30*Game.cmPerCell*windowScale*0.5, 35*Game.cmPerCell*windowScale*0.5));
		blinky.setBounding();
		model.add(blinky);
		view.add(blinky.new GhostAvatar(0));
		BasicStunt BlinkyStunt = new BasicStunt(model, blinky);
		blinky.setStunt(BlinkyStunt);
		BlinkyStunt.walk(0);

		Controller ct = new Controller(PacManStunt);
		KeyListener kl = new KeyListener(ct);
		canvas.set(kl);
		
		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 17);
			}
		}, 17);
			
	}

}
