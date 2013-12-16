package game.entities.enemies;

import game.GOType;
import game.Play;
import game.Sounds;
import game.entities.Bullet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Point;

public class Eyeball extends EMoveToPlayer{
	
	Ellipse rangeBounds;
	int range;
	
	boolean inRange;

	public Eyeball(Point p) throws SlickException {
		super(p);
		atkType = AttackType.ranged;
		atkDelay = 2000;
		
		range = 150;
		inRange = false;
		rangeBounds = new Ellipse(Play.p.pos.getX(), Play.p.pos.getY(), range, range);
		
		initMoveAnimations("eyeball");
		initAttackAnimations("eyeball");
	}
	
	@Override
	public void move(int delta) throws SlickException{
		if(inRange){
			useAttack();
		}
		else{
			if(!canMoveX){
				if(pos.getY() < Play.p.pos.getY()){
					move.y = speed;
				}
				else{
					move.y = -speed;					
				}
			}
			if(!canMoveY){
				if(pos.getX() < Play.p.pos.getX()){
					move.x = speed;
				}
				else{
					move.x = -speed;					
				}
			}
		}
		
		super.move(delta);
		
		updateTarget();
	}
	
	@Override
	public void attack() throws SlickException{
		if(!isStunned && !Play.p.invisible){
			Bullet b = new Bullet(pos, 1, 1, GOType.Enemy);
			b.damage = 1000;
			b.speed += 1;
			b.range = 100;
			Play.addBullet(b);
			super.attack();			
			
			Sounds.shoot3.play();
		}
	}

	@Override
	public void updateTarget(){
		rangeBounds.setX(Play.p.pos.getX() - range + Play.p.animation.getCurrentFrame().getWidth()/2);
		rangeBounds.setY(Play.p.pos.getY() - range + Play.p.animation.getCurrentFrame().getWidth()/2);
		
		if(rangeBounds.contains(pos)){
			inRange = true;
		}
		else{
			inRange = false;
		}
		
			super.updateTarget();
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
	}
}
