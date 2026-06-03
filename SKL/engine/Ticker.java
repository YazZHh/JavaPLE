package engine;

import game.Model;
import oop.tasks.Task;

public class Ticker {
	int ms;
	Model model;
	Task task;
	
	public Ticker(Task task, Model model, int ms) {
		this.task = task;
		this.model = model;
		this.ms = ms;
	}
}
