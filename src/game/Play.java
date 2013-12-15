package game;

import game.entities.EMoveRandom;
import game.entities.EMoveToPlayer;
import game.entities.GameObject;
import game.entities.GameText;
import game.entities.Pickable;
import game.entities.Player;
import game.entities.Wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
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
	
	Random r;
	Image bg;
	public static GameState gameState;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		new Fonts();
		new ScreenShake();
		new HUD();
		
		r = new Random();
		
		wave = 0;
		restTime = 20000;
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
		
		// first shop
		shop = new Shop();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.translate(ScreenShake.offsetX, ScreenShake.offsetY);
		g.setFont(Fonts.font16);
		
		bg.setAlpha(0.9f);
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
		p.render(g);
		
		// HUD
		HUD.render(g);
		
		// display xy coords
//		int x = gc.getInput().getMouseX();
//		int y = gc.getInput().getMouseY();
//		g.drawString(x+ " " + y, x, y-20);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		HUD.update(delta);
		
		// skip countdown
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			timeTillNextWave = 0;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_F2)){
			ScreenShake.screenShake(1000);
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
		if(gameState == GameState.rest){
			
			// countdown
			timeTillNextWave -= delta;
			
			if(timeTillNextWave < 0){
				// commence next wave
				timeTillNextWave = restTime;
				addAINorth(1);
//				addAIWest(5);
//				addAIEast(5);
//				addRandomWest(15);
//				addRandomEast(15);
				wave++;
				
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
	
	public void addEnemyAI(float x, float y) throws SlickException{
		EMoveToPlayer ee = new EMoveToPlayer(new Point(x, y));
		objects.get(GOType.Enemy).add(ee);
	}
	
	public void addEnemyRandom(float x, float y) throws SlickException{
		EMoveRandom ee = new EMoveRandom(new Point(x, y));
		Play.objects.get(GOType.Enemy).add(ee);
	}
	
	public void addAIWest(int count) throws SlickException{
		for(int i = 0; i < count; i ++){
			addEnemyAI(0 - i*Game.TS + 10, r.nextInt(85) + 185);
		}
	}
	
	public void addAIEast(int count) throws SlickException{
		for(int i = 0; i < count; i ++){
			addEnemyAI(Game.GWIDTH + i*Game.TS + 10, r.nextInt(85) + 185);
		}
	}
	
	public void addAINorth(int count) throws SlickException{
		for(int i = 0 ; i < count ; i++){
			addEnemyAI(r.nextInt(417-305-Game.TS) + 305, 0 - i*Game.TS + 10);
		}
	}
	
	public void addRandomWest(int count) throws SlickException{
		for(int i = 0 ; i < count ; i++){
			addEnemyRandom(0 - i*Game.TS + 10, r.nextInt(85) + 185);
		}
	}
	
	public void addRandomEast(int count) throws SlickException{
		for(int i = 0 ; i < count ; i++){
			addEnemyRandom(Game.GWIDTH + i*Game.TS + 10, r.nextInt(85) + 185);
		}
	}
	
	public void addRandomNorth(int count) throws SlickException{
		for(int i = 0 ; i < count ; i++){
			addEnemyRandom(r.nextInt(417-305-Game.TS) + 305, 0 - i*Game.TS + 10);
		}
	}
	
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

	public static int centerText(String string, AngelCodeFont font){
		return (Game.GWIDTH - font.getWidth(string))/2;
	}

	public int getID() {		
		return 1;
	}
}