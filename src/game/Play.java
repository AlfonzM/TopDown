package game;

import game.entities.Bullet;
import game.entities.GameObject;
import game.entities.GameText;
import game.entities.Pickable;
import game.entities.Player;
import game.entities.Wizard;
import game.entities.enemies.Bat;
import game.entities.enemies.Demon;
import game.entities.enemies.DireWolf;
import game.entities.enemies.EMoveRandom;
import game.entities.enemies.Enemy;
import game.entities.enemies.Eyeball;
import game.entities.enemies.LavaGolem;
import game.entities.enemies.Orc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState{
	// Constant Values
	static int TILESIZE = 20;
	static int goCount = 0;
	public enum GameState { battle, rest }
	
	// GAME OBJECTS
	public static Player p;
	public static Map<GOType, ArrayList<GameObject>> objects;
	public static ArrayList<GameText> gameTexts;
	public static Shop shop;
	
	// Wave counter and timers
	public static int wave;
	public static int timeTillNextWave;
	public static int restTime;
	
	// Render translates
	public static float offsetX, offsetY;
	
	// Particle effects
	public static ParticleSystem pSystem;
	public static ConfigurableEmitter emitterUnit, emitterFire;
	
	// HUD
	static Image healthGui;
	
	// tutorial
	static boolean tutorial;
	
	// game stats
	public static int enemiesKilled;
	public static int totalGold;
	public static int totalExp;
	
	static Random r;
	static Image bg;
	public static GameState gameState;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initPlay(gc);
	}

	public static void initPlay(GameContainer gc) throws SlickException{
		new Fonts();
		new MyColors();
		new ScreenShake();
		new HUD();
		
		r = new Random();
		
		wave = 0;
		restTime = 30000;
		timeTillNextWave = restTime;
		
		bg = new Image("res/bg3.png");
		gameState = GameState.rest;
		
		// Initialize arrays
		objects = new HashMap<GOType, ArrayList<GameObject>>();
		gameTexts = new ArrayList<GameText>();
	
		// Player
		p = new Wizard(gc.getInput(), new Point(Game.MWIDTH/2, Game.MHEIGHT/2));
	
		objects.put(GOType.Pickable, new ArrayList<GameObject>());
		objects.put(GOType.Player, new ArrayList<GameObject>());
		objects.put(GOType.Enemy, new ArrayList<GameObject>());
		objects.put(GOType.Bullet, new ArrayList<GameObject>());
		
		objects.get(GOType.Player).add(p);
		
		// Prepare particle system
		try{
			Image image = new Image("res/particles/square.png");
			File xml = new File("res/particles/unitdeath.xml");
			File xmlFire = new File("res/particles/fire.xml");
			
			emitterUnit = ParticleIO.loadEmitter(xml);
			emitterFire = ParticleIO.loadEmitter(xmlFire);
					
			pSystem = new ParticleSystem(image, 1500);
	
			emitterFire.setPosition(143, 55);
			pSystem.addEmitter(emitterFire);
			
			ConfigurableEmitter e = emitterFire.duplicate();
			e.setPosition(569, 55);
			pSystem.addEmitter(e);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		tutorial = true;
		
		// game stats
		enemiesKilled = 0;
		totalGold = 0;
		totalExp = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(false);
		g.translate(ScreenShake.offsetX, ScreenShake.offsetY);
		g.setFont(Fonts.font16);
		
		bg.setAlpha(0.8f);
		bg.draw(offsetX, offsetY);

		if(gameState == GameState.rest){
			g.translate(offsetX, offsetY);
			
			if(Shop.isOpen)
				shop.render(g);
			g.translate(-offsetX, -offsetY);
		}
		
		// ===============================
		// offset
		// ===============================
		g.translate(offsetX, offsetY);
		
		// Tutorials
		if(tutorial && p.isAlive){
			switch(wave){
			case 0:
				g.drawString("Welcome to Hammerfall.", Game.MWIDTH/2 - Fonts.font16.getWidth("Welcome to Hammerfall.")/2, 400);
				g.drawString("Use WASD to move, IJKL to shoot.", Game.MWIDTH/2 - Fonts.font16.getWidth("Use WASD to move, IJKL to shoot.")/2, 420);	
				break;
				
			case 1:
				if(gameState == GameState.battle){
					g.drawString("Destroy all forces of evil.", Game.MWIDTH/2 - Fonts.font16.getWidth("Destroy all forces of evil.")/2, 400);
					g.drawString("Collect experience and gold.", Game.MWIDTH/2 - Fonts.font16.getWidth("Collect experience and gold.")/2, 420);	
				}
				else{
					if(Play.p.skills[0].name == "" && Play.p.skills[1].name == "" && Play.p.skills[2].name == "" && Play.p.skills[3].name == ""){
						float y = 330;
						String t = "You will be given 3 random scrolls at the end of every round,";
						g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y);
						t = "but you only get to purchase one. So choose wisely.";
						g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y+20);	
						t = "Select a scroll and press \"Q\" to store it to your Q skill slot.";
						g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y+40);							
					}						
					else{
						float y = 330;
						String t = "You may store other skills to E, U and O.";
						g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y);
						t = "However, you also only get to use a skill once per round.";
						g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y+20);	
						t = "Use them wisely.";
						g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y+40);							
					}
				}
				break;
				
			case 4:
				float y = 400;
				if(gameState == GameState.rest){
					String t = "Storing a new skill to an occupied slot will replace the old one.";
					g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y);							
				}
				break;
				
			case 5:
				if(gameState == GameState.battle){
					String t = "Good luck.";
					g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, 400);
				}
				break;
			}
		}
		
		// Stuff
		for(ArrayList<GameObject> goArray : objects.values()){
			for(GameObject go : goArray ){
				if(!go.getClass().equals(p.getClass())){
					go.render(g);
				}
			}
		}
		
		// Game Texts
		for(GameText gt : gameTexts){
			gt.render(g);
		}
		
		// Particle system
		pSystem.render();

		// ===============================
		// offset back to normal for HUD
		// ===============================
		
		g.translate(-offsetX, -offsetY);
		
		if(p.isAlive)
			p.render(g);
		
		// HUD
		HUD.render(g);
		
		// display xy coords
//		int x = gc.getInput().getMouseX();
//		int y = gc.getInput().getMouseY();
//		g.drawString(x+ " " + y, x, y-20);
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		HUD.update(delta, gc, sbg);
		
		// skip countdown
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			if(gameState == GameState.rest){
				timeTillNextWave = 0;
			}
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_F2)){
			ScreenShake.shake();
		}
		
		// check state
		if(gameState == GameState.battle && getEnemies().isEmpty()){
			gameState = GameState.rest;
			
			for(int i = 0 ; i < p.skills.length ; i++){
				p.canUseSkill[i] = true;
			}
			
			// initialize shop
			shop = new Shop();
		}
		
		// REST STATE
		if(gameState == GameState.rest && p.isAlive){
			// countdown
			timeTillNextWave -= delta;
			
			if(timeTillNextWave < 0){
				// commence next wave
				wave++;
				timeTillNextWave = restTime;
				addNorth(10 * wave, EType.orc);
				addSouth(2*wave, EType.lavagolem);
				addWest(5*wave, EType.eyeball);
//				addNorth(10 * wave, EType.wolf);
//				addSouth(2 * wave, EType.eyeball);
//				addEast(2 * wave, EType.eyeball);
//				addWest(2 * wave, EType.eyeball);
				
				gameState = GameState.battle;
			}
		}
		
		// Update all game objects
		for(ArrayList<GameObject> goArray : objects.values()){
			for (Iterator<GameObject> iterator = goArray.iterator(); iterator.hasNext(); ) {
				GameObject go = iterator.next();
				if (!go.isAlive) {
					iterator.remove();
				} else
					go.update(delta);
			}
		}
		
		// Update all game texts
		for (Iterator<GameText> iterator = gameTexts.iterator(); iterator.hasNext(); ) {
			GameText gt = iterator.next();
			if (!gt.isAlive) {
				iterator.remove();
			} else
				gt.update(delta);
		}
		
		// Select enemy mode (for testing only)
//		if(gc.getInput().isKeyPressed(Input.KEY_F1)){
//			for(GameObject go : getEnemies()){
//				go.isAlive = false;
//			}
//			
//			addAIWest(3);
//			
//			addRandomWest(5);
//			addRandomNorth(5);
//			addRandomEast(5);
//		}
//		if(gc.getInput().isKeyPressed(Input.KEY_F2)){
//			for(GameObject go : getEnemies()){
//				go.isAlive = false;
//			}
//
//			for(int i = 0; i < 30; i ++){
//				EMoveRandom ee = new EMoveRandom(new Point(r.nextInt(Game.GWIDTH), r.nextInt(Game.GHEIGHT)));
//				Play.objects.get(GOType.Enemy).add(ee);
//			}
//		}
		
		// Update particle system
		pSystem.update(delta);
		
		// Update screenshaker
		ScreenShake.update(delta);
	}
	
	// UTILITY FUNCTIONS
	
	public static void addExpDrop(Point pos, int value) throws SlickException{
		objects.get(GOType.Pickable).add(new Pickable(pos, value, PickableType.exp));
	}
	
	public static void addGoldDrop(Point pos, int value) throws SlickException{
		objects.get(GOType.Pickable).add(new Pickable(pos, value, PickableType.gold));
	}
//	
//	public void addEnemyAI(float x, float y) throws SlickException{
//		Eyeball ee = new Eyeball(new Point(x, y));
//		objects.get(GOType.Enemy).add(ee);
//	}
//	
//	public void addEnemyRandom(float x, float y) throws SlickException{
//		EMoveRandom ee = new EMoveRandom(new Point(x, y));
//		objects.get(GOType.Enemy).add(ee);
//	}
	
	public void addNorth(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(r.nextInt(Game.MWIDTH-20) + 10, 0 - i*Game.TS + 10, et);
	}
	
	public void addSouth(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(r.nextInt(Game.MWIDTH-20) + 10, Game.MHEIGHT + i*Game.TS + 10, et);
	}
	
	public void addWest(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(0 - i*Game.TS + 10, r.nextInt(Game.MHEIGHT - 100) + 50, et);
	}
	
	public void addEast(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(Game.GWIDTH + i*Game.TS + 10, r.nextInt(Game.MHEIGHT - 100) + 50, et);
	}
	
	public void addEnemy(float x, float y, EType et) throws SlickException{
		Enemy e = null;
		Point p = new Point(x, y);
		
		switch(et){
		case bat:
			e = new Bat(p);
			break;
		case demon:
			e = new Demon(p);
			break;
		case eyeball:
			e = new Eyeball(new Point(x, y));
			break;
		case lavagolem:
			e = new LavaGolem(p);
			break;
		case orc:
			e = new Orc(p);
			break;
		case wolf:
			e = new DireWolf(p);
			break;
		default:
			break;
		}
		
		objects.get(GOType.Enemy).add(e);
	}
	
//	public void addAIWest(int count) throws SlickException{
//		for(int i = 0; i < count; i ++){
//			addEnemyAI(0 - i*Game.TS + 10, r.nextInt(85) + 185);
//		}
//	}
//	
//	public void addAIEast(int count) throws SlickException{
//		for(int i = 0; i < count; i ++){
//			addEnemyAI(Game.GWIDTH + i*Game.TS + 10, r.nextInt(85) + 185);
//		}
//	}
//	
//	public void addAINorth(int count) throws SlickException{
//		for(int i = 0 ; i < count ; i++){
//			addEnemyAI(r.nextInt(417-305-Game.TS) + 305, 0 - i*Game.TS + 10);
//		}
//	}
//	
//	public void addRandomWest(int count) throws SlickException{
//		for(int i = 0 ; i < count ; i++){
//			addEnemyRandom(0 - i*Game.TS + 10, r.nextInt(85) + 185);
//		}
//	}
//	
//	public void addRandomEast(int count) throws SlickException{
//		for(int i = 0 ; i < count ; i++){
//			addEnemyRandom(Game.GWIDTH + i*Game.TS + 10, r.nextInt(85) + 185);
//		}
//	}
//	
//	public void addRandomNorth(int count) throws SlickException{
//		for(int i = 0 ; i < count ; i++){
//			addEnemyRandom(r.nextInt(417-305-Game.TS) + 305, 0 - i*Game.TS + 10);
//		}
//	}
	
	public static ArrayList<GameObject> getEnemies(){
		return objects.get(GOType.Enemy);
	}
	
	public static boolean isInsideWallsX(float x){
//		return x < 20 || x > Game.MWIDTH - 20 - Game.TS;
		
		return x < 0 || x > Game.MWIDTH - Game.TS;
	}
	
	public static boolean isInsideWallsY(float y){
//		return y < 95 || y > Game.MHEIGHT - 40;
		
		return y < 0 || y > Game.MHEIGHT - Game.TS;
	}
	
	public static int getObjectCount(){
		int objectCount = 0;
		
		for(ArrayList<GameObject> goArray : objects.values()){
			for(@SuppressWarnings("unused") GameObject go : goArray){
				objectCount++;
			}
		}
		
		return objectCount;
	}

	public static void addBullet(Bullet b) {
		Play.objects.get(GOType.Bullet).add(b);
		
	}

	public static int centerText(String string, AngelCodeFont font){
		return (Game.GWIDTH - font.getWidth(string))/2;
	}

	public int getID() {		
		return 1;
	}
}