package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Bat extends EMoveRandom{

	public Bat(Point p) throws SlickException {
		super(p);
		// TODO Auto-generated constructor stub
		
		defaultSpeed = 2.5f;
		damage = 2;
		speed = defaultSpeed;
		
		initMoveAnimations("bat", 3);
		initAttackAnimations("bat");
	}
}