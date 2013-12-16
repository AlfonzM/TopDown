package game.entities;

import game.GOType;
import game.Game;
import game.Play;
import game.Vectors;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Bullet extends Unit{

//	Dir direction;
	ArrayList<GameObject> targets;
	
	public int range = 50;

	int rangeCount = 0;

	public int damage = 1;
	
	int bWidth = 5,
	    bHeight = 5;
	
	Image sprite;
	
	public Bullet(Point p, float vx, float vy, GOType userType) throws SlickException{
		super(p);
		speed = 5;
		
		bounds.setHeight(bHeight);
		bounds.setWidth(bWidth);
		
		if(userType == GOType.Player){
			targets = Play.getEnemies();
			move.x = vx;
			move.y = vy;
			
			sprite = new Image("res/wizard/normalatk.png").getSubImage((int) ++vx *Game.TS, (int) ++vy * Game.TS, Game.TS, Game.TS);
		}
		else if(userType == GOType.Enemy){
			targets = Play.objects.get(GOType.Player);
			sprite = new Image("res/wizard/normalatk2.png").getSubImage(1 * Game.TS, 0, Game.TS, Game.TS);
			recalculateVector(Play.p.pos.getX(), Play.p.pos.getY());
			
			float xDistance = (float) Play.p.pos.getX() - p.getX();
			float yDistance = (float) Play.p.pos.getY() - p.getY();
			
			float angleToTurn = (float) Math.toDegrees(Math.atan2(-yDistance, -xDistance));
			
			sprite.rotate(angleToTurn - 90);
		}
		
		health = 1;
	}
	
	@Override
	public void render(Graphics g){
		g.drawImage(sprite, pos.getX(), pos.getY());
	}
	
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		
		if(rangeCount <= range){
			rangeCount++;
		}
		else{
			isAlive = false;
		}
		
		for(int i = 0 ; isAlive && i < targets.size(); i++){
			if(getBounds().intersects(targets.get(i).getBounds())){
				((Unit) targets.get(i)).takeDamage(damage);
				takeDamage(1);
			}
		}
	}

	public float recalculateVector(float newX, float newY){
		float rad = Vectors.getRad(pos.getX(), newX, pos.getY(), newY);
		move.x = (float) Math.sin(rad) * speed;
		move.y = (float) Math.cos(rad) * speed;
		
		return rad;
	}
}
