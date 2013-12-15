package game.entities.skills;

import game.entities.Pickable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ExpUp extends Skill{

	int duration;
	boolean on;
	
	public ExpUp() throws SlickException {
		super();
		name = "INCREASED XP";
		desc = "Experience gained is increased by 200% for 10 seconds.";
		cost = 300;
		
		
		on = false;
	}

	@Override
	public void useSkill() throws SlickException {
		on = true;
		duration = 10000;
		Pickable.expMod = 2f;
	}

	@Override
	public void update(int delta) {
		if(on){
			duration -= delta;
			
			if(duration <= 0){
				on = false;
				Pickable.expMod = 1;
			}
		}
	}

	@Override
	public void render(Graphics g) {
	}

}
