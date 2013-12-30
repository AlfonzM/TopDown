package game.entities;

import game.Fonts;
import game.MyColors;
import game.PickableType;
import game.Play;
import game.Sounds;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.particles.ConfigurableEmitter;

public class Pickable extends GameObject{	
	PickableType type;
	int value;
	
	int lifespan = 7000, life;
	
	public static float expMod = 1;
	public static float goldMod = 1;
	
	ConfigurableEmitter e;
	
	public Pickable(Point p, int value, PickableType type) throws SlickException {
		super(p);
		this.type = type;
		this.value = value;
		life = lifespan;
		
		if(type == PickableType.gold)
			sprite = new Image("res/gold.png");
		else
			sprite = new Image("res/exp.png");
			
		bounds = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());
		
		e = Play.emitterPick.duplicate();
	}

	@Override
	public void update(int delta) throws SlickException {
		life -= delta;
		
		if(life < 0 ){
			isAlive = false;
		}
	}

	@Override
	public void render(Graphics g) {
		sprite.setAlpha((float) life/(lifespan/2));
		g.setAntiAlias(false);
		g.drawImage(sprite, pos.getX(), pos.getY());
	}

	public void pickedUp(){
		isAlive = false;
		String text = null;
		Color c = null;
		
		switch(type){			
		case exp:
			value *= expMod;
			c = MyColors.cyan;
			
			if(expMod > 1)
				text = "EXP x2\n+" + value;
			else
				text = "+" + value;

			Play.p.addExp(value);
			Play.totalExp += value;
			
			Sounds.exp.play();

			e.addColorPoint(0, new Color(0.5f, 0.8f, 0.8f));
			e.addColorPoint(0.5f, new Color(0.5f, 1, 1));
			break;
			
		case gold:			
			value *= goldMod;
			c = MyColors.yellow;
			
			if(goldMod > 1)
				text = "GOLD x2\n+" + value;
			else
				text = "+" + value;

			Play.p.goldToAdd += value;
			Play.totalGold += value;
			
			Sounds.coin.play();
			
			e.addColorPoint(0, new Color(0.8f, 0.8f, 0));
			e.addColorPoint(0.2f, Color.yellow);
			break;
			
		default:
			break;
		}
		
		new GameText(text, new Point(pos.getX() - Fonts.font24.getWidth(text)/2, pos.getY()), c);
		
		e.setPosition(pos.getX(), pos.getY());
		Play.pSystem.addEmitter(e);
	}
}