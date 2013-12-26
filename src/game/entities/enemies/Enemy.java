
package game.entities.enemies;

import game.Dir;
import game.Fonts;
import game.Play;
import game.Sounds;
import game.Vectors;
import game.entities.GameObject;
import game.entities.GameText;
import game.entities.Human;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

public class Enemy extends Human{
	int exp;
		
	Random r;
	Point targetPoint;
	
	public boolean hitByPowershot;
	
	boolean isStunned;
	int stunDuration;

	public Enemy(Point p) throws SlickException {
		super(p);
		r = new Random();
		targetPoint = new Point(0, 0);
		
		speed = 1;
		
		atkDelay = 500;
		
		exp = 10;
		
		// stat effects
		isStunned = false;
		hitByPowershot = false;
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		if(isStunned){
			atkCounter -= delta;
		}
		
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
			canMoveX = false;
			atk = true;	
		}
		
		if(getNewYBounds().intersects(Play.p.getNewYBounds())){
			canMoveY = false;
			atk = true;
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
		if(!isStunned){
			super.attack();
			
			if(atkType == AttackType.melee){
				Play.p.takeDamage(damage);
			}
		}
	}
	
	@Override
	public void die() throws SlickException {
		super.die();
		
		Random r = new Random();
		if(r.nextBoolean())
			Play.addExpDrop(pos, exp);
		else{
			Play.addGoldDrop(pos,r.nextInt(10)+10);
		}
		
		Play.enemiesKilled++;
		Sounds.die.play();
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
	
	@Override
	public void hitPowershot(int dmg){
		if(!hitByPowershot){
			takeDamage(dmg);
			hitByPowershot = true;
			System.out.println(dmg + " " + hitByPowershot);
		}
	}

	public void stun(int duration) {
		isStunned = true;
		speed = 0;
		stunDuration = duration;
		
		new GameText("Stun", pos, 80, Color.orange, Fonts.font16);
	}
}