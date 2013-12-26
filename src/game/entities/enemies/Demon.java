package game.entities.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Demon extends EMoveToPlayer {

	public Demon(Point p) throws SlickException {
		super(p);
		
		defaultSpeed = 2.5f;
		speed = defaultSpeed;
		health = 4;
		damage = 4;

		initMoveAnimations("demon", 4);
		initAttackAnimations("demon");
		
		exp = 12;
		
		dieColors[0] = new Color(180, 0, 0);
		dieColors[1] = new Color(250, 0, 0);
	}

}
