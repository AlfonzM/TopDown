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
	
	// GAME OBJECTS
	public static Player p;
	public static Map<GOType, ArrayList<GameObject>> objects;
	public static ArrayList<GameText> gameTexts;
	
	public int wave, timeTillNextWave, restTime;
	
	// Particle effects
	public static ParticleSystem pSystem;
	public static ConfigurableEmitter emitterUnit, emitterFire;
	
	Random r;
	Image bg;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		r = new Random();
		
		wave = 0;
		restTime = 5000;
		timeTillNextWave = restTime;
		
		bg = new Image("res/bg.png");
		
		// Initialize arrays
		objects = new HashMap<GOType, ArrayList<GameObject>>();
		gameTexts = new ArrayList<GameText>();

		// Player
		p = new Wizard(gc.getInput(), new Point(Game.GWIDTH/2, Game.GHEIGHT/2));
		
		objects.put(GOType.Player, new ArrayList<GameObject>());
		objects.put(GOType.Enemy, new ArrayList<GameObject>());
		objects.put(GOType.Bullet, new ArrayList<GameObject>());
		objects.put(GOType.Pickable, new ArrayList<GameObject>());
		
		objects.get(GOType.Player).add(p);
		objects.get(GOType.Pickable).add(new Pickable(new Point(300, 300), PickableType.exp));
		
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
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// HUD
		renderHUD(g);
		g.translate(0, Game.HUDHEIGHT);
		bg.draw(0,0);
		
		for(ArrayList<GameObject> goArray : objects.values()){
			for(GameObject go : goArray ){
				go.render(g);
			}
		}
		
		// Game Texts
		for(GameText gt : gameTexts){
			gt.render(g);
		}
		
		// Particle system
		pSystem.render();
		
		// display xy coords
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		g.drawString(x+ " " + y, x, y-Game.HUDHEIGHT-20);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// check next wave
		if(getEnemies().isEmpty()){
			timeTillNextWave -= delta;
			if(timeTillNextWave < 0){
				// next wave
				timeTillNextWave = restTime;
				addAINorth(3);
				addRandomWest(5);
				addRandomEast(5);
				wave++;
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
		if(gc.getInput().isKeyPressed(Input.KEY_F1)){
			for(GameObject go : getEnemies()){
				go.isAlive = false;
			}
			
			addAIWest(3);
			
//			addRandomWest(5);
//			addRandomNorth(5);
//			addRandomEast(5);
		}
		if(gc.getInput().isKeyPressed(Input.KEY_F2)){
			for(GameObject go : getEnemies()){
				go.isAlive = false;
			}

			for(int i = 0; i < 30; i ++){
				EMoveRandom ee = new EMoveRandom(new Point(r.nextInt(Game.GWIDTH), r.nextInt(Game.GHEIGHT)));
				Play.objects.get(GOType.Enemy).add(ee);
			}
		}
		
		// Update particle system
		pSystem.update(delta);
	}
	
	public void renderHUD(Graphics g){
		g.drawString("Wave: " + wave, 150, 5);
		
		if(getEnemies().isEmpty())
			g.drawString("Next wave in " + (timeTillNextWave/1000) + "." + (timeTillNextWave%1000/100) + (timeTillNextWave%100/10), 150, 25);
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
			addEnemyAI(r.nextInt(417-305-Game.TS) + 305, 0 - i*Game.TS + 10 - Game.HUDHEIGHT);
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
			addEnemyRandom(r.nextInt(417-305-Game.TS) + 305, 0 - i*Game.TS + 10 - Game.HUDHEIGHT);
		}
	}
	
	public static ArrayList<GameObject> getEnemies(){
		return objects.get(GOType.Enemy);
	}
	
	public static boolean isInsideWallsX(float x){
		return x < 20 || x > Game.GWIDTH - 20 - Game.TS;
	}
	
	public static boolean isInsideWallsY(float y){
		return y < 95 || y > Game.GHEIGHT - Game.HUDHEIGHT - 40;
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

	public int getID() {		
		return 0;
	}
}