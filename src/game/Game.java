package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

public class Game extends StateBasedGame{
	
	/*
	 * Stress test: 1400 objects before FPS starts dropping
	 */
	
	public static int GWIDTH = 720, GHEIGHT = 480;
	public static int MWIDTH = 1281, MHEIGHT = 873;
	public static int TS = 24;

	public Game() {
		super("Hammerfall");
		
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Menu());
		this.addState(new Play());
		this.enterState(0);
	}
	
	public static void main(String args[]) throws SlickException{
		AppGameContainer appgc = new AppGameContainer(new Game());
		appgc.setDisplayMode(GWIDTH, GHEIGHT, false);
		appgc.setTargetFrameRate(60);
		appgc.setShowFPS(false);
		appgc.start();
	}
}