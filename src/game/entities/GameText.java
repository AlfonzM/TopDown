package game.entities;

import game.Fonts;
import game.Play;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

/*
 * Example: take damage, take health
 */

public class GameText extends GameObject{
	
	String value;
	public Color color = Color.white;
	float targetY;
	
	AngelCodeFont font = Fonts.font24;
	
	public GameText(String val, Point p){
		super(p);
		value = val;
		
		targetY = pos.getY() - 40;
		
		Play.gameTexts.add(this);
	}
	
	public GameText(String val, Point p, Color col){
		super(p);
		value = val;
		
		targetY = pos.getY() - 40;
		
		color = col;
		
		Play.gameTexts.add(this);
	}
	
	public GameText(String val, Point p, int range){
		super(p);
		value = val;
		
		targetY = pos.getY() - range;
		
		Play.gameTexts.add(this);
	}
	
	public GameText(String val, Point p, int range, Color col, AngelCodeFont font){
		super(p);
		value = val;
		
		targetY = pos.getY() - range;
		
		color = col;
		
		this.font = font;
		
		Play.gameTexts.add(this);		
	}
	
	@Override
	public void update(int delta) throws SlickException {
//		pos.setY((float) (pos.getY() + (targetY - pos.getY()) * 0.2));
		pos.setY(pos.getY() - 0.9f);
		
		if((int)pos.getY() <= targetY){
			isAlive = false;
		}
	}
	
	@Override
	public void render(Graphics g){
		g.setColor(color);
		font.drawString(pos.getX(), pos.getY(), value, color);
//		g.drawString(value, pos.getX(), pos.getY());
		g.setColor(Color.white);
	}

}
