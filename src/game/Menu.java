package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

public class Menu extends BasicGameState{
	Image bg, bg2, title;
	
	float x;
	int counter;
	
	boolean disp;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("res/123.png");
		bg2 = new Image("res/123.png");
		title = new Image("res/title.png");
		new Fonts();
		new Sounds();
		
		x = bg.getWidth();
		
		disp = true;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg, x, -30);
		g.drawImage(bg2, x - bg.getWidth(), -30);
		
		g.drawImage(title, Game.GWIDTH/2 - title.getWidth()/2, 100);
		
//		Fonts.font48.drawString(Play.centerText("HAMMERFALL", Fonts.font48), 100, "HAMMERFALL");
		if(disp){
			Fonts.font16.drawString(Play.centerText("PRESS ENTER TO START", Fonts.font16), 350, "PRESS ENTER TO START");
		}
		
		Fonts.font8.drawString(Play.centerText("VERSION 1.1", Fonts.font8), Game.GHEIGHT - 20, "VERSION 1.1");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(x <= 0)
			x = bg.getWidth();
		else
			x-=0.5f;
		
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			sbg.enterState(1, new EmptyTransition(), new HorizontalSplitTransition());
			Play.initPlay(gc, sbg);	
		}
		
		if(counter < 600){
			counter += delta;
		}
		else{
			counter = 0;
			disp = (disp)?false:true;
		}
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
