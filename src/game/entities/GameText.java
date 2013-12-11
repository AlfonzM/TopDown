package game.entities;

import game.Play;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

/*
 * Example: take damage, take health
 */

public class GameText extends GameObject{
	
	String value;
	
	float targetY;
	
	public GameText(String val, Point p){
		super(p);
		value = val;
		
		targetY = pos.getY() - 30;
		
		Play.gameTexts.add(this);
	}
	
	public GameText(String val, Point p, int range){
		super(p);
		value = val;
		
		targetY = pos.getY() - range;
		
		Play.gameTexts.add(this);
	}

	@Override
	public void update(int delta) throws SlickException {
//		pos.setY((float) (pos.getY() + (targetY - pos.getY()) * 0.2));
		pos.setY(pos.getY() - 0.9f);
		
		if((int)pos.getY() <= targetY){
			isAlive = false;
			System.out.println("dead");
		}
	}
	
	@Override
	public void render(Graphics g){
		g.drawString(value, pos.getX(), pos.getY());
	}

}
