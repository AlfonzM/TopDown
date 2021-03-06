package game.entities.enemies;
import game.Game;
import game.Play;
import game.entities.GameObject;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class EMoveRandom extends Enemy{
	
	int currentFrames, maxFrames;
	
	public EMoveRandom(Point p) throws SlickException {
		super(p);
		defaultSpeed = 2.2f;
		speed = defaultSpeed;
		getNewPoint();
		
		atkDelay = 1000;
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
		

		// Collision with other mobs and player
		for(GameObject go : Play.getEnemies()){
			if(this != go && getNewXBounds().intersects(go.getBounds())){
				getNewPoint();
			}
			
			if(this != go && getNewYBounds().intersects(go.getBounds())){
				getNewPoint();
			}
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
		moveAwayFromPlayer();
	}
	
	public void getNewPoint(){
		currentFrames = 0;
		maxFrames = r.nextInt(100) + 50;
		
		float newX = r.nextInt(Game.MWIDTH - 20 - (int) getBounds().getWidth()) + 20;
		float newY = r.nextInt(Game.MHEIGHT - 40 - 95) + 95;
		
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
}