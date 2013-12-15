package game.entities;

import game.Play;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class EMoveToPlayer extends Enemy{

	public EMoveToPlayer(Point p) throws SlickException {
		super(p);
		defaultSpeed = 2.4f;
		speed = defaultSpeed;
		health = 2;

		updateTarget();
	}
	
	public EMoveToPlayer(Point p, float speed) throws SlickException {
		super(p);

		this.speed = speed;
		
		updateTarget();
	}
	
//	public void render(Graphics g){
//		super.render(g);
//		g.drawString("" + atkCounter, pos.getX() - 10, pos.getY() - 20);
//		g.drawString("atkng" + isAttacking, pos.getX() - 10, pos.getY() - 40);
//		g.drawString("canatk" + canAtk, pos.getX() - 10, pos.getY() - 60);
//	}

	@Override
	public void move(int delta) throws SlickException{
		// ai to follow player
		if(!Play.p.invisible){
			super.move(delta);
		}
		updateTarget();
	}
	
	public void updateTarget(){
		// update targetPoint and velocity if player moves
//		if(targetPoint.getX() != Play.p.pos.getX() || targetPoint.getY() != Play.p.pos.getY()){
			targetPoint.setX(Play.p.pos.getX());
			targetPoint.setY(Play.p.pos.getY());
			recalculateVector(targetPoint.getX(), targetPoint.getY());
//		}
	}

}