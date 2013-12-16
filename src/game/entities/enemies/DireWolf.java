package game.entities.enemies;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class DireWolf extends EMoveToPlayer{

	public DireWolf(Point p) throws SlickException {
		super(p, 3);

		initMoveAnimations("enemy2");
		initAttackAnimations("enemy2");
		
		bounds.setWidth(50);
		bounds.setHeight(50);
	}
	
}
