package game.entities;
import java.awt.Color;
import java.awt.Event;

import game.Dir;
import game.Play;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;


/*
 * Unit with input
 */
public class Player extends Human{
	Input input;
	Animation normalAtk;
	Image healthGui;
	boolean inv = false;
	Boolean isDashing = inv;
	
	public Player(Input input, Point p) throws SlickException {
		super(p);
		
		this.input = input;
		
		// move
		speed = 3;
		
		// health
		health = 100;
		
		// atk
		atkDelay = 300;

	}
	
	@Override
	public void render(Graphics g){
		try {
			healthGui = new Image("res/lifeBar.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.render(g);
		int posText = 0;
		for(int ctr = 1; ctr<= health;ctr++){
			healthGui.setColor(2, 2.0f,2.0f,2.0f);
			g.drawImage(healthGui, 10+ctr, 10);
			posText = (10+ctr)/2;
		}
		if(health <= 10){
			posText = 10;
		}
	
			g.drawString(" "+health, posText-10, 10);
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		isDashing = inv = false;
		
		// Movement Input handler
		if(input.isKeyDown(Input.KEY_A)){
			move.x = -1;
		}
		else if(input.isKeyDown(Input.KEY_D)){
			move.x = 1;
		}
		else{
			move.x = 0;
		}
		
		if(input.isKeyDown(Input.KEY_W)){				
			move.y = -1;
		}
		else if(input.isKeyDown(Input.KEY_S)){				
			move.y = 1;
		}
		else{
			move.y = 0;
		}
		
		// Attack input and attack animation handler

		int attacktype = 1;
		
		if(attacktype == 0){
			if(input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_I)){
				useAttack();
			}
		}
		else{
			int bx = 0, by = 0;
			if(input.isKeyDown(Input.KEY_UP)){
				isAttacking = true;
				by = -1;
				dir = Dir.up;
			}
			else if(input.isKeyDown(Input.KEY_DOWN)){
				isAttacking = true;
				by = 1;
				dir = Dir.down;
			}
			
			if(input.isKeyDown(Input.KEY_LEFT)){
				isAttacking = true;
				bx = -1;
				dir = Dir.left;
			}
			else if(input.isKeyDown(Input.KEY_RIGHT)){
				isAttacking = true;
				bx = 1;
				dir = Dir.right;
			}
			
			if(isAttacking){
				useAttack(bx, by);

				if(bx == 0 && by == 0){
					isAttacking = false;
				}
			}
			
			//Special Skills handler
			if(input.isKeyPressed(Input.KEY_E)){ 
				isDashing = inv = true;
				if(dir == Dir.left){
					pos.setX(pos.getX()-100); //dashLeft
				}
				else if(dir == Dir.right){
					pos.setX(pos.getX()+100); //dashRight
				}
				else if(dir == Dir.up){
					pos.setY(pos.getY()-100); //dashUp
				}
				else if(dir == Dir.down){
					pos.setY(pos.getY()+100); //dashDown
				}
			}
		}
	}
	
	@Override
	public void move(int delta) throws SlickException{
		canMoveX = true;
		canMoveY = true;
		
		for(GameObject go : Play.getEnemies()){
			if(canMoveX && getNewXBounds().intersects(go.getBounds())){
				canMoveX = false;
			}
			
			if(canMoveY && getNewYBounds().intersects(go.getBounds())){
				canMoveY = false;
			}
		}
		
		super.move(delta);
	}
	
	// arrow keys
	public void useAttack(int bx, int by) throws SlickException{
		if(canAtk){
			attack(bx, by);
		}
	}
	
	// 1 key for attack
	@Override	
	public void useAttack() throws SlickException{		
		if(canAtk){			
			switch(dir){
			case up: useAttack(0, -1); break;
			case down: useAttack(0, 1); break;
			case left: useAttack(-1, 0); break;
			case right: useAttack(1, 0); break;
			case upleft: useAttack(-1, -1); break;
			case upright: useAttack(1, -1); break;
			case downleft: useAttack(-1, 1); break;
			case downright: useAttack(1, 1); break;
			default: break; 
			}
		}
	}
	
	// attack based on faced direction
	public void attack() throws SlickException{
		super.attack();
	}
	
	// attack based on arrow keys
	public void attack(int bx, int by) throws SlickException{
		super.attack();
	}
	
	@Override
	public void takeDamage(int dmg){
		if(inv != true){
			super.takeDamage(dmg);
			new GameText("-" + dmg, new Point(pos.getX(), pos.getY() - 30));
//			Play.addGameText("-" + dmg, new Point(pos.getX(), pos.getY() - 30));
		}
	}
}
