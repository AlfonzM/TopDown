package game.entities.skills;

import game.GOType;
import game.Play;
import game.entities.Bullet;
import game.entities.WizardBullet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Powershot extends Skill{
	Bullet b;
	Point pos;

	public Powershot() throws SlickException {
		icon = new Image("res/sprite.png");
		name = "POWERSHOT";
		desc = "Shoots a projectile in the direction you're facing,\nkilling all enemies it hits.";
		cost = 250;
		
	}

	public void useSkill() throws SlickException {
		int x = 0, y = 0;
		
		pos = Play.p.pos;
		System.out.println("Powershot!");
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
		WizardBullet b = new WizardBullet(pos, x, y, GOType.Player);
		b.damage = 1000000;
		b.health = 10000000;
		b.range = 10000;
		Play.objects.get(GOType.Bullet).add(b);
	}
	
	public void update(int delta){
	}
	
	public void render(Graphics g){
	}
}