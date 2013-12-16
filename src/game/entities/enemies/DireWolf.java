package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class DireWolf extends EMoveToPlayer{

	public DireWolf(Point p) throws SlickException {
		super(p);

		speed = 2.9f;
		health = 2;
		
		atkDelay = 300;
		damage = 2;
		
		initMoveAnimations("wolf", 3);
		initAttackAnimations("wolf");
		
		bounds.setWidth(50);
		bounds.setHeight(50);
	}
}