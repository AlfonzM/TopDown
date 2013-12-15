package game.entities.skills;

import game.GOType;
import game.Play;
import game.entities.WizardBullet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SpreadShot extends Skill{

	public SpreadShot() throws SlickException {
		super();
		name = "SPREAD SHOT";
		desc = "Fires a volley of 7 shots.";
		cost = 150;
		
	}

	@Override
	public void useSkill() throws SlickException {
		float x = 0, x2 = 0, y = 0, y2 = 0;
		
		switch(Play.p.dir){
		case left:
			y = -0.75f;
			x = -1;
			x2 = 0;
			y2 = 1;
			break;
			
		case right:
			y = -0.75f;
			x = 1;
			x2 = 0;
			y2 = 1;
			break;
			
		case up:
			y = -1;
			y2 = 0;
			x = -0.75f;
			x2 = 1;
			break;
			
		case down:
			y = 1;
			y2 = 0;
			x = -0.75f;
			x2 = 1;
			break;
		
		default:
			break;
		}
		
		float inc = 0.25f;
		
		for(int i = 0 ; i < 8 ; i++){
			WizardBullet b1 = new WizardBullet(Play.p.pos, x + (i* inc * x2), y + (i * inc * y2), GOType.Player);
			b1.health = 3;
			b1.range = 9999999;
			Play.objects.get(GOType.Bullet).add(b1);
		}
	}

	@Override
	public void update(int delta) {
	}

	@Override
	public void render(Graphics g) {
	}
	

}
