package game.entities.skills;

import game.Fonts;
import game.Play;
import game.Sounds;
import game.entities.GameObject;
import game.entities.GameText;
import game.entities.Player;
import game.entities.enemies.Enemy;

import org.newdawn.slick.Color;
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
		Sounds.slow.play();
		for(GameObject go : Play.getEnemies()){
			Enemy e = (Enemy) go;
			if(getBounds().contains(e.pos)){
				e.speed /= 3;
				new GameText("Slow", e.pos, 80, Color.pink, Fonts.font16);
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
