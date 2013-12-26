package game.entities.enemies;

import game.ScreenShake;
import game.Sounds;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class LavaGolem extends EMoveToPlayer {

	public LavaGolem(Point p) throws SlickException {
		super(p);
		
		defaultSpeed = 1.5f;
		speed = defaultSpeed;
		health = 15;
		damage = 25;
		
		atkDelay = 3000;

		initMoveAnimations("lavagolem", 5);
		initAttackAnimations("lavagolem");
		
		exp = 30;
		
		dieColors[0] = new Color(40, 0, 150);
		dieColors[1] = new Color(40, 0, 250);
	}
	
	@Override
	public void attack() throws SlickException{
		super.attack();
		Sounds.stomp2.play();
		ScreenShake.shake();
	}
	
	@Override
	public void die() throws SlickException{
		super.die();
		ScreenShake.shake();
	}

}
