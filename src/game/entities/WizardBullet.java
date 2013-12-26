package game.entities;

import game.GOType;
import game.Game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class WizardBullet extends Bullet{

	public WizardBullet(Point p, float vx, float vy, GOType type) throws SlickException {
		super(p, vx, vy, type);

		// init bounds
		bounds.setHeight(sprite.getHeight());
		bounds.setWidth(sprite.getWidth());
	}
}
