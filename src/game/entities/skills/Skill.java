package game.entities.skills;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Skill{
	public Image icon;
	public String desc;
	public String name;
	public int cost;
	
	public Skill() throws SlickException {
		icon = new Image("res/sprite.png");
	}
	
	//Override these methods
	public abstract void useSkill() throws SlickException;
	
	public abstract void update(int delta);
	
	public abstract void render(Graphics g);
}