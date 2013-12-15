package game.entities.skills;

import game.Play;
import game.entities.Enemy;
import game.entities.GameObject;
import game.entities.Player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class AoESlow extends Skill{
	Rectangle aoe;
	int size = 300;

	public AoESlow() throws SlickException {
		super();
		name = "CRIPPLE";
		desc = "Enemies around you will be slowed by half.";
		cost = 150;
		
		aoe = new Rectangle(Player.renderX - size, Player.renderY - size, size, size);
	}

	@Override
	public void useSkill() throws SlickException {
		for(GameObject go : Play.getEnemies()){
			Enemy e = (Enemy) go;
			if(getBounds().contains(e.pos)){
				e.speed /= 2;
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
