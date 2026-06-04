package engine;

import game.Model;
import oop.tasks.Task;
import oop.tasks.Runtime;
import oop.tasks.Runnable;

public class Ticker implements Runnable {
	private Model model;
	private Task task;
	private long time;
	
	public Ticker(Model model) {
		long time = System.currentTimeMillis();
		this.task = Runtime.newTask("Ticker");
		this.model = model;
		this.task.post(()->{
			run();
		});
	}

	@Override
	public void run() {
		long now = System.currentTimeMillis();
		long elapsed = now - this.time;
		this.time = now;
		this.task.post(()->{
			model.tick(elapsed);
			this.task.post(this, 1);
		});
	}
}
