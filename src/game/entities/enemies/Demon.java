package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Demon extends EMoveToPlayer {

	public Demon(Point p) throws SlickException {
		super(p);
		
		speed = 2.5f;
		health = 4;
		damage = 4;

		initMoveAnimations("demon", 4);
		initAttackAnimations("demon");
		
		exp = 12;
	}

}
