package game;

import engine.BasicStunt;

public class Controller {
	private BasicStunt player;
	
	public Controller(BasicStunt player) {
		this.player = player;
	}
	
	public void left() {
		player.walk(180);
	}
	
	public void right() {
		player.walk(0);
	}
	
	public void up() {
		player.walk(270);
	}
	
	public void down() {
		player.walk(90);
	}
}
