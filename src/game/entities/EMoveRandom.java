package game.entities;
import game.Game;
import game.Play;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class EMoveRandom extends Enemy{
	
	int currentFrames, maxFrames;
	
	public EMoveRandom(Point p) throws SlickException {
		super(p);
		speed = 1;
		getNewPoint();
		
		atkDelay = 100;
	}
	
	@Override
	public void move(int delta) throws SlickException{
//		System.out.println(""+pos.getX() + ", " + pos.getY());
		if(currentFrames <= maxFrames &&
			Math.floor(pos.getX()) != Math.floor(targetPoint.getX()) &&
			Math.floor(pos.getY()) != Math.floor(targetPoint.getY())){
			super.move(delta);
			currentFrames++;
		}
		else{
			getNewPoint();
		}
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		if(isAnimatingAtk){
			if(animation.isStopped())
				moveAwayFromPlayer();
		}
	}
	
	public void attack() throws SlickException{
		super.attack();
	}
	
	public void getNewPoint(){
		currentFrames = 0;
		maxFrames = r.nextInt(100) + 50;
		
		int newX = r.nextInt(Game.GWIDTH);
		int newY = r.nextInt(Game.GHEIGHT);
		
		recalculateVector(newX, newY);
		
		targetPoint.setX(newX);
		targetPoint.setY(newY);
		
//		System.out.println("New target: " + targetPoint.getX() + ", " + targetPoint.getY());
//		System.out.println("New velocity: " + move.x + ", " + move.y);
	}
	
	public void moveAwayFromPlayer(){
		if(Play.p.pos.getX() > pos.getX()){
			move.x = -speed;
		}
		else{
			move.x = speed;
		}
		
		if(Play.p.pos.getY() > pos.getY()){
			move.y = -speed;
		}
		else{
			move.y = speed;
		}
	}
	
	@Override
	public void die() throws SlickException {
		isAlive = false;
	}
}