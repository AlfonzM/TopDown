package game.entities.skills;

import game.Play;
import game.Sounds;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class WindWalk extends DurationSkill{
	
	public static int duration = 7000;
	
	public WindWalk() throws SlickException {
		super(duration);
		name = "SHADOW WALK";
		desc = "Grants invisibility and increased\nmove speed for 7 seconds.";
		cost = 150;
		
		on = false;
		System.out.println(this.getClass().getSuperclass());
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
		super.update(delta);
	}

	@Override
	public void render(Graphics g) {
	}
	
	@Override
	public void end(){
		super.end();
		
		Play.p.invulnerable = false;
		Play.p.invisible = false;
		Play.p.speed = Play.p.defaultSpeed;
	}
}