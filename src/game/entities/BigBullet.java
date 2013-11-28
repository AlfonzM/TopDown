package game.entities;

import game.GOType;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;

public class BigBullet extends Bullet{

	public BigBullet(Point p, int vx, int vy, GOType type) throws SlickException {
		super(p, vx, vy, type);
		bWidth = 20;
		bHeight = 10;
	}

	@Override
	public void render(Graphics g){
		g.fill(new Circle(pos.getX(), pos.getY(), bWidth));
	}
}
