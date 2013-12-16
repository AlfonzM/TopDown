package game.entities.enemies;

import game.Play;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class EMoveToPlayer extends Enemy{

	public EMoveToPlayer(Point p) throws SlickException {
		super(p);
		defaultSpeed = 2.4f;
		speed = defaultSpeed;
		health = 2;
	}
	
	public EMoveToPlayer(Point p, float speed) throws SlickException {
		super(p);

		this.speed = speed;
		
		updateTarget();
	}

	@Override
	public void move(int delta) throws SlickException{
		// ai to follow player
		if(!Play.p.invisible){
			super.move(delta);
		}
		updateTarget();
	}
	
	public void updateTarget(){
		// set target to player's position
		targetPoint.setX(Play.p.pos.getX());
		targetPoint.setY(Play.p.pos.getY());
		recalculateVector(targetPoint.getX(), targetPoint.getY());
	}

}