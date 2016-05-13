package geometry;

import org.lwjgl.util.vector.Vector2f;

public class Line {
	
	private float p;
	private float m;
	
	public Line(Vector2f vector, Vector2f point){
		m=vector.y/vector.x;
		p=point.y-(m*point.x);
	}
	
	public boolean liesOnSameSide(Vector2f p1, Vector2f p2){
		
	}
}
