package game.entities;

import game.Dir;
import game.GOType;
import game.Game;
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
	
	// stats attributes
	
	
	// level
	int level, experience, expToNextLevel;
	
	
	// skill
	Boolean isDashing = inv;
	float dashStartPoint, dashRange;  
	
	int defaultSpeed;
	
	public Player(Input input, Point p) throws SlickException {
		super(p);
		
		this.input = input;
		
		// move
		defaultSpeed = 3;
		speed = defaultSpeed;
		
		// health
		health = 100;
		
		// atk
		atkDelay = 300;
		
		// stats attributes
		
		// level
		level = 1;
		experience = 0;
		expToNextLevel = 100;
		
		// skill
		dashStartPoint = 0;
		dashRange = 100;
	}
	
	@Override
	public void render(Graphics g){
		// health
		try {
			healthGui = new Image("res/lifeBar.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.render(g);
		g.translate(0, -Game.HUDHEIGHT);
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
		
		g.drawString("Level: " + level, 10, 35);
		g.drawString("Exp: " + experience + "/" + expToNextLevel, 10, 55);
		
		g.translate(0, Game.HUDHEIGHT);
	}
	
	@Override
	public void update(int delta) throws SlickException{
		super.update(delta);
		
		// Pickables collision
		for(GameObject p : Play.objects.get(GOType.Pickable)){
			Pickable pickable = (Pickable) p;
			if(getBounds().intersects(pickable.getBounds())){
				pickable.pickedUp();
			}
		}
		
		// Controls handler
		controls();
	}
	
	public void controls() throws SlickException{
		// Movement Input handler
		if (input.isKeyDown(Input.KEY_A)) {
			move.x = -1;
		} else if (input.isKeyDown(Input.KEY_D)) {
			move.x = 1;
		} else if(!isDashing) {
			move.x = 0;
		}

		if (input.isKeyDown(Input.KEY_W)) {
			move.y = -1;
		} else if (input.isKeyDown(Input.KEY_S)) {
			move.y = 1;
		} else {
			move.y = 0;
		}

		// Attack input and attack animation handler
		int bx = 0, by = 0;
		if (input.isKeyDown(Input.KEY_I)) {
			isAttacking = true;
			by = -1;
			dir = Dir.up;
		}
		else if (input.isKeyDown(Input.KEY_K)) {
			isAttacking = true;
			by = 1;
			dir = Dir.down;
		}

		if (input.isKeyDown(Input.KEY_J)) {
			isAttacking = true;
			bx = -1;
			dir = Dir.left;
		}
		else if (input.isKeyDown(Input.KEY_L)) {
			isAttacking = true;
			bx = 1;
			dir = Dir.right;
		}

		if (isAttacking) {
			useAttack(bx, by);

			if (bx == 0 && by == 0) {
				isAttacking = false;
			}
		}
		
		// Dash
		if(!isDashing && input.isKeyPressed(Input.KEY_U)){
			isDashing = inv = true;
			dashStartPoint = 0;
			
			switch(dir){
			case left:
				move.x = -1;
				break;
				
			case right:
				move.x = 1;
				break;
				
//			case up:
//				move.y = 1;
//				break;				
//				
//			case down:
//				move.y = -1;
//				break;
			}
		}
		
		if(isDashing){
			speed = 20;
			
			if(Math.abs(dashStartPoint) > dashRange){
				isDashing = inv = false;
				speed = defaultSpeed;
				move.x = 0;
				move.y = 0;
			}
			else{
				dashStartPoint += move.x * speed;
			}
		}
		
		System.out.println(dashStartPoint + "  " + dashRange);
//
//		// Special Skills handler
//		if (input.isKeyPressed(Input.KEY_U)) {
//			isDashing = inv = true;
//			if (dir == Dir.left) {
//				move.x = 20;
////				pos.setX(pos.getX() - 100); // dashLeft
//			} else if (dir == Dir.right) {
//				pos.setX(pos.getX() + 100); // dashRight
//			} else if (dir == Dir.up) {
//				pos.setY(pos.getY() - 100); // dashUp
//			} else if (dir == Dir.down) {
//				pos.setY(pos.getY() + 100); // dashDown
//			}
//		}
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

		float newX = pos.getX() + (move.x * speed);
		if(canMoveX && Play.isInsideWallsX(newX)){
			canMoveX = false;
		}
		
		float newY = pos.getY() + (move.y * speed);
		if(canMoveY && Play.isInsideWallsY(newY)){
			canMoveY = false;
		}
		
		super.move(delta);
	}
	
	public void addExp(int exp){
		experience += exp;
		
		// level up
		if(experience >= expToNextLevel){
			level++;
			experience -= expToNextLevel;
			expToNextLevel += 50;
			new GameText("Level up!", pos, 50);
		}
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
		}
	}
	
	public void addHP(int hp){
		health += hp;
	}
	
	public void addMP(int mp){
		health += mp;
	}
}