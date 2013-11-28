package game.entities;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

/*
 * Components: position, renderable, AABB
 */

public abstract class GameObject {
	Point pos;
	Image sprite;
	public boolean isAlive;
	
	Rectangle bounds;	
	
	public GameObject(Point p){
		pos = new Point(p.getX(), p.getY());
		isAlive = true;
	}
	
	public abstract void update(int delta) throws SlickException;
	
	public abstract void render(Graphics g);
	
	public Rectangle getBounds(){
		return new Rectangle(pos.getX(), pos.getY(), bounds.getWidth(), bounds.getHeight());
	}
}