package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Orc extends EMoveToPlayer {

	public Orc(Point p) throws SlickException {
		super(p);
		
		speed = 2.2f;
		
		health = 3;
		atkDelay = 1000;
		damage = 6;
		
		initMoveAnimations("orc", 3);
		initAttackAnimations("orc");
	}

}
