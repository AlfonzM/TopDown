package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Bat extends EMoveRandom{

	public Bat(Point p) throws SlickException {
		super(p);
		// TODO Auto-generated constructor stub
		
		defaultSpeed = 2.2f;
		speed = defaultSpeed;
	}
}