package game.entities.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Orc extends EMoveToPlayer {

	public Orc(Point p) throws SlickException {
		super(p);
		
		defaultSpeed = 2.2f;
		speed = defaultSpeed;
		
		health = 3;
		atkDelay = 300;
		damage = 6;
		
		initMoveAnimations("orc", 3);
		initAttackAnimations("orc");
		
		exp = 12;
		
		dieColors[0] = new Color(0, 100, 20);
		dieColors[1] = new Color(0, 150, 20);
	}

}
