package game.entities;

import game.PickableType;
import game.Play;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Pickable extends GameObject{	
	PickableType type;
	
	public Pickable(Point p, PickableType type) throws SlickException {
		super(p);
		this.type = type;
		sprite = new Image("res/lifeBar.png");
		bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
	}

	@Override
	public void update(int delta) throws SlickException {
		
	}

	@Override
	public void render(Graphics g) {
		if(type == PickableType.exp){
			sprite.setImageColor(0, 0, 255);
		}
		g.drawImage(sprite, pos.getX(), pos.getY());
	}

	public void pickedUp(){
		isAlive = false;
		
		switch(type){
		case hpUp:
			Play.p.addHP(10);
			break;
			
		case mpUp:
			Play.p.addMP(10);
			break;
			
		case exp:
			Play.p.experience += 10;
			break;
		}
	}
}