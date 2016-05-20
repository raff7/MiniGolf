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
	
	public boolean liesOnSameSide(Vector2f p1, Vector2f p2){
		float distance1;
		float distance2;
		if(isVertical){
			distance1 = pointOfLine.x - p2.getX();
			distance2 = pointOfLine.x - p1.getX();
		}
		else{
			float yLine1 = m*p1.getX()+p;
			float yLine2 = m*p2.getX()+p;
			
			distance1 = yLine1-p1.getY();
			distance2 = yLine2-p2.getY();
		}
		if(distance1*distance2>=0){	
			return true;
		}
		else
			return false;
	}
	public String toString(){
		return "y="+m+"x+"+p;
	}

	public Vector2f[] intersectPoint(Circle2D circle) {
		Vector2f[] result = new Vector2f[2];
		float a= (m*m)+1;
		float b= (2*m*(p-circle.getK())-2*(circle.getH()));
		float c= (circle.getH()*circle.getH())+p-circle.getK()-(circle.getR()*circle.getR());
		
		
		float delta = (b*b)-4*a*c;
		
		if(delta<0)
			return null;
		else if(delta==0){
			result[0]=new Vector2f();
			result[0].x=(float)-b/2*a;
			result[0].y=m*(result[0].x)+p;
			result[1]=null;
		}else if(delta>0){
			result[0]=new Vector2f();

			result[0].x=(float)((-b+Math.sqrt(delta))/2*a);
			result[0].y=m*(result[0].x)+p;
			
			result[1]=new Vector2f();

			result[1].x=(float)((-b-Math.sqrt(delta))/2*a);
			result[1].y=m*(result[1].x)+p;
		}
		return result;
		
	}
}
