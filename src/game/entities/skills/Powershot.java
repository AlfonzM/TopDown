package game.entities.skills;

import game.GOType;
import game.Play;
import game.Sounds;
import game.entities.Bullet;
import game.entities.PowershotBullet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Powershot extends Skill{
	Bullet b;
	Point pos;

	public Powershot() throws SlickException {
		name = "POWERSHOT";
		desc = "Shoots a projectile in the direction you're facing,\ndealing damage to all enemies it hits.";
		cost = 250;
		
	}

	public void useSkill() throws SlickException {
		int x = 0, y = 0;
		
		pos = Play.p.pos;
		Sounds.powershot.play(1, 0.5f);
		switch(Play.p.dir){
		case up:
			x = 0;
			y = -1;
			break;
			
		case down:
			x = 0;
			y = 1;
			break;
			
		case left:
			x = -1;
			y = 0;
			break;
			
		case right:
			x = 1;
			y = 0;
			
		default:
			break;
		
		}
		PowershotBullet b = new PowershotBullet(pos, x, y, GOType.Player);
		Play.objects.get(GOType.Bullet).add(b);
	}
	
	public void update(int delta){
	}
	
	public void render(Graphics g){
	}
}