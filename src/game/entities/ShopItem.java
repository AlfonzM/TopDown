package game.entities;

import game.Fonts;
import game.Game;
import game.HUD;
import game.MyColors;
import game.Play;
import game.Shop;
import game.Sounds;
import game.entities.skills.DurationSkill;
import game.entities.skills.Skill;
import game.entities.skills.SkillList;
import game.entities.skills.WindWalk;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public class ShopItem{
	Skill s;
	
	Point pos;
	int value;
	Image icon;
	
	public ShopItem(int id, Point pos) throws SlickException {
		new SkillList();
		s = SkillList.getSkill(id);
		
		this.pos = pos;
		
		value = 100;
		
		float x = pos.getX();
		
		if(x < Game.MWIDTH/2){
			icon = new Image("res/gandalf/wiz1.png");
		}
		else if(x > Game.MWIDTH/2){
			icon = new Image("res/gandalf/wiz2.png");
		}
		else{
			icon = new Image("res/gandalf/wiz3.png");
		}
	}
	
	public void render(Graphics g){
		g.drawImage(icon, pos.getX(), pos.getY());
		
		if(Play.p.getBounds().intersects(getBounds())){
			g.translate(-Play.offsetX, -Play.offsetY);
			
			// Display item details
			Fonts.font24.drawString(Play.centerText(s.name, Fonts.font24), Game.GHEIGHT/2 + 90, s.name);
//			g.drawString(s.name, Play.centerText(s.name, Fonts.font16), Game.GHEIGHT/2 + 100);
			g.drawString(s.desc, Play.centerText(s.desc, Fonts.font16), Game.GHEIGHT/2 + 120);
			
			Color c = (Play.p.gold >= s.cost)? MyColors.yellow : MyColors.red;
			Fonts.font16.drawString(Play.centerText("Cost: "+s.cost, Fonts.font16), Game.GHEIGHT/2 + 160, "Cost: "+s.cost, c);
			
			g.translate(Play.offsetX, Play.offsetY);

			if(Play.p.input.isKeyPressed(Input.KEY_Q)){
				buy(0, s);
			}
			else if(Play.p.input.isKeyPressed(Input.KEY_E)){
				buy(1, s);
			}
			else if(Play.p.input.isKeyPressed(Input.KEY_U)){
				buy(2, s);
			}
			else if(Play.p.input.isKeyPressed(Input.KEY_O)){
				buy(3, s);
			}
		}
	}
	
	public void buy(int i, Skill s){
		String t;
		if(Play.p.gold >= s.cost){
			if(Play.p.skills[i].getClass().getSuperclass() == WindWalk.class.getSuperclass()){
				DurationSkill ds = (DurationSkill) Play.p.skills[i];
				ds.end();
				HUD.timer[i] = false;
			}
			Play.p.skills[i] = null;
			Play.p.skills[i] = s;
			Shop.isOpen = false;
			
			Play.p.goldToSubtract = s.cost;
			
			t = "-" + s.cost;
			
			Sounds.coin.play();
		}
		else{
			t = "Not enough gold!";
		}
		
		new GameText(t, new Point(Play.p.pos.getX() - Fonts.font16.getWidth(t)/2, Play.p.pos.getY() - 30), 60, Color.white, Fonts.font16);
	}

	public Rectangle getBounds(){
		return new Rectangle(pos.getX(), pos.getY(), icon.getWidth(), 
				icon.getHeight());
	}
}
