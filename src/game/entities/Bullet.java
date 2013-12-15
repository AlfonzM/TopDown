package game.entities;

import game.GOType;
import game.Play;

import java.util.ArrayList;

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
		}
		else if(userType == GOType.Enemy){
			targets = Play.objects.get(GOType.Player);
		}
		
		health = 1;
		
		move.x = vx;
		move.y = vy;
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
}
