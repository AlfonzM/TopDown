package game;

public class ScreenShake {
	static int mode;
	
	// screenshake values
	static final float defaultOffset = 25;
	static float offset;
	static float shakeSpeed = 8;
	static int duration = 500; // in ms
	public static float offsetX;
	public static float offsetY;
	static boolean screenShake, goingUp;
	
	static boolean on;
	
	public ScreenShake(){
		mode = 1;
		
		offsetX = 0;
		offsetY = 0;
		screenShake = false;
		goingUp = true;
	}
	
	public static void shake(){
		mode = 1;
		
		offsetX = 0;
		offsetY = 0;
		screenShake = false;
		goingUp = true;
		
		on = true;
		
		offset = defaultOffset;
	}
	
	public static void update(int delta){
		if(on){
			screenShake();

			offset -= 0.7f;
			if(offset <= 0.0f){
				on = false;
				
				offsetX = 0;
				offsetY = 0;
				
				offset = defaultOffset;
			}
		}
	}
	
	static private void screenShake(){
		switch(mode){
		case 1:			
			if(goingUp)
				offsetY -= shakeSpeed;
			else
				offsetY += shakeSpeed;

			if(Math.abs(offsetY) > offset)
				goingUp = false;
			else if(Math.abs(offsetY) <= 0){
				goingUp = true;
				screenShake = false;
				offsetY = 0;
			}
			break;
			
		case 2:
			if(goingUp)
				offsetX -= shakeSpeed;
			else
				offsetX += shakeSpeed;
			
			if(Math.abs(offsetX) > offset)
				goingUp = false;
			else if(Math.abs(offsetX) <= 0){
				// end
				goingUp = true;
				screenShake = false;
				offsetX = 0;
			}
			
			break;
			
		case 3:
			break;
		
		default:
			break;
		}
	}
}
