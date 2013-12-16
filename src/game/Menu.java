package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState{
	Image bg;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("res/bg2.png");
		bg.setAlpha(0.5f);
		new Fonts();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		Fonts.font48.drawString(Play.centerText("HAMMERFALL", Fonts.font48), 100, "HAMMERFALL");
		Fonts.font16.drawString(Play.centerText("Press enter to start", Fonts.font16), 350, "Press enter to start");
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
