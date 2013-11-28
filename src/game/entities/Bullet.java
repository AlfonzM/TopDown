package game.entities;

import game.GOType;
import game.Play;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Bullet extends Unit{

//	Dir direction;
	ArrayList<GameObject> targets;
	
	int range = 50, rangeCount = 0, damage = 1;
	
	int bWidth = 5,
	    bHeight = 5;
	
	Image sprite;
	
	public Bullet(Point p, int vx, int vy, GOType type) throws SlickException{
		super(p);
		speed = 5;
		
		bounds.setHeight(bHeight);
		bounds.setWidth(bWidth);
		
		if(type == GOType.Player){
			targets = Play.getEnemies();
		}
		else if(type == GOType.Enemy){
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
