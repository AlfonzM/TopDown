
package game.entities;

import game.Dir;
import game.Game;
import game.Play;
import game.StatusEffect;
import game.Vectors;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Enemy extends Human{
	int exp;
		
	Random r;
	Point targetPoint;
	
	boolean isStunned;
	int stunDuration;

	public Enemy(Point p) throws SlickException {
		super(p);
		r = new Random();
		targetPoint = new Point(0, 0);
		
		speed = 1;
		
		atkDelay = 500;
		
		initMoveAnimations("enemy1");
		initAttackAnimations("enemy1");
		
		exp = 10;
		
		// stat effects
		isStunned = false;
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		updateStatusEffects(delta);
	}

	@Override
	public void render(Graphics g){
		super.render(g);
//		g.fill(getBounds());
	}
	
	@Override
	public void move(int delta) throws SlickException{
		canMoveX = true;
		canMoveY = true;
		
		// Collision with other mobs and player
		for(GameObject go : Play.getEnemies()){
			if(this != go && getNewXBounds().intersects(go.getBounds())){
				canMoveX = false;
			}
			
			if(this != go && getNewYBounds().intersects(go.getBounds())){
				canMoveY = false;
			}
		}
		
		// while at entrance gates
//		if(pos.getY() > 85 && pos.getY() < 185 && pos.getX() < 20){
//			canMoveY = false;
//		}
//		if((pos.getX() < 305 || pos.getX() > 417 - Game.TS) && pos.getY() < 95){
//			canMoveX = false;
//		}
		
//		if(!canMoveX && canMoveY){ // temporary solution, move down if cannot move x
//			move.y -= move.x;
//		}
//		
//		if(!canMoveY && canMoveX){
//			move.x += move.y;
//		}
		
		checkCollisionWithPlayer();
		
		super.move(delta);
	}
	
	@Override
	public void updateDirection(){
		if(Math.abs(move.y) > Math.abs(move.x)){
			if(move.y > 0)
				dir = Dir.down;
			else
				dir = Dir.up;
		}
		else if(move.x < 0)
			dir = Dir.left;
		else if(move.x > 0)
			dir = Dir.right;
	}
	
	public void checkCollisionWithPlayer() throws SlickException{
//		System.out.println(move.x + " " + move.y);
		
		boolean atk = false;
		
		if(getNewXBounds().intersects(Play.p.getNewXBounds())){
			if(Play.p.isDashing != true){
				canMoveX = false;
				atk = true;	
			}
			else{
				this.takeDamage(Play.p.damage);
			}
		}
		
		if(getNewYBounds().intersects(Play.p.getNewYBounds())){
			if(Play.p.isDashing != true){
				canMoveY = false;
				atk = true;
			}
			else{
				this.takeDamage(Play.p.damage);
			}
		}
		
		if(atk){
			useAttack();
			isAttacking = true;
		}
		else{
			isAttacking = false;
		}
	}
	
	public void attack() throws SlickException{
		super.attack();
		Play.p.takeDamage(damage);
	}
	
	@Override
	public void die() throws SlickException {
		super.die();
		
		Random r = new Random();
		if(r.nextBoolean())
			Play.addExpDrop(pos, exp);
		else{
			Play.addGoldDrop(pos, 10);
		}
	}
	
	public void recalculateVector(float newX, float newY){
		float rad = Vectors.getRad(pos.getX(), newX, pos.getY(), newY);
		move.x = (float) Math.sin(rad) * speed;
		move.y = (float) Math.cos(rad) * speed;
	}
	
	private void updateStatusEffects(int delta) {
		if(isStunned){
			stunDuration -= delta;
			
			if(stunDuration < 0){
				isStunned = false;
				speed = defaultSpeed;
			}
		}
	}

	public void stun(int duration) {
		isStunned = true;
		speed = 0;
		stunDuration = duration;
	}
}