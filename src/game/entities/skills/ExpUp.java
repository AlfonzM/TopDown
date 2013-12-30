package game.entities.skills;

import game.entities.Pickable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ExpUp extends DurationSkill{

	public static int duration = 10000;
	
	public ExpUp() throws SlickException {
		super(duration);
		name = "INCREASED XP";
		desc = "Experience gained is increased by 200% for 10 seconds.";
		cost = 300;
		
		on = false;
	}

	@Override
	public void useSkill() throws SlickException {
		on = true;
		Pickable.expMod = 2f;
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
		Pickable.expMod = 1;
	}

}
