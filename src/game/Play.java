package game;

import game.entities.EMoveRandom;
import game.entities.EMoveToPlayer;
import game.entities.GameObject;
import game.entities.GameText;
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
	// Values
	static int TILESIZE = 20;
	static int goCount = 0;
	
	// GAME OBJECTS
	public static Player p;
	public static Map<GOType, ArrayList<GameObject>> objects;
	public static ArrayList<GameText> gameTexts;
	
	// Particle effects
	public static ParticleSystem pSystem;
	public static ConfigurableEmitter emitterUnit, emitterFire;
	
	Random r;
	Image bg;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		objects = new HashMap<GOType, ArrayList<GameObject>>();
		gameTexts = new ArrayList<GameText>();
		
		objects.put(GOType.Player, new ArrayList<GameObject>());
		objects.put(GOType.Enemy, new ArrayList<GameObject>());
		objects.put(GOType.Bullet, new ArrayList<GameObject>());
//		objects.put(GOType.GameText, new ArrayList<GameObject>());
		
		p = new Wizard(gc.getInput(), new Point(Game.GWIDTH/2, Game.GHEIGHT/2));
		
		objects.get(GOType.Player).add(p);
		
		r = new Random();
		bg = new Image("res/bg.png");
		
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
		bg.draw(0,0);
		
		for(ArrayList<GameObject> goArray : objects.values()){
			for(GameObject go : goArray ){
				go.render(g);
			}
		}
		
		for(GameText gt : gameTexts){
			gt.render(g);
		}
		
		pSystem.render();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for(ArrayList<GameObject> goArray : objects.values()){
			for (Iterator<GameObject> iterator = goArray.iterator(); iterator.hasNext(); ) {
				GameObject go = iterator.next();
				if (!go.isAlive) {
					iterator.remove();
				} else
					go.update(delta);
			}
		}
		
		// game texts
		for (Iterator<GameText> iterator = gameTexts.iterator(); iterator.hasNext(); ) {
			GameText gt = iterator.next();
			if (!gt.isAlive) {
				iterator.remove();
			} else
				gt.update(delta);
		}
		
		// refresh
		if(gc.getInput().isKeyPressed(Input.KEY_F1)){
			for(GameObject go : getEnemies()){
				go.isAlive = false;
			}
			for(int i = 0; i < 10; i ++){
				addEnemyAI(0 - i*Game.TS + 10, Game.GHEIGHT/2);
			}
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
		
		pSystem.update(delta);
	}
	
	public void addEnemyAI(float x, float y) throws SlickException{
		EMoveToPlayer ee = new EMoveToPlayer(new Point(x, y));
		objects.get(GOType.Enemy).add(ee);
	}
	
	public static void addGameText(String value, Point p){
//		GameText t = new GameText(p);
//		gameTexts.add(t);
	}
	
	public static ArrayList<GameObject> getEnemies(){
		return objects.get(GOType.Enemy);
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