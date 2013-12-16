package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Demon extends Enemy {

	public Demon(Point p) throws SlickException {
		super(p);
		
		speed = 1;
		health = 10;
		damage = 30;

		initMoveAnimations("demon", 4);
		initAttackAnimations("demon");
	}

}
