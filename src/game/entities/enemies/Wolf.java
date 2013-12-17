package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Wolf extends EMoveToPlayer{

	public Wolf(Point p) throws SlickException {
		super(p);

		speed = 2.9f;
		health = 2;
		
		atkDelay = 200;
		damage = 1;
		
		initMoveAnimations("wolf", 3);
		initAttackAnimations("wolf");
		
		exp = 15;
	}
}