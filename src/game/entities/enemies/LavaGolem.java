package game.entities.enemies;

import game.ScreenShake;
import game.Sounds;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class LavaGolem extends EMoveToPlayer {

	public LavaGolem(Point p) throws SlickException {
		super(p);
		
		speed = 1.5f;
		health = 15;
		damage = 25;
		
		atkDelay = 3000;

		initMoveAnimations("lavagolem", 5);
		initAttackAnimations("lavagolem");
		
		exp = 30;
	}
	
	@Override
	public void attack() throws SlickException{
		super.attack();
		Sounds.stomp2.play();
		ScreenShake.shake();
	}

}
