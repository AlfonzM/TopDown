package game.entities.skills;

import game.Play;
import game.Sounds;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class WindWalk extends Skill{
	
	public static int duration = 7000;
	boolean on;
	
	public WindWalk() throws SlickException {
		super();
		name = "SHADOW WALK";
		desc = "Grants invisibility and increased\nmove speed for 7 seconds.";
		cost = 150;
		
		on = false;
	}

	@Override
	public void useSkill() throws SlickException {
		duration = 7000;
		on = true;
		
		Play.p.invulnerable = true;
		Play.p.invisible = true;
		Play.p.speed *= 1.5f;
		
		Sounds.windwalk.play();
	}

	@Override
	public void update(int delta) {
		if(on){
			duration -= delta;
			
			if(duration < 0){
				on = false;
				Play.p.invulnerable = false;
				Play.p.invisible = false;
				Play.p.speed = Play.p.defaultSpeed;
			}			
		}
	}

	@Override
	public void render(Graphics g) {
	}
}