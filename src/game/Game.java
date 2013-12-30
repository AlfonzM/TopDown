package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{
	
	/*
	 * Stress test: 1400 objects before FPS starts dropping
	 */
	
	public static int GWIDTH = 720, GHEIGHT = 480;
	public static int MWIDTH = 1281, MHEIGHT = 873;
	public static int TS = 24;

	public Game() {
		super("Hammerfall");
		LoadingList.setDeferredLoading(false);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);
		this.addState(new Menu());
		this.addState(new Play());
		this.addState(new Loading());
		this.enterState(2);
	}
	
	public static void main(String args[]) throws SlickException{
		AppGameContainer appgc = new AppGameContainer(new Game());
		appgc.setDisplayMode(GWIDTH, GHEIGHT, false);
		appgc.start();
	}
}