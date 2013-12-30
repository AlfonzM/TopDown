package game.entities.enemies;

import game.Game;
import game.HUD;
import game.MyColors;
import game.Play;
import game.ScreenShake;
import game.entities.GameObject;
import game.entities.GameText;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class Lupa extends EMoveToPlayer{
	
	boolean lv1, lv2, lv3;
	int maxHealth;
	
	public Lupa(Point p) throws SlickException {
		super(p);

		defaultSpeed = 2f;
		speed = defaultSpeed;
		maxHealth = 60;
		health = maxHealth;
		
		atkDelay = 200;
		damage = 1;

		initMoveAnimations("lupa", 3);
		initAttackAnimations("lupa");
		
		lv1 = false;
		lv2 = false;
		lv3 = false;
		
		exp = 100;
		
		dieColors[0] = new Color(80, 80, 80);
		dieColors[1] = new Color(0, 0, 0);
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		if(!lv1 && health <= 40){
			lv1 = true;
			new GameText("ATK UP!", pos, 60, MyColors.red);
//			Play.addWest(1, EType.bat);
//			Play.addEast(10, EType.skull);
			damage = 5;
		}
		else if(!lv2 && health <= 30){
			lv2 = true;
			new GameText("HASTE!", pos, 60, MyColors.red);
//			Play.addNorth(5, EType.eyeball);
//			Play.addWest(5, EType.orc);
//			Play.addEast(5, EType.demon);
			speed = 2.5f;
		}
		else if(!lv3 && health <= 10){
			lv3 = true;
			new GameText("RUN FOR YOUR LIFE!", pos, 60, MyColors.red);
//			Play.addWest(20, EType.wolf);
//			Play.addNorth(5, EType.lavagolem);
			speed = 2.9f;
			damage = 10;
		}
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		
		g.translate(-Play.offsetX, -Play.offsetY);
		int x = Game.GWIDTH/2 - (6*maxHealth)/2, y = Game.GHEIGHT - 100;
		g.drawImage(HUD.hp[0], x, y);
		int i;
		g.drawImage(HUD.hp[0], x, y);
		for(i = 1 ; i < maxHealth*3; i ++){
			int iHp = 0;
			if(i < health*3)
				iHp = 1;
			else if(i == health*3)
				iHp = 2;
			else
				iHp = 3;
			
			g.drawImage(HUD.hp[iHp], x + i*2, y);
		}
		g.drawImage(HUD.hp[4], x + i*2, y);
		
//		g.drawString(""+health, pos.getX() + bounds.getWidth()/2 - Fonts.font16.getWidth(""+health), pos.getY() - 20);
		g.translate(Play.offsetX, Play.offsetY);
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
	
	@Override
	public void die() throws SlickException{
		super.die();
		for(GameObject g : Play.getEnemies()){
			Enemy e = (Enemy) g;
			if(e.getClass() != this.getClass()){
				e.isAlive = false;

//				ConfigurableEmitter em = Play.emitterUnit.duplicate();
//				em.addColorPoint(1, Color.black);
//				em.setPosition(e.pos.getX(), e.pos.getY() + getBounds().getHeight()/2);
//				Play.pSystem.addEmitter(em);
			}
		}
		ScreenShake.shake();
	}
}
