package game;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Loading extends BasicGameState{

	private String lastLoaded;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		g.drawString("Loaded: " + lastLoaded, 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		if(LoadingList.get().getRemainingResources() > 0){
			DeferredResource nextResource = LoadingList.get().getNext();
			try {
				nextResource.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastLoaded = nextResource.getDescription();
			System.out.println(lastLoaded);
		}
		else{
			lastLoaded = "Done.";
			sbg.enterState(0);
		}
	}

	@Override
	public int getID() {
		return 2;
	}

}
