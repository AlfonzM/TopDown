package game.entities;

import game.GOType;
import game.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class WizardBullet extends Bullet{

	public WizardBullet(Point p, int vx, int vy, GOType type) throws SlickException {
		super(p, vx, vy, type);

		// init sprite
		sprite = new Image("res/wizard/normalatk.png").getSubImage(++vx*Game.TS, ++vy*Game.TS, Game.TS, Game.TS);
		// init bounds
		
		bounds.setHeight(sprite.getHeight());
		bounds.setWidth(sprite.getWidth());
	}

	@Override
	public void render(Graphics g){
//		g.fill(getBounds());
		g.drawImage(sprite, pos.getX(), pos.getY());
	}
}
