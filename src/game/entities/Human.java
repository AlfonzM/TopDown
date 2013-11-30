package game.entities;

import game.Play;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.particles.ConfigurableEmitter;

/*
 * Components: unit, attack, attack animations
 */

public class Human extends Unit{
	
	// attacking component
	enum AttackType { melee, ranged };
	int damage;
	int atkDelay, atkCounter;
	boolean canAtk, isAttacking, isAnimatingAtk;
	
	Animation aAtkUp, aAtkDown, aAtkLeft, aAtkRight;
	
	// 4 directions animation
	Animation aUp, aDown, aLeft, aRight;
	
	public Human(Point p) throws SlickException{
		super(p);
		
		// Attack
		damage = 1;
		atkCounter = 0;
		atkDelay = 1000; // delay between attacks. lower = faster. in millisecs
		canAtk = true;
		isAttacking = false;
		isAnimatingAtk = false;
	}
	
	@Override
	public void render(Graphics g){
		updateAnimation();
		
		if(animation != null){
			if(animation == aAtkLeft){
				g.drawAnimation(animation, pos.getX() - 9, pos.getY());
			}
			else{
				System.out.println("asd");
				g.drawAnimation(animation, pos.getX(), pos.getY());
			}
		}
		
//		g.setColor(Color.red);
//		g.fill(getNewBounds(move.x, move.y));
//		
//		g.setColor(Color.white);
//		g.fill(getBounds());
	}
	
	@Override
	public void update(int delta) throws SlickException{
		// Atk component
		if(!canAtk){
			atkCounter += delta;
			if(atkCounter >= atkDelay)
				canAtk = true;
		}
		
		super.update(delta);
	}
	
	public void updateAnimation(){
		// not animate attack
		if(!isAnimatingAtk){
			switch(dir){
			case left: animation = aLeft; break;
			case right: animation = aRight; break;
			case up: animation = aUp; break;
			case down: animation = aDown; break;
			default: break;
			}
		}
		// animate attack
		else if(isAnimatingAtk){
			if(animation.isStopped()){
				isAnimatingAtk = false;
			}
		}
				
		if((move.x == 0 && move.y == 0) && !isAttacking){
			// idle
			animation.setCurrentFrame(0);
			animation.stop();
		}
		else{
			animation.start();
		}
	}
	
	public void attack() throws SlickException{
		switch (dir) {
		case left:
			aAtkLeft.restart();
			animation = aAtkLeft;
			break;
		case right:
			aAtkRight.restart();
			animation = aAtkRight;
			break;
		case up:
			aAtkUp.restart();
			animation = aAtkUp;
			break;
		case down:
			aAtkDown.restart();
			animation = aAtkDown;
			break;
		default:
			break;
		}
		isAnimatingAtk = true;
		
		atkCounter = 0;
		canAtk = false;
	}
	
	public void useAttack() throws SlickException{
		if(canAtk){
			attack();
		}
	}
	
	public void die() throws SlickException{
		super.die();
		ConfigurableEmitter e = Play.emitter.duplicate();
		e.setPosition(pos.getX(), pos.getY());
		Play.pSystem.addEmitter(e);
	}
	
	public void initAttackAnimations() throws SlickException{
		initAttackAnimations(this.getClass().toString().substring(20).toLowerCase());
	}
	
	public void initMoveAnimations() throws SlickException{
		initMoveAnimations(this.getClass().toString().substring(20).toLowerCase());
	}
	
	public void initAttackAnimations(String classType) throws SlickException{
		int duration = 150;
		if(atkDelay < 300){
			duration = atkDelay/2;
		}
		
		Image[] leftAtkImgs = 
			{ new Image("res/" + classType + "/attack/left/1.png"),
				new Image("res/" + classType + "/attack/left/2.png") };
		
		Image[] rightAtkImgs = 
			{ new Image("res/" + classType + "/attack/right/1.png"),
				new Image("res/" + classType + "/attack/right/2.png") };
		
		Image[] upAtkImgs = 
			{ new Image("res/" + classType + "/attack/up/1.png"),
				new Image("res/" + classType + "/attack/up/2.png") };
		
		Image[] downAtkImgs = 
			{ new Image("res/" + classType + "/attack/down/1.png"),
				new Image("res/" + classType + "/attack/down/2.png") };
		
		aAtkUp = new Animation(upAtkImgs, duration, true);
		aAtkLeft = new Animation(leftAtkImgs, duration, true);
		aAtkDown = new Animation(downAtkImgs, duration, true);
		aAtkRight = new Animation(rightAtkImgs, duration, true);
		
		aAtkLeft.setLooping(false);
		aAtkRight.setLooping(false);
		aAtkUp.setLooping(false);
		aAtkDown.setLooping(false);
	}
	
	public void initMoveAnimations(String classType) throws SlickException{
		final int duration = 300/2;
		
		Image[] leftImgs = 
			{ new Image("res/" + classType + "/left/1.png"),
				new Image("res/" + classType + "/left/2.png") };
		
		Image[] rightImgs = 
			{ new Image("res/" + classType + "/right/1.png"),
				new Image("res/" + classType + "/right/2.png") };
		
		Image[] upImgs = new Image[3];
		Image[] downImgs = new Image[3];
		
		for(int i = 0; i < 3; i++){
			upImgs[i] = new Image("res/" + classType + "/up/" + (i+1) + ".png");
			downImgs[i] = new Image("res/" + classType + "/down/" + (i+1) + ".png");
		}
		
		aLeft = new Animation(leftImgs, duration, true);
		aRight = new Animation(rightImgs, duration, true);
		aUp = new Animation(upImgs, duration, true);
		aDown= new Animation(downImgs, duration, true);
		
		animation = aRight;
	}
	
	public Point centerPos(){
		return new Point(pos.getX() + animation.getCurrentFrame().getWidth()/2,
						 pos.getY() + animation.getCurrentFrame().getHeight()/2);
	}

}
