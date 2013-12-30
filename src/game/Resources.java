package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Resources {
	public static Animation bat1;
	Image[] imgs;
	
	public Resources() throws SlickException{
		// bat
		SpriteSheet bat = new SpriteSheet("res/bat/bat.png", 30, 30);
		imgs = new Image[3];
		for(int i = 0 ; i < bat.getHorizontalCount(); i++){
			imgs[i] = bat.getSubImage(i, 0);
		}
		
		bat1 = new Animation(imgs, 150, true);
	}
}
