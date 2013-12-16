package game.entities.skills;

import game.Fonts;
import game.Play;
import game.entities.GameText;
import game.entities.Pickable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class ExpUp extends Skill{

	public static int duration;
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
		
		new GameText("EXP X2!!", new Point(Play.p.pos.getX() - Fonts.font24.getWidth("EXP X2!!")/2, Play.p.pos.getY() - 20), 80, Color.cyan, Fonts.font24);
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
