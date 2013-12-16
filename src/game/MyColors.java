package game;

import org.newdawn.slick.Color;

public class MyColors {
	public static Color green, cyan, red, yellow;
	
	public MyColors(){
		green = new Color(156/256f, 255/256f, 0f, 1);
		red = new Color(254/256f, 37/256f, 37/256f, 1);
		yellow = new Color(255/256f, 240/256f, 0/256f, 1);
		cyan = new Color(54/255f, 240/255f, 252/255f, 1);
	}
}