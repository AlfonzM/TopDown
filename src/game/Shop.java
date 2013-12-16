package game;

import game.entities.ShopItem;
import game.entities.skills.SkillList;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Shop {
	ShopItem[] items;
	
	public static boolean isOpen;
	
	public Shop() throws SlickException{
		new SkillList();
		
		items = new ShopItem[3];
		Random r = new Random();
		
		int id1, id2, id3;
		do{
			id1 = r.nextInt(SkillList.list.size()-1) + 1;
		}while(checkIfExists(id1));
		
		items[0] = new ShopItem(id1, new Point(Game.MWIDTH/2 - 100, Game.MHEIGHT/2 - 50));
		
		do{
			id2 = r.nextInt(SkillList.list.size()-1) + 1;
		}while(id2 == id1 || checkIfExists(id2));
		
		items[1] = new ShopItem(id2, new Point(Game.MWIDTH/2, Game.MHEIGHT/2 - 50));
		
		do{
			id3 = r.nextInt(SkillList.list.size()-1) + 1;
		}while(id3 == id1 || id3 == id2 || checkIfExists(id3));
		
		items[2] = new ShopItem(6, new Point(Game.MWIDTH/2 + 100, Game.MHEIGHT/2 - 50));
		
		isOpen = true;
	}
	
	public boolean checkIfExists(int id){
		for(int i = 0 ; i < Play.p.skills.length ; i++){
			if(Play.p.skills[i].name.equals(SkillList.getSkill(id).name)){
				return true;
			}
		}
		
		return false;		
	}
	
	public void update(int delta){
//		items[0].update(delta);
//		items[1].update(delta);
//		items[2].update(delta);
	}
	
	public void render(Graphics g){
		items[0].render(g);
		items[1].render(g);
		items[2].render(g);
	}
}