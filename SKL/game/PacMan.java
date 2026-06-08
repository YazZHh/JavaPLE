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
			this.sprites = new BufferedImage[4][3];
			BufferedImage imgSprites = g.load("sprites.png");
			
			sprites[East][0] = imgSprites.getSubimage(1, 1, 13, 13);
			sprites[East][1] = imgSprites.getSubimage(17, 1, 13, 13);
			sprites[East][2] = imgSprites.getSubimage(33, 1, 13, 13);
			
			sprites[North][0] = imgSprites.getSubimage(1, 34, 13, 13);
			sprites[North][1] = imgSprites.getSubimage(17, 34, 13, 13);
			sprites[North][2] = imgSprites.getSubimage(33, 1, 13, 13);
			
			sprites[West][0] = imgSprites.getSubimage(1, 17, 13, 13);
			sprites[West][1] = imgSprites.getSubimage(17, 17, 13, 13);
			sprites[West][2] = imgSprites.getSubimage(33, 1, 13, 13);
			
			sprites[South][0] = imgSprites.getSubimage(1, 49, 13, 13);
			sprites[South][1] = imgSprites.getSubimage(17, 49, 13, 13);
			sprites[South][2] = imgSprites.getSubimage(33, 1, 13, 13);
		}
		
		@Override
		public void paint(Graphics g) {
			if (this.sprites == null)
				getSprites(g);
			if (PacMan.this.lSpeed().targetX_cm > 0)
				direction = East;
			else if (PacMan.this.lSpeed().targetX_cm < 0)
				direction = West;
			else if (PacMan.this.lSpeed().targetY_cm > 0)
				direction = South;
			else if (PacMan.this.lSpeed().targetY_cm < 0)
				direction = North;
//			g.drawImage(sprites[direction][index], (int) (PacMan.this.center().x_cm*Game.cmPerCell), (int) (PacMan.this.center().y_cm*Game.cmPerCell));
			g.drawImage(sprites[direction][index], this.toPixel(PacMan.this.center().x_cm), this.toPixel(PacMan.this.center().y_cm));
			index++;
			if (index > 2)
				index = 0;
		}
	}

}
