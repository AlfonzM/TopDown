package game.entities.skills;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

public class SkillList {
	public static Map<Integer, Skill> list;
	
	public SkillList() throws SlickException{
		list = new HashMap<Integer, Skill>();
		add(new Blank());
		add(new Powershot());
		add(new WindWalk());
		add(new Haste());
		add(new AoESlow());
		add(new AoEStun());
		add(new AoEDamage());
		add(new SpreadShot());
		add(new ExpUp());
		add(new GoldUp());
		add(new Shoot360());
	}
	
	public void add(Skill s){
		list.put(list.size(), s);
	}
	
	public static Skill getSkill(int id){
		return list.get(id);
	}
}