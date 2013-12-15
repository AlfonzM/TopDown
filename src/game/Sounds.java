package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound die;
	
	public Sounds() throws SlickException{
		die = new Sound("res/sounds/die.wav");
	}
}
