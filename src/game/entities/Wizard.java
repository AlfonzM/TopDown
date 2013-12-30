package game.entities;

import game.GOType;
import game.Play;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

/*
 * Wizard class
 */

public class Wizard extends Player{

	public Wizard(Input input, Point p) throws SlickException {
		super(input, p);
		
		initMoveAnimations("wizard", 4);
		initAttackAnimations("wizard");
		Image[] imgs = new Image[1];
		imgs[0] = new Image("res/wizard/normalatk.png");
		
//		animation = new Animation(imgs, 100, true);
	}
	
	@Override
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
//		Image[] downAtkImgs = www
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
	
//	@Override
//	public void initMoveAnimations(String s, int count) throws SlickException{
//		final int duration = 300/2;
//		
//		String classType = "wizard";
//		
//		Image[] leftImgs = new Image[4];
//		Image[] rightImgs = new Image[4];
//		
//		Image[] upImgs = new Image[3];
//		Image[] downImgs = new Image[3];
//		
//		for(int i = 0 ; i < count; i++){
//			leftImgs[i] = new Image("res/" + classType + "/left/" + (i+1) + ".png");
//			rightImgs[i] = new Image("res/" + classType + "/right/" + (i+1) + ".png");
//		}
//		
//		for(int i = 0; i < 3; i++){
//			upImgs[i] = new Image("res/" + classType + "/up/" + (i+1) + ".png");
//			downImgs[i] = new Image("res/" + classType + "/down/" + (i+1) + ".png");
//		}
//		
//		aLeft = new Animation(leftImgs, duration, true);
//		aRight = new Animation(rightImgs, duration, true);
////		aUp = new Animation(upImgs, duration, true);w
////		aDown= new Animation(downImgs, duration, true);
//		
//		animation = aRight;
//	}
	
	// attack based on arrow keys
	public void attack(int bx, int by) throws SlickException{
		Play.addBullet(new WizardBullet(pos, bx, by, GOType.Player));
		super.attack();
	}
	
	public Rectangle getNewBounds(){
//		return new Ellipse(pos.getX() + x, pos.getY() + y, bounds.getWidth(), bounds.getHeight());
		return new Rectangle(pos.getX() + move.x + 10, pos.getY() + move.y, bounds.getWidth() - 20, bounds.getHeight());
	}
	
	public Rectangle getNewXBounds(){
		return new Rectangle(pos.getX() + 10 + move.x * 2, pos.getY(), bounds.getWidth() - 20, bounds.getHeight());
	}
	
	public Rectangle getNewYBounds(){
		return new Rectangle(pos.getX() + 10, pos.getY() + move.y * 2, bounds.getWidth() - 20, bounds.getHeight());
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(pos.getX() + 10, pos.getY(), 69 - 10, 51);
	}
}
