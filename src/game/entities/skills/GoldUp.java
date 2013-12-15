package game.entities.skills;

import game.Play;
import game.entities.Pickable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GoldUp extends Skill{

	int duration;
	boolean on;

	public GoldUp() throws SlickException {
		super();
		name = "GREED IS GOOD";
		desc = "Gold gained is increased by 200% for 10 seconds.";
		cost = 500;
		
	}

	@Override
	public void useSkill() throws SlickException {
		on = true;
		duration = 10000;
		Pickable.goldMod = 2f;
	}

	@Override
	public void update(int delta) {
		if(on){
			duration -= delta;
			
			if(duration <= 0){
				on = false;
				Pickable.goldMod = 1;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(on)
			g.drawString("GOLD X2", Play.p.renderX - 20, Play.p.renderY - 20);
	}

}
