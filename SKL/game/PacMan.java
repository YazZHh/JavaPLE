// == PAC MAN ==
package game;

import engine.Circle;
import engine.Entity;
import engine.Game;
import engine.ISU.Dimension;
import oop.graphics.BufferedImage;
import oop.graphics.Canvas;
import oop.graphics.Graphics;

public class PacMan extends Entity {

	public static final double PacManSpeed = 10*Main.windowScale;
	
	private PacManAvatar self;

	// CONSTRUCTOR
	public PacMan() {
		super("PacMan");
		this.setSize(this.isu.new Dimension(Game.cmPerCell/2.0, Game.cmPerCell/2.0));
	}

	// === Task COLLISION ===
	public void setBounding() {
		this.bounding().add(new Circle(this.center(), Game.cmPerCell/4.0));
	}
	
	public PacManAvatar avatar() {
		return self;
	}
	
	public class PacManAvatar extends Avatar{
		
		public static final int spriteSize = 13;
		
		int index, indChg;

		public PacManAvatar() {
			super(PacMan.this);
			PacMan.this.self = this;
			this.index = 0;
			this.indChg = 0;
		}

		public void getSprites(Graphics g) {
			this.sprites = new BufferedImage[4][3];
			BufferedImage imgSprites = g.load("sprites.png");
			
			sprites[East][0] = imgSprites.getSubimage(1, 1, spriteSize, spriteSize);
			sprites[East][1] = imgSprites.getSubimage(17, 1, spriteSize, spriteSize);
			sprites[East][2] = imgSprites.getSubimage(33, 1, spriteSize, spriteSize);
			
			sprites[North][0] = imgSprites.getSubimage(1, 33, spriteSize, spriteSize);
			sprites[North][1] = imgSprites.getSubimage(17, 33, spriteSize, spriteSize);
			sprites[North][2] = imgSprites.getSubimage(33, 1, spriteSize, spriteSize);
			
			sprites[West][0] = imgSprites.getSubimage(1, 17, spriteSize, spriteSize);
			sprites[West][1] = imgSprites.getSubimage(17, 17, spriteSize, spriteSize);
			sprites[West][2] = imgSprites.getSubimage(33, 1, spriteSize, spriteSize);
			
			sprites[South][0] = imgSprites.getSubimage(1, 49, spriteSize, spriteSize);
			sprites[South][1] = imgSprites.getSubimage(17, 49, spriteSize, spriteSize);
			sprites[South][2] = imgSprites.getSubimage(33, 1, spriteSize, spriteSize);
		}
		
		@Override
		public void paint(Graphics g) {
		    if (this.sprites == null)
		        getSprites(g);
		        
		    if (PacMan.this.lSpeed().targetX_cm > 0)
		        direction = East;
		    if (PacMan.this.lSpeed().targetX_cm < 0)
		        direction = West;
		    if (PacMan.this.lSpeed().targetY_cm > 0)
		        direction = South;
		    if (PacMan.this.lSpeed().targetY_cm < 0)
		        direction = North;
		    
		    PacMan.this.show(System.out);
		        
		    int posX = this.toPixel(PacMan.this.center().x_cm - (PacMan.this.size.x() / 2.0));
		    int posY = this.toPixel(PacMan.this.center().y_cm - (PacMan.this.size.y() / 2.0));
		    g.drawImage(sprites[direction][index], posX, posY, (int) (spriteSize*Main.windowScale), (int) (spriteSize*Main.windowScale));
		    
		    if (indChg == 0)
		    	index++;
		    if (index > 2)
		    	index = 0;
		    indChg++;
		    if (indChg > 4)
		    	indChg = 0;
		}
	}
}
