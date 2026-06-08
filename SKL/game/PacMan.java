// == PAC MAN ==
package game;

import engine.Circle;
import engine.Entity;
import engine.Game;
import engine.ISU.Dimension;
import oop.graphics.BufferedImage;
import oop.graphics.Graphics;

public class PacMan extends Entity {

	// CONSTRUCTOR
	public PacMan() {
		super("PacMan");
		this.setSize(this.isu.new Dimension(Game.cmPerCell/2.0, Game.cmPerCell/2.0));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Circle(this.center(), Game.cmPerCell/4.0));
	}
	
	public class PacManAvatar extends Avatar{
		int index;

		public PacManAvatar() {
			super(PacMan.this);
			this.index = 0;
		}

		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[3];
			BufferedImage imgSprites = g.load("sprites.png");
			sprites[0] = imgSprites.getSubimage(3, 617, 61, 88);
			sprites[1] = imgSprites.getSubimage(65, 617, 81, 88);
			sprites[2] = imgSprites.getSubimage(153, 616, 89, 88);
		}
		
		@Override
		public void paint(Graphics g) {
			getSprites(g);
			g.drawImage(sprites[index], PacMan.this.position().x, PacMan.this.position().y);
			index++;
			if (index > 2)
				index = 0;
		}
	}

}
