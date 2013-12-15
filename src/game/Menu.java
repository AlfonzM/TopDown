package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState{

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Press enter to start.", 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			sbg.enterState(1);
		}
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
