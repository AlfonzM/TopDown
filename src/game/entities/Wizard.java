package game.entities;

import game.GOType;
import game.Play;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

/*
 * Wizard class
 */

public class Wizard extends Player{

	public Wizard(Input input, Point p) throws SlickException {
		super(input, p);
		
		initMoveAnimations();
		initAttackAnimations();
		Image[] imgs = new Image[1];
		imgs[0] = new Image("res/wizard/normalatk.png");
		
//		animation = new Animation(imgs, 100, true);
	}
	
	public void initAttackAnimations() throws SlickException{
		super.initAttackAnimations();	
	}
	
	// attack based on arrow keys
	public void attack(int bx, int by) throws SlickException{
		Play.objects.get(GOType.Bullet).add(new WizardBullet(pos, bx, by, GOType.Player));
		super.attack();
	}
}
