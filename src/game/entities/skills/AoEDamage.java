package game.entities.skills;

import game.Play;
import game.ScreenShake;
import game.entities.Enemy;
import game.entities.GameObject;
import game.entities.Player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class AoEDamage extends Skill{
	Rectangle aoe;
	int size = 300;
	
	boolean on;
	int duration = 5000;

	public AoEDamage() throws SlickException {
		super();
		name = "SPLIT EARTH";
		desc = "Damages all enemies around you.";
		cost = 750;
				
		aoe = new Rectangle(Player.renderX - size, Player.renderY - size, size, size);
	}

	@Override
	public void useSkill() throws SlickException {
		ScreenShake.screenShake(1000);
		for(GameObject go : Play.getEnemies()){
			Enemy e = (Enemy) go;
			if(getBounds().contains(e.pos)){
				e.die();
			}
		}
	}

	@Override
	public void update(int delta) {
		aoe.setX(Play.p.pos.getX() - aoe.getWidth()/2);
		aoe.setY(Play.p.pos.getY() - aoe.getWidth()/2);
	}

	@Override
	public void render(Graphics g) {
	}
	
	public Rectangle getBounds(){
		return aoe;
	}

}
