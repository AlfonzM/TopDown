package game;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fonts {
	public static AngelCodeFont font8, font16, font24, font48;
	
	public Fonts() throws SlickException{
		String font = "font16";
		font16 = new AngelCodeFont("res/fonts/" + font + ".fnt", new Image("res/fonts/"+ font +"_0.png"));		
		
		font = "font8";
		font8 = new AngelCodeFont("res/fonts/" + font + ".fnt", new Image("res/fonts/"+ font +"_0.png"));
		
		font = "font24";
		font24 = new AngelCodeFont("res/fonts/" + font + ".fnt", new Image("res/fonts/"+ font +"_0.png"));
		
		font = "font48";
		font48 = new AngelCodeFont("res/fonts/" + font + ".fnt", new Image("res/fonts/"+ font +"_0.png"));
	}
}
