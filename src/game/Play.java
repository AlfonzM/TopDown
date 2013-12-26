package game;

import game.entities.Bullet;
import game.entities.GameObject;
import game.entities.GameText;
import game.entities.Pickable;
import game.entities.Player;
import game.entities.Wizard;
import game.entities.enemies.Bat;
import game.entities.enemies.Demon;
import game.entities.enemies.Enemy;
import game.entities.enemies.Eyeball;
import game.entities.enemies.LavaGolem;
import game.entities.enemies.Lupa;
import game.entities.enemies.Orc;
import game.entities.enemies.Skull;
import game.entities.enemies.Wolf;

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
import org.newdawn.slick.state.transition.FadeInTransition;

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
	public static ConfigurableEmitter emitterUnit, emitterSpark;
	
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
	public static boolean win;
	
	
	int gameCounter = 0;
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		initPlay(gc, sbg);
	}

	public static void initPlay(GameContainer gc, StateBasedGame sbg) throws SlickException{
		new Fonts();
		new MyColors();
		new ScreenShake();
		new HUD(sbg);
		
		r = new Random();
		
		wave = 0;
		restTime = 30000;
		timeTillNextWave = restTime;
		
		bg = new Image("res/123.png");
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
			File xml2 = new File("res/particles/spark.xml");
			
			emitterUnit = ParticleIO.loadEmitter(xml);
			emitterSpark = ParticleIO.loadEmitter(xml2);
			
			pSystem = new ParticleSystem(image, 1500);
			pSystem.setRemoveCompletedEmitters(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		tutorial = true;
		win = false;
		
		// game stats
		enemiesKilled = 0;
		totalGold = 0;
		totalExp = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(wave >= 15 && gameState == GameState.rest){
			win = true;
		}
		
		g.setAntiAlias(false);
		g.translate(ScreenShake.offsetX, ScreenShake.offsetY);
		g.setFont(Fonts.font16);
		bg.draw(offsetX, offsetY);

		if(!win && gameState == GameState.rest){
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
				g.drawString("Hordes of evil forces are incoming!", Game.MWIDTH/2 - Fonts.font16.getWidth("Hordes of evil forces are incoming!")/2, 330);
				g.drawString("You must defend Hammerfall!", Game.MWIDTH/2 - Fonts.font16.getWidth("You must defend Hammerfall!")/2, 350);	
				break;
				
			case 1:
				if(gameState == GameState.battle){
					g.drawString("Use WASD to move, IJKL to shoot.", Game.MWIDTH/2 - Fonts.font16.getWidth("Use WASD to move, IJKL to shoot.")/2, 330);
					g.drawString("Collect experience and gold.", Game.MWIDTH/2 - Fonts.font16.getWidth("Collect experience and gold.")/2, 350);	
				}
				else{
					if(Play.p.skills[0].name == "" && Play.p.skills[1].name == "" && Play.p.skills[2].name == "" && Play.p.skills[3].name == ""){
						float y = 330;
						String t = "You will be given 3 random skills at the end of every round,";
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
				
			case 3:
				if(gameState == GameState.rest){
					float y = 330;
					String t = "When you level up, you will be";
					g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y);
					t = "restored back to full health.";
					g.drawString(t, Game.MWIDTH/2 - Fonts.font16.getWidth(t)/2, y+20);	
				}
				break;
				
			case 4:
				float y = 330;
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
				
			case 6:
				tutorial = false;
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
		
		// on enemies empty / battle end
		if(!win && gameState == GameState.battle && getEnemies().isEmpty()){
			gameState = GameState.rest;
			
			for(int i = 0 ; i < p.skills.length ; i++){
				p.canUseSkill[i] = true;
			}
			
			// initialize shop
			shop = new Shop();
		}
		
		// while on rest state
		if(!win && gameState == GameState.rest && p.isAlive){
			// countdown
			timeTillNextWave -= delta;
			
			if(timeTillNextWave < 0){
				// commence next wave
				battleBegin();
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
	
	private void battleBegin() throws SlickException {
		HUD.counter = 0;
		HUD.waveAlpha = 0;
		
		Shop.isOpen = false;
		wave++;
		if(tutorial)
			timeTillNextWave = restTime;
		else
			timeTillNextWave = 15000;
		switch(wave){
		case 1:
//			tutorial=false;
			addNorth(1, EType.bat);
			addWest(5, EType.bat);
			addEast(5, EType.bat);
//			addNorth(5, EType.skull);
//			addWest(1, EType.lavagolem);
//			addWest(3, EType.eyeball);
//			addSouth(3, EType.demon);
//			addSouth(3, EType.orc);
//			addEast(1, EType.lupa);
//			addEast(5, EType.wolf);
			break;
			
		case 2:
			addSouth(10, EType.bat);
			addNorth(1, EType.orc);
			addEast(1, EType.orc);
			addWest(1, EType.orc);
			break;
			
		case 3:
			addWest(5, EType.orc);
			addEast(5, EType.orc);
			addSouth(3, EType.demon);
			break;
			
		case 4:
			addWest(5, EType.orc);
			addEast(3, EType.demon);
			addNorth(5, EType.bat);
			addSouth(5, EType.bat);
			break;
			
		case 5:
			addWest(10, EType.bat);
			addEast(10, EType.bat);
			addNorth(5, EType.wolf);
			addSouth(5, EType.wolf);
			break;
			
		case 6:
			addWest(5, EType.wolf);
			addEast(5, EType.wolf);
			addNorth(5, EType.demon);
			break;
			
		case 7:
			addWest(2, EType.eyeball);
			addNorth(2, EType.eyeball);
			addEast(5, EType.bat);
			addSouth(5, EType.bat);
			break;
			
		case 8:
			addSouth(5, EType.wolf);
			addEast(3, EType.eyeball);
			addWest(3, EType.eyeball);
			addNorth(5, EType.bat);
			break;
			
		case 9:
			addNorth(5, EType.orc);
			addSouth(5, EType.demon);
			addWest(5, EType.skull);
			addEast(5, EType.skull);
			break;
			
		case 10:
			addSouth(1, EType.lavagolem);
			addNorth(3, EType.wolf);
			addEast(3, EType.demon);
			addWest(3, EType.orc);
			break;
			
		case 11:
			addSouth(3, EType.lavagolem);
			addNorth(10, EType.skull);
			addWest(5, EType.wolf);
			addEast(5, EType.eyeball);
			break;
			
		case 12:
			addNorth(5, EType.eyeball);
			addSouth(5, EType.eyeball);
			addWest(5, EType.eyeball);
			break;
			
		case 13:
			addNorth(15, EType.bat);
			addSouth(15, EType.skull);
			addWest(5, EType.lavagolem);
			addEast(10, EType.wolf);
			break;
			
		case 14:
			addNorth(5, EType.lavagolem);
			addSouth(5, EType.lavagolem);
			addWest(10, EType.skull);
			addEast(10, EType.skull);					
			break;
			
		case 15:
			EType e = EType.lupa;
			addNorth(1, e);
			addWest(10, EType.wolf);
			addEast(10, EType.wolf);
			addSouth(3, EType.lavagolem);
			break;
		}
		
		gameState = GameState.battle;
	}

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
	
	public static void addNorth(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(Dir.up, i, et);
	}
	
	public static void addSouth(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(Dir.down, i, et);
	}
	
	public static void addWest(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(Dir.left, i, et);
	}
	
	public static void addEast(int count, EType et) throws SlickException{
		for(int i = 0 ; i < count ; i++)
			addEnemy(Dir.right, i, et);
	}
	
	public static void addEnemy(Dir d, int i, EType et) throws SlickException{
		Enemy e = null;
		Point p = new Point(0, 0);
		
		switch(et){
		case bat:
			e = new Bat(p);
			break;
		case demon:
			e = new Demon(p);
			break;
		case eyeball:
			e = new Eyeball(p);
			break;
		case lavagolem:
			e = new LavaGolem(p);
			break;
		case orc:
			e = new Orc(p);
			break;
		case wolf:
			e = new Wolf(p);
			break;
		case skull:
			e = new Skull(p);
			break;
		case lupa:
			e = new Lupa(p);
			break;
		default:
			break;
		}
		
		float x = 0, y = 0;
		
		switch(d){
		case up:
			x = r.nextInt(Game.MWIDTH-20) + 10;
			y = 0 - (e.getBounds().getHeight() * i);
			break;
			
		case down:
			x = r.nextInt(Game.MWIDTH-20) + 10;
			y = Game.MHEIGHT + (e.getBounds().getHeight() * i) + 10;
			break;
			
		case left:
			x = 0 - i*e.getBounds().getWidth() + 10;
			y = r.nextInt(Game.MHEIGHT - 100) + 50;
			break;
			
		case right:
			x = Game.MWIDTH + i*e.getBounds().getWidth() + 10;
			y = r.nextInt(Game.MHEIGHT - 100) + 50;
			break;
			
		default:
			break;
		}
		
		e.pos.setX(x);
		e.pos.setY(y);
		
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