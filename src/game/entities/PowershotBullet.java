package game.entities;

import game.GOType;
import game.Play;
import game.entities.enemies.Enemy;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class PowershotBullet extends WizardBullet{

	public PowershotBullet(Point p, float vx, float vy, GOType type) throws SlickException {
		super(p, vx, vy, type);
		
		damage = 5;
		health = 10000000;
		range = 10000;
		
		targets = Play.getEnemies();
	}
	
	@Override
	public void update(int delta) throws SlickException{
		move(delta);
		
		if(rangeCount <= range){
			rangeCount++;
		}
		else{
			isAlive = false;
		}
		
		for(int i = 0 ; isAlive && i < targets.size(); i++){
			Enemy e = (Enemy) targets.get(i);
			if(getBounds().intersects(targets.get(i).getBounds())){
				e.hitPowershot(damage);
					
				takeDamage(1);	
			}
		}		
	}

}
