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
		float yLine1 = m*p1.getX()+p;
		float yLine2 = m*p2.getX()+p;
		
		float distance1 = yLine1-p1.getY();
		float distance2 = yLine2-p2.getY();
		//System.out.println(yLine2+"  "+p2.getY());
		//System.out.println("distances: "+distance1+"  "+distance2);
		if(distance1*distance2>0 || distance1 ==0)
			return true;
		else
			return false;
	}
}
