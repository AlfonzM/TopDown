package game.entities.skills;

import game.Play;
import game.ScreenShake;
import game.Sounds;
import game.entities.GameObject;
import game.entities.Player;
import game.entities.enemies.Enemy;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class AoEDamage extends Skill{
	Rectangle aoe;
	int size = 200;
	
	boolean on;
	int duration = 5000;

	public AoEDamage() throws SlickException {
		super();
		name = "SPLIT EARTH";
		desc = "Deals damage to enemies in a small area around you.";
		cost = 650;
				
		aoe = new Rectangle(Player.renderX - size, Player.renderY - size, size, size);
	}

	@Override
	public void useSkill() throws SlickException {
		ScreenShake.shake();
		for(GameObject go : Play.getEnemies()){
			Enemy e = (Enemy) go;
			if(getBounds().contains(e.pos)){
				e.takeDamage(5);
			}
		}
		
		Sounds.stomp.play();
		Sounds.stomp2.play();
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
