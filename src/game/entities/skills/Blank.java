package game.entities.skills;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class Blank extends Skill {
	
	public Blank() throws SlickException{
		name = "";
		desc = "";
	}

	public void useSkill() throws SlickException {
	}

	public void update(int delta) {
	}

	public void render(Graphics g) {
	}

}
