package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound coin, die, exp, levelup, powershot, shoot, shoot2, stomp, stomp2, windwalk;
	public static Sound hit, shoot3;
	
	public Sounds() throws SlickException{
		die = new Sound("res/sounds/die.wav");
		coin = new Sound("res/sounds/coin.wav");
		exp = new Sound("res/sounds/exp.wav");
		levelup = new Sound("res/sounds/levelup.wav");
		powershot = new Sound("res/sounds/powershot.wav");
		shoot = new Sound("res/sounds/shoot.wav");
		shoot2 = new Sound("res/sounds/shoot1.wav");
		shoot3 = new Sound("res/sounds/shoot3.wav");
		stomp = new Sound("res/sounds/stomp.wav");
		stomp2 = new Sound("res/sounds/stomp2.wav");
		windwalk = new Sound("res/sounds/windwalk.wav");
		hit = new Sound("res/sounds/hit.wav");
	}
}