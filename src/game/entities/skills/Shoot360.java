package game.entities.skills;

import game.GOType;
import game.Play;
import game.Sounds;
import game.entities.WizardBullet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Shoot360 extends Skill{

	public Shoot360() throws SlickException {
		super();
		name = "SHOOT 360";
		desc = "Fire 8 shots around you.";
		cost = 400;
	}

	@Override
	public void useSkill() throws SlickException {
		WizardBullet b1;
		
		for(int i = -1; i < 2 ; i++){
			for(int j = -1; j < 2; j++){
				if(!(i==0 && j==0)){
					b1 = new WizardBullet(Play.p.pos, i, j, GOType.Player);
					b1.health = 10;
					b1.sprite.setImageColor(1f, 1f, 0);
					Play.objects.get(GOType.Bullet).add(b1);					
				}
			}
		}
		
		Sounds.powershot.play();
	}

	@Override
	public void update(int delta) {
	}

	@Override
	public void render(Graphics g) {
	}

}
