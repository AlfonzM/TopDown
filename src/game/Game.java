package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{
	
	/*
	 * Stress test: 1400 objects before FPS starts dropping
	 */
	
	public static int GWIDTH = 720, HUDHEIGHT = 60, GHEIGHT = 480 + HUDHEIGHT;
	public static int TS = 24;

	public Game() {
		super("Top Down Game");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
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
