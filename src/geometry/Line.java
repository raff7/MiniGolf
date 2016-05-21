package geometry;

import org.lwjgl.util.vector.Vector2f;

public class Line {
	
	public float p;
	public float m;
	public float a,b,c;
	public Vector2f pa;
	public Vector2f pb;

	private boolean isVertical = false;
	
	public Line(Vector2f p1, Vector2f p2){
		pa = p1;
		pb= p2;
		a=pa.y-pb.y;
		b=pb.x-pa.x;
		c=(pa.x*pb.y-pb.x*pa.y);
		if(b==0){
			isVertical =true;
			
		}
		else{
			m=-(a/b);
			p= -(c/b);
			}
	}
	
	public boolean liesOnSameSide(Vector2f p1, Vector2f p2){
		float distance1;
		float distance2;
		if(isVertical()){
			distance1 = pa.x - p2.getX();
			distance2 = pa.x - p1.getX();
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


	public Line getPerpendicoular(Vector2f point){//to be fixed
			Line returned = null;
			if(!isVertical&& m!=0){
				returned = new Line(point,new Vector2f(point.x+1,point.y-1/m));
			}
			else if(!isVertical && m==0){
				returned = new Line(point, new Vector2f(point.x,point.y+1));
			}
			else if(isVertical){
				returned = new Line(point, new Vector2f(point.x+1,point.y));
			}
			return returned;
	}

	public boolean isVertical() {
		return isVertical;
	}
	public String toString(){
		if(!isVertical)
			return "y="+m+"x+"+p;
		return "Vertical line, x="+pa.x;
	}

}
