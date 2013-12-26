package game.entities.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Bat extends EMoveRandom{

	public Bat(Point p) throws SlickException {
		super(p);
		// TODO Auto-generated constructor stub
		
		defaultSpeed = 2.6f;
		damage = 2;
		speed = defaultSpeed;
		
		initMoveAnimations("bat", 3);
		initAttackAnimations("bat");
		
		exp = 8;
		
		dieColors[0] = new Color(80, 80, 80);
		dieColors[1] = new Color(0, 0, 0);
	}
}