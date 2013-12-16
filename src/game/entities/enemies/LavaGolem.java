package game.entities.enemies;

import game.ScreenShake;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class LavaGolem extends EMoveToPlayer {

	public LavaGolem(Point p) throws SlickException {
		super(p);
		
		speed = 2;
		health = 10;
		damage = 30;

		initMoveAnimations("lavagolem", 5);
		initAttackAnimations("lavagolem");
	}
	
	@Override
	public void attack() throws SlickException{
		super.attack();
		ScreenShake.shake();
	}

}
