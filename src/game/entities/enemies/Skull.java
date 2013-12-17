package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Skull extends EMoveRandom{

	public Skull(Point p) throws SlickException {
		super(p);
		
		speed = 4;
		health = 1;
		damage = 8;

		initMoveAnimations("skull", 3);
		initAttackAnimations("skull");
		
		exp = 14;
	}
}
