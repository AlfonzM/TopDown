package game.entities.skills;

import game.entities.Pickable;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GoldUp extends DurationSkill{

	public static int duration = 10000;

	public GoldUp() throws SlickException {
		super(duration);
		name = "GREED IS GOOD";
		desc = "Gold gained is increased by 200% for 10 seconds.";
		cost = 500;

	}

	@Override
	public void useSkill() throws SlickException {
		on = true;
		Pickable.goldMod = 2f;
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
		Pickable.goldMod = 1;
	}

}
