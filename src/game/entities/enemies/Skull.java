package game.entities.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Skull extends EMoveRandom{

	public Skull(Point p) throws SlickException {
		super(p);
		
		defaultSpeed = 4;
		speed = defaultSpeed;
		health = 1;
		damage = 8;

		initMoveAnimations("skull", 3);
		initAttackAnimations("skull");
		
		exp = 14;
		
		dieColors[0] = new Color(200, 200, 250);
		dieColors[1] = new Color(200, 200, 250);
	}
}
