package game.entities;

import game.Play;
import game.Sounds;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
	public enum AttackType { melee, ranged };
	public int damage;
	public int atkDelay;
	public int atkCounter;
	protected AttackType atkType;
	public boolean canAtk, isAttacking, isAnimatingAtk;
	
//	public Animation aAtkUp, aAtkDown, aAtkLeft, aAtkRight;
	
	// 4 directions animation
	protected Animation aLeft;
	protected Animation aRight;
//	protected Animation aUp, aDown;
	
	public float defaultSpeed;
	public boolean isHit;
	
	// for particle
	public Color[] dieColors;
	Color[] sparkColors;
	
	public Human(Point p) throws SlickException{
		super(p);
		new Sounds();
		
		// Attack
		damage = 1;
		atkCounter = 0;
		atkDelay = 1000; // delay between attacks. lower = faster. in millisecs
		canAtk = true;
		isAttacking = false;
		isAnimatingAtk = false;
		atkType = AttackType.melee;
		
		isHit = false;
		
		dieColors = new Color[2];
		dieColors[0] = new Color(255, 255 * 1, 255 * 1);
		dieColors[1] = new Color(255, 255 * 1, 255 * 1);
		
		sparkColors = new Color[2];
		sparkColors[0] = new Color(255, 255 * 1, 255 * 1);
		sparkColors[1] = new Color(255, 255 * 1, 255 * 1);
	}
	
	@Override
	public void render(Graphics g){
		updateAnimation();
		
		if(animation != null){
			if(isHit){
				Image i = animation.getCurrentFrame();
				i.drawFlash(pos.getX(), pos.getY());
				isHit = false;
			}
			else{
				g.drawAnimation(animation, pos.getX(), pos.getY());	
			}
//			if(animation == aAtkLeft){
//				g.drawAnimation(animation, pos.getX() - 9, pos.getY());
//			}
//			else{
//				g.drawAnimation(animation, pos.getX(), pos.getY());
//			}
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
			case up:
				if(move.x < 0)
					animation = aLeft;
				else
					animation = aRight;
				break;
			case down:
				if(move.x < 0)
					animation = aLeft;
				else
					animation = aRight;
				break;
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
//		switch (dir) {
//		case left:
//			aAtkLeft.restart();
//			animation = aAtkLeft;
//			break;
//		case right:
//			aAtkRight.restart();
//			animation = aAtkRight;
//			break;
//		case up:
//			aAtkUp.restart();
//			animation = aAtkUp;
//			break;
//		case down:
//			aAtkDown.restart();
//			animation = aAtkDown;
//			break;
//		default:
//			break;
//		}
//		isAnimatingAtk = true;
		
		atkCounter = 0;
		canAtk = false;
	}
	
	public void useAttack() throws SlickException{
		if(canAtk && Play.p.isAlive){
			attack();
		}
	}

	@Override
	public void takeDamage(int i){
		super.takeDamage(i);
		
		if(isAlive){
			ConfigurableEmitter e = Play.emitterSpark.duplicate();
			e.addColorPoint(0, dieColors[0]);
			e.addColorPoint(1, dieColors[1]);
			e.setPosition(pos.getX() + getBounds().getWidth()/2, pos.getY() + 5);
			Play.pSystem.addEmitter(e);
			Sounds.hit.play();
			
			isHit = true;
		}
	}
	
	@Override
	public void die() throws SlickException{
		super.die();
		
		ConfigurableEmitter e = Play.emitterUnit.duplicate();
		for(int i = 0; i < 2; i++){
			e.addColorPoint(i, dieColors[i]);
		}
		e.setPosition(pos.getX(), pos.getY() + getBounds().getHeight()/2);
		Play.pSystem.addEmitter(e);
		
		Sounds.die.play();
	}
	
	public void initAttackAnimations(String classType) throws SlickException{
//		int duration = 150;
//		if(atkDelay < 300){
//			duration = atkDelay/2;
//		}
//		
//		Image[] leftAtkImgs = 
//			{ new Image("res/" + classType + "/attack/left/1.png"),
//				new Image("res/" + classType + "/attack/left/2.png") };
//		
//		Image[] rightAtkImgs = 
//			{ new Image("res/" + classType + "/attack/right/1.png"),
//				new Image("res/" + classType + "/attack/right/2.png") };
//		
//		Image[] upAtkImgs = 
//			{ new Image("res/" + classType + "/attack/up/1.png"),
//				new Image("res/" + classType + "/attack/up/2.png") };
//		
//		Image[] downAtkImgs = 
//			{ new Image("res/" + classType + "/attack/down/1.png"),
//				new Image("res/" + classType + "/attack/down/2.png") };
//		
//		aAtkUp = new Animation(upAtkImgs, duration, true);
//		aAtkLeft = new Animation(leftAtkImgs, duration, true);
//		aAtkDown = new Animation(downAtkImgs, duration, true);
//		aAtkRight = new Animation(rightAtkImgs, duration, true);
//		
//		aAtkLeft.setLooping(false);
//		aAtkRight.setLooping(false);
//		aAtkUp.setLooping(false);
//		aAtkDown.setLooping(false);
	}
	
	public void initMoveAnimations(String classType, int count) throws SlickException{
		int duration = 300/2;
		
		if(classType == "wolf"){
			duration = 100;
		}
		else if(classType == "lupa"){
			duration = 100;
		}
		
		Image[] leftImgs = new Image[count];
		Image[] rightImgs = new Image[count];
		
//		Image[] upImgs = new Image[3];
//		Image[] downImgs = new Image[3];
		
		for(int i = 0 ; i < count; i++){
			leftImgs[i] = new Image("res/" + classType + "/left/" + (i+1) + ".png");
			rightImgs[i] = new Image("res/" + classType + "/right/" + (i+1) + ".png");
		}
		
//		for(int i = 0; i < 3; i++){
//			upImgs[i] = new Image("res/" + classType + "/up/" + (i+1) + ".png");
//			downImgs[i] = new Image("res/" + classType + "/down/" + (i+1) + ".png");
//		}
		
		aLeft = new Animation(leftImgs, duration, true);
		aRight = new Animation(rightImgs, duration, true);
//		aUp = new Animation(upImgs, duration, true);
//		aDown= new Animation(downImgs, duration, true);
		
		animation = aRight;
		
		bounds.setWidth(animation.getCurrentFrame().getWidth());
		bounds.setHeight(animation.getCurrentFrame().getHeight());
	}
	
	public Point centerPos(){
		return new Point(pos.getX() + animation.getCurrentFrame().getWidth()/2,
						 pos.getY() + animation.getCurrentFrame().getHeight()/2);
	}

}
