package game;

public class Vectors {
	public static float getRad(float x1, float newX, float y1, float newY){
		float rad = (float) (Math.atan2(newX - x1, newY - y1));
		return rad;
	}
}