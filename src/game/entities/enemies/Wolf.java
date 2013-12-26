package game.entities.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Wolf extends EMoveToPlayer{

	public Wolf(Point p) throws SlickException {
		super(p);

		defaultSpeed = 2.9f;
		speed = defaultSpeed;
		
		health = 2;
		
		atkDelay = 200;
		damage = 1;
		
		initMoveAnimations("wolf", 3);
		initAttackAnimations("wolf");
		
		exp = 15;
		
		dieColors[0] = new Color(80, 80, 80);
		dieColors[1] = new Color(0, 0, 0);
	}
}