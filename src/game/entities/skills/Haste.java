package game.entities.skills;
import game.Play;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Haste extends DurationSkill {
	
	public static int duration = 5000;

	public Haste() throws SlickException {
		super(duration);
		name = "HASTE";
		desc = "Grants 150% increased movespeed for 10 seconds.";
		cost = 100;
		
		on = false;
	}

	@Override
	public void useSkill() throws SlickException {
		on = true;
		Play.p.speed *= 2;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void render(Graphics g) {
		// TODO particles
	}
	
	@Override
	public void end(){
		super.end();
		
		Play.p.speed = Play.p.defaultSpeed;
	}
}
