package game;

import game.entities.skills.Skill;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HUD {
	static Image offSkill, onSkill;
	static Image hp[];
	static Image exp[];
	static Image levelBox;
	
	static boolean[] timer;
	static int[] dur;
	
	public HUD() throws SlickException{
		Image spritesheet = new Image("res/HUD.png");
		offSkill = spritesheet.getSubImage(0, 0, 54, 32);
		onSkill = spritesheet.getSubImage(0, 32, 54, 32);
		
		hp = new Image[5];
		exp = new Image[5];
		
		for(int i = 0 ; i < 5; i++){
			hp[i] = spritesheet.getSubImage(56 + (i*4), 0, 2, 19);
			exp[i] = spritesheet.getSubImage(56 + (i*4), 19, 2, 9);
		}
		
		levelBox = spritesheet.getSubImage(78, 0, 28, 28);
		
		timer = new boolean[4];
		dur = new int[4];
	}

	public static void render(Graphics g){
		g.translate(-ScreenShake.offsetX, -ScreenShake.offsetY);
		
		if(Play.getEnemies().isEmpty()){
			String t = "Next wave in";
			g.drawString(t, Play.centerText(t, Fonts.font16), 25);
			
			t= (Play.timeTillNextWave/1000) + "";
//				 + "." + (timeTillNextWave%1000/100) + (timeTillNextWave%100/10);
			Fonts.font24.drawString(Play.centerText(t, Fonts.font24), 50, t);
		}

		// HUD
		// health
//		try {
//			Play.healthGui = new Image("res/lifeBar.png");
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
//		
//		int posText = 0;
//		for(int ctr = 1; ctr<= Play.p.health;ctr++){
//			Play.healthGui.setColor(2, 2.0f,2.0f,2.0f);
//			g.drawImage(Play.healthGui, 10+ctr, 10);
//			posText = (10+ctr)/2;
//		}
//		if(Play.p.health <= 10){
//			posText = 10;
//		}
//		g.drawString(" "+ Play.p.health, posText-10, 10);
		
		// level
		g.drawImage(levelBox, 10, 10);
		
		float x = 10 + levelBox.getWidth();
		
		// HEALTH
		g.drawImage(hp[0], x, 10);
		g.drawImage(exp[0], x, 29);
		
		float x2 = 0;
		
		// health and expbar bg
		for(int i = 0 ; i < Play.p.maxHealth * 70/100; i++){
			x2 = 2 + x + (i*2);
			g.drawImage(hp[3], x2, 10);
			g.drawImage(exp[3], x2, 29);
		}
		
		// end health bar
		g.drawImage(hp[4], x2 + 2, 10);
		g.drawImage(exp[4], x2 + 2, 29);
		
		float x3 = x2;
		
		// current health
		for(int i = 0 ; i < Play.p.health * 70/100; i ++){
			x2 = 2 + x + (i * 2);
			g.drawImage(hp[1], x2, 10);
		}
		
		g.drawImage(hp[2], x2+2,10);
		
		x2 = 0;
		
		// EXP
		for(int i = 0 ; i < Play.p.experience * 70/Play.p.expToNextLevel; i++){
			x2 = 2 + x + (i * 2);
			g.drawImage(exp[1], x2, 29);
		}
		
		Fonts.font8.drawString(10 + levelBox.getWidth() + (x3+4-x)/2 - Fonts.font8.getWidth(Play.p.health + "/100")/2, 16, Play.p.health + "/100");
		
		Fonts.font16.drawString(10 + levelBox.getWidth()/2 - Fonts.font16.getWidth(""+Play.p.level)/2, 17, ""+Play.p.level);

		
//		g.drawString("Wave: " + Play.wave, 150, 5);
//		g.drawString("Level: " + Play.p.level, 10, 35);
//		g.drawString("Exp: " + Play.p.experience + "/" + Play.p.expToNextLevel, 10, 55);
		
		g.setColor(Color.yellow);
		Fonts.font8.drawString(Game.GWIDTH - 10 - Fonts.font8.getWidth("GOLD"), 10, "GOLD", Color.yellow);
		g.drawString("" + Play.p.gold, Game.GWIDTH - 10 - Fonts.font16.getWidth(""+Play.p.gold), 20);		
		g.setColor(Color.white);

		int gap = 40;
		float xpos = 0, ypos = Game.GHEIGHT - 20 - onSkill.getHeight();
		
		for(int i = 0 ; i < Play.p.skills.length ; i++){
			String hotkey = "";
			
			Skill s = Play.p.skills[i];
			
			switch(i){
			case 0:
				xpos = 10;
				hotkey = "Q";
				break;
			
			case 1:
				xpos = 10 + onSkill.getWidth() + gap;
				hotkey = "E";
				break;
			
			case 2:
				xpos = Game.GWIDTH - 10 - (onSkill.getWidth()*2) - gap;
				hotkey = "U";
				break;
				
			case 3:
				xpos = Game.GWIDTH - 10 - onSkill.getWidth();
				hotkey = "O";
				break;
			}
			
			if(Play.p.canUseSkill[i] && s.name != "")
				g.drawImage(onSkill, xpos, ypos);
			else
				g.drawImage(offSkill, xpos, ypos);
			
			float textX = 0, textY = ypos + onSkill.getHeight() + 5;			
			textX = xpos + onSkill.getWidth()/2 - Fonts.font8.getWidth(s.name)/2;
			Fonts.font8.drawString(textX, textY, s.name);
			
			textX = xpos + onSkill.getWidth()/2 - Fonts.font24.getWidth(hotkey)/2 - 1;
			Fonts.font24.drawString(textX, textY - 33, hotkey);
		}
		
		// timers
		for(int i = 0 ; i < 4; i++){
			if(timer[i]){
				int xpos2 = 0;
				
				switch(i){
				case 0:
					xpos2 = 10;
					break;
				
				case 1:
					xpos2 = 10 + onSkill.getWidth() + gap;
					break;
				
				case 2:
					xpos2 = Game.GWIDTH - 10 - (onSkill.getWidth()*2) - gap;
					break;
					
				case 3:
					xpos2 = Game.GWIDTH - 10 - onSkill.getWidth();
					break;
				}
				
				String d = "" + dur[i]/1000 + "." + dur[i]%1000/100;
				
				Fonts.font16.drawString(xpos2 + onSkill.getWidth()/2 - Fonts.font16.getWidth(d)/2, ypos - 20, d);
			}
		}
	}
	
	public static void update(int delta){
		for(int i = 0; i < 4; i++){
			if(timer[i]){
				dur[i] -= delta;
				
				if(dur[i] <= 0)
					timer[i] = false;
			}
		}
	}
	
	public static void addTimer(int duration, int pos){
		timer[pos] = true;
		dur[pos] = duration;
	}
}
