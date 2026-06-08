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

	public static void main(String[] args) {
		Dimension d = new Dimension(640, 480);
		Runtime.boot(d, new Main(), true);
	}

	@Override
	public void run() throws Exception {
		Game game = new Game(10, 10);
		this.task = Runtime.task();
		this.canvas = (Canvas) this.task.find("canvas");
		View view = new View(canvas);
		canvas.set(new PaintListener(view));
		Model model = new Model();
		Task tick = Runtime.newTask("tick");
		Ticker ticker = new Ticker(model, tick, 10);
		
		PacMan pacman = new PacMan();
		pacman.setPosition(model.grid().new Position(1, 1));
//		pacman.setBounding();
//		pacman.setlSpeed(10, 0);
		model.add(pacman);
		view.add(pacman.new PacManAvatar());
		BasicStunt PacManStunt = new BasicStunt(model, pacman);
		PacManStunt.walk(0);

		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 30);
			}
		}, 30);
			
	}

}
