package game.entities.enemies;

import game.Fonts;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Lupa extends EMoveToPlayer{
	
	boolean lv1, lv2, lv3;
	
	public Lupa(Point p) throws SlickException {
		super(p);

		speed = 2f;
		health = 50;
		
		atkDelay = 200;
		damage = 1;

		initMoveAnimations("lupa", 3);
		initAttackAnimations("lupa");
		
		lv1 = false;
		lv2 = false;
		lv3 = false;
		
		exp = 100;
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		if(!lv1 && health <= 35){
			lv1 = true;
			System.out.println("1");
//			Play.addWest(1, EType.bat);
//			Play.addEast(10, EType.skull);
			damage = 3;
		}
		else if(!lv2 && health <= 25){
			lv2 = true;
			System.out.println("2");
//			Play.addNorth(5, EType.eyeball);
//			Play.addWest(5, EType.orc);
//			Play.addEast(5, EType.demon);
			speed = 2.3f;
			damage = 6;
		}
		else if(!lv3 && health <= 10){
			lv3 = true;
			System.out.println("3");
//			Play.addWest(20, EType.wolf);
//			Play.addNorth(5, EType.lavagolem);
			speed = 2.9f;
			damage = 10;
		}
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		g.drawString(""+health, pos.getX() + bounds.getWidth()/2 - Fonts.font16.getWidth(""+health), pos.getY() - 20);
	}
	
	public Rectangle getNewBounds(){
		return new Rectangle(pos.getX() + move.x + 5, pos.getY() + move.y, bounds.getWidth() - 10, bounds.getHeight());
	}
	
	public Rectangle getNewXBounds(){
		return new Rectangle(pos.getX() + 5 + move.x, pos.getY(), bounds.getWidth() - 10, bounds.getHeight());
	}
	
	public Rectangle getNewYBounds(){
		return new Rectangle(pos.getX() + 5, pos.getY() + move.y * 2, bounds.getWidth() - 10, bounds.getHeight());
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(pos.getX() + 5, pos.getY(), 69 - 5, 51);
	}
}
