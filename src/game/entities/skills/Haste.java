package game.entities.skills;
import game.Play;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Haste extends Skill {
	
	public static int duration = 5000;
	boolean on;

	public Haste() throws SlickException {
		super();
		name = "HASTE";
		desc = "Grants 150% increased movespeed for 10 seconds.";
		cost = 100;
		
		on = false;
	}

	@Override
	public void useSkill() throws SlickException {
		duration = 5000;
		on = true;
		Play.p.speed *= 2;
	}

	@Override
	public void update(int delta) {
		if(on){
			duration -= delta;
			
			if(duration < 0){
				on = false;
			}
		}
		else{
			if(Play.p.speed > Play.p.defaultSpeed)
				Play.p.speed -= 0.1f;
			else
				Play.p.speed = Play.p.defaultSpeed;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO particles
	}
}
