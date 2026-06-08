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
		Runtime.boot(d, new Main());
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
//		Gum gum = new Gum();
//		gum.setPosition(model.grid().new Position(1, 1));
		model.add(pacman);
//		model.add(gum);
		view.add(pacman.new PacManAvatar());
//		view.add(gum.new GumAvatar());
		
		BasicStunt stunt = new BasicStunt(model, pacman);
//		BasicStunt stunt2 = new BasicStunt(model,gum);
		stunt.walk(0);
		
		this.task.post(new Runnable() {
			@Override
			public void run() throws Exception {
				canvas.repaint();
				task.post(this, 50);
			}
		}, 50);
			
	}

}
