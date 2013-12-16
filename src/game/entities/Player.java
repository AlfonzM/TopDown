package game.entities;

import game.Dir;
import game.Fonts;
import game.GOType;
import game.Game;
import game.HUD;
import game.MyColors;
import game.Play;
import game.Play.GameState;
import game.ScreenShake;
import game.Sounds;
import game.entities.skills.Blank;
import game.entities.skills.ExpUp;
import game.entities.skills.GoldUp;
import game.entities.skills.Haste;
import game.entities.skills.Skill;
import game.entities.skills.WindWalk;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
	public boolean invulnerable = false;
	public boolean invisible = false;
	
	// stats
	public int level;
	public int experience;
	public int expToNextLevel;
	public int gold;
	public int goldToAdd, goldToSubtract;
	
	// skill
	public Skill[] skills;
	public boolean[] canUseSkill;
	public boolean isDashing = invulnerable;
	public float dashStartPoint, dashRange;
	
	// render points
	public static float renderX, renderY;
	
	// health
	public int maxHealth;
	
	public Player(Input input, Point p) throws SlickException {
		super(p);
		
		this.input = input;
		
		// move
		defaultSpeed = 3;
		speed = defaultSpeed;
		
		// health
		maxHealth = 100;
		health = maxHealth;
		
		// atk
		atkDelay = 300;
		
		// stats attributes
		gold = 500;
		
		// level
		level = 1;
		experience = 0;
		expToNextLevel = 100;
		
		// skills
		skills = new Skill[4];
		canUseSkill = new boolean[4];
		for(int i = 0 ; i < skills.length; i++){
			skills[i] = new Blank();
			canUseSkill[i] = true;
		}
		dashStartPoint = 0;
		dashRange = 100;
	}
	
	@Override
	public void render(Graphics g){
//		super.render(g);
		updateAnimation();
		
		renderX = Game.GWIDTH/2;
		renderY = Game.GHEIGHT/2;
		
		// RENDER X
		if(pos.getX() - Game.GWIDTH/2 <= 0){
			// manual
			renderX = pos.getX();
		}
		else if(pos.getX() + Game.GWIDTH/2 > Game.MWIDTH){
			// manual
			renderX = Play.offsetX + pos.getX();
		}
		else{
			// center
			Play.offsetX = Game.GWIDTH/2 - pos.getX();
			renderX = Game.GWIDTH/2;
		}
		
		// RENDERY
		if(pos.getY() - Game.GHEIGHT/2 <= 0){
			// manual
			renderY = pos.getY();
		}
		else if(pos.getY() + Game.GHEIGHT/2 > Game.MHEIGHT){
			// manual
			renderY = Play.offsetY + pos.getY();
		}
		else{
			// center
			Play.offsetY = Game.GHEIGHT/2 - pos.getY();
			renderY = Game.GHEIGHT/2;
		}
		
		if(invisible){
			Image i = animation.getCurrentFrame().copy();
			i.setAlpha(0.5f);
			g.drawImage(i, renderX, renderY);
		}
		else if(animation != null){
			if(animation == aAtkLeft){
				g.drawAnimation(animation, renderX, renderY);
			}
			else{
				g.drawAnimation(animation, renderX, renderY);
			}
		}
		
		// Render skills
		for(int i = 0 ; i < skills.length ; i++){
			skills[i].render(g);
		}
		
		g.setColor(Color.white);
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
		
		// Update skills
		for(int i = 0 ; i < skills.length; i++){
			skills[i].update(delta);
		}
		
		// Controls handler
		controls();
		
		// Update gold
		int inc = 2;
		if(goldToAdd > 0){
			if(goldToAdd > inc){
				goldToAdd -= inc;
				gold += inc;
			}
			else{
				gold += goldToAdd;
				goldToAdd = 0;
			}
		}
		
		if(goldToSubtract > 0){
			if(goldToSubtract > inc){
				goldToSubtract -= inc;
				gold -= inc;
			}
			else{
				gold -= goldToSubtract;
				goldToSubtract = 0;
			}
		}
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
		
		// Skills handler
		if(Play.gameState != GameState.rest){
			if(Play.p.input.isKeyPressed(Input.KEY_Q)){
				useSkill(0);
			}
			if(Play.p.input.isKeyPressed(Input.KEY_E)){
				System.out.println("use e");
				useSkill(1);
			}
			if(Play.p.input.isKeyPressed(Input.KEY_U)){
				useSkill(2);
			}
			if(Play.p.input.isKeyPressed(Input.KEY_O)){
				useSkill(3);
			}			
		}
		
		// Dash
//		if(!isDashing && input.isKeyPressed(Input.KEY_U)){
//			isDashing = invulnerable = true;
//			dashStartPoint = 0;
//			
//			switch(dir){
//			case left:
//				move.x = -1;
//				break;
//				
//			case right:
//				move.x = 1;
//				break;
//				
//			default:
//				break;
//				
//			case up:
//				move.y = 1;
//				break;				
//				
//			case down:
//				move.y = -1;
//				break;
//			}
//		}
		
		if(isDashing){
			speed = 20;
			
			if(Math.abs(dashStartPoint) > dashRange){
				isDashing = invulnerable = false;
				speed = defaultSpeed;
				move.x = 0;
				move.y = 0;
			}
			else{
				dashStartPoint += move.x * speed;
			}
		}
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
	
	private void useSkill(int i) throws SlickException {
		if(canUseSkill[i] && skills[i].name != ""){
			skills[i].useSkill();
			canUseSkill[i] = false;
			
			switch(skills[i].name){
			case "HASTE":
				HUD.addTimer(Haste.duration, i);
				break;
				
			case "SHADOW WALK":
				HUD.addTimer(WindWalk.duration, i);
				break;
				
			case "INCREASED XP":
				HUD.addTimer(ExpUp.duration, i);
				break;
				
			case "GREED IS GOOD":
				HUD.addTimer(GoldUp.duration, i);
				break;
				
			default:
				break;
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
	
	public void addExp(float f){
		experience += f;
		
		// level up
		if(experience >= expToNextLevel){
			level++;
			experience -= expToNextLevel;
			expToNextLevel += 50;
			new GameText("LEVEL UP!!!", pos, 150, MyColors.green, Fonts.font24);
			
			Sounds.levelup.play();
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
		Sounds.shoot2.play();
	}
	
	// attack based on arrow keys
	public void attack(int bx, int by) throws SlickException{
		super.attack();
	}
	
	@Override
	public void takeDamage(int dmg){
		if(invulnerable != true){
			super.takeDamage(dmg);
			new GameText("-" + dmg, new Point(pos.getX(), pos.getY() - 30), Color.red);
		}
	}
	
	@Override
	public void die() throws SlickException{
		super.die();
		health = 0;
		ScreenShake.shake();
		bounds.setWidth(0);
		bounds.setHeight(0);
		
		Sounds.stomp.play();
	}
}