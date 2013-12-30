package game.entities.skills;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DurationSkill extends Skill{

	int counter;
	final int duration;
	boolean on;
		
	public DurationSkill(int d) throws SlickException {
		super();
		
		counter = 0;
		duration = d;
		on = false;
	}

	@Override
	public void useSkill() throws SlickException {
		on = true;
	}

	@Override
	public void update(int delta) {
		System.out.println(on + ": " + counter + "/" + duration);
		if(on){
			counter += delta;
			
			
			if(counter >= duration){
				end();
			}
		}
	}

	@Override
	public void render(Graphics g) {
	}
	
	public void end(){
		counter = 0;
		on = false;
	}

}
