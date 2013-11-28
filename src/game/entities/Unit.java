package game.entities;
import game.Dir;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

/*
 * Components: movement, health, collision detection, animation
 */

public class Unit extends GameObject{
	
	// movement component
	Vector2f move;
	boolean canMoveX, canMoveY;
	public float speed;
	Dir dir;
	
	// health component
	int health;
	
	// Animation
	Animation animation;
	
	public Unit(Point p) throws SlickException{		
		// Position & isAlive at super
		super(p);
		
		// Collision
		bounds = new Rectangle(pos.getX(), pos.getY(), 0, 0);
		
		// Move
		move = new Vector2f(0, 0);
		speed = 0;
		dir = Dir.right;
		canMoveX = true;
		canMoveY = true;
		
		// Health
		health = 1;
		
		// Render
		sprite = new Image("res/sprite.png");
		
		bounds.setHeight(sprite.getHeight());
		bounds.setWidth(sprite.getWidth());
	}
	
	@Override
	public void render(Graphics g){
		
		if(animation != null){
			g.drawAnimation(animation, pos.getX(), pos.getY());
		}
	}

	@Override
	public void update(int delta) throws SlickException {
		// handle movement
		move(delta);
		
		// Update Direction
		updateDirection();
	}
	
	public void move(int delta) throws SlickException{
		move.normalise();
		
		if(canMoveX)
			pos.setX(pos.getX() + (move.x * speed));
		if(canMoveY)
			pos.setY(pos.getY() + (move.y * speed));
	}
	
	public void takeDamage(int dmg){
		health -= dmg;
		
		if(health <= 0){
			try {
				die();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	// update dir
	public void updateDirection(){
		if(move.y > 0){
			// down
			dir = Dir.down;
		}
		else if(move.y < 0){
			// up
			dir = Dir.up;
		}
		
		if(move.x < 0){
			// left
			dir = Dir.left;
		}
		else if(move.x > 0){
			// right
			dir = Dir.right;
		}
		
//		if(move.x < 0){
//			if(Math.abs(move.y) > Math.abs(move.x)){
//				if(move.y > 0)
//					dir = Dir.down;
//				else
//					dir = Dir.up;
//			}
//			else
//				dir = Dir.left;
//		}
//		else if(move.x > 0){
//			dir = Dir.right;
//		}
//		else if(move.y < 0){
//			dir = Dir.up;
//		}
//		else if(move.y > 0){
//			dir = Dir.down;
//		}
	}
	
	public void die() throws SlickException{
		isAlive = false;
	}
	
	public Rectangle getNewBounds(){
//		return new Ellipse(pos.getX() + x, pos.getY() + y, bounds.getWidth(), bounds.getHeight());
		return new Rectangle(pos.getX() + move.x, pos.getY() + move.y, bounds.getWidth(), bounds.getHeight());
	}
	
	public Rectangle getNewXBounds(){
		return new Rectangle(pos.getX() + move.x, pos.getY(), bounds.getWidth(), bounds.getHeight());
	}
	
	public Rectangle getNewYBounds(){
		return new Rectangle(pos.getX(), pos.getY() + move.y, bounds.getWidth(), bounds.getHeight());
	}
}
