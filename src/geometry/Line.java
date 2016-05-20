package geometry;

import org.lwjgl.util.vector.Vector2f;

public class Line {
	
	public float p;
	public float m;
	private Vector2f pointOfLine;
	private boolean isVertical = false;
	
	public Line(Vector2f p1, Vector2f p2){
		pointOfLine = p1;
		if(p1.x==p2.x){
			isVertical=true;
		}
		else{
			m=(p1.y-p2.y)/(p1.x-p2.x);
			p= p1.y-m*p1.x;
			}
	}
	//p1 is the ball in 2D, p2 is the third point of the triangle(in 2D as well)
	public boolean liesOnSameSide(Vector2f p1, Vector2f p2){
		float distance1;
		float distance2;
		if(isVertical){
			//distance between line and third point of the triangle
			distance1 = pointOfLine.x - p2.getX();
			//distance between the line and the ball
			distance2 = pointOfLine.x - p1.getX();
		}
		else{
			//y of the line at the ball's x
			float yLine1 = m*p1.getX()+p;
			//y of the line at the x of the third point of the triangle 
			float yLine2 = m*p2.getX()+p;
			
			distance1 = yLine1-p1.getY();
			distance2 = yLine2-p2.getY();
		}
		if(distance1*distance2 >= 0)
			return true;
		else
			return false;
	}
	public String toString(){
		return "y="+m+"x+"+p;
	}
}
