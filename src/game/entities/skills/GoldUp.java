package game.entities.skills;

import game.Fonts;
import game.Play;
import game.entities.GameText;
import game.entities.Pickable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class GoldUp extends Skill{

	public static int duration;
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
		
		new GameText("GOLD X2!!", new Point(Play.p.pos.getX() - Fonts.font24.getWidth("GOLD X2!!")/2, Play.p.pos.getY() - 20), 80, Color.yellow, Fonts.font24);
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
	}

}
