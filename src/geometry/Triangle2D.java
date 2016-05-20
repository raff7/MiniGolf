package geometry;

import org.lwjgl.util.vector.Vector2f;

public class Triangle2D {
	private Vector2f p1;
	private Vector2f p2;
	private Vector2f p3;
	private Line line1;
	private Line line2;
	private Line line3;

	public Triangle2D(Vector2f p1, Vector2f p2, Vector2f p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		
		line1 = new Line(p1, p2);
		line2 = new Line(p1, p3);
		line3 = new Line(p2, p3);
	}
	
	public boolean ballIsIn(Vector2f center,float rad){
		Vector2f[] intersectPoint= new Vector2f[2];
		Circle2D circle = new Circle2D(center,rad);

		//check if it itersect line 1 between p1 and p2
		intersectPoint = line1.intersectPoint(circle);
		

		if(intersectPoint!=null    																				//check if there is an intesection
				&& ((intersectPoint[0].x<=Math.min(p1.x, p2.x) && intersectPoint[0].x>=Math.max(p1.x, p2.x)		//if there is it is stored in [0] (in case there is a second one will be store in [1], check now if its a valid collision
						&& intersectPoint[0].y<=Math.min(p1.y, p2.y)&& intersectPoint[0].y>=Math.max(p1.y, p2.y))
					|| (intersectPoint[1]!=null && (intersectPoint[1].x<=Math.min(p1.x, p2.x) && intersectPoint[1].x>=Math.max(p1.x, p2.x) //check if there is a second point, if there is check if is valid
						&& intersectPoint[1].y<=Math.min(p1.y, p2.y)&& intersectPoint[1].y>=Math.max(p1.y, p2.y))))){
			return true;
		}

		//check if it itersect line 2 between p1 and p3
		intersectPoint = line2.intersectPoint(circle);

				
		if(intersectPoint!=null    																				//check if there is an intesection
				&& ((intersectPoint[0].x<=Math.min(p1.x, p3.x) && intersectPoint[0].x>=Math.max(p1.x, p3.x)		//if there is it is stored in [0] (in case there is a second one will be store in [1], check now if its a valid collision
						&& intersectPoint[0].y<=Math.min(p1.y, p3.y)&& intersectPoint[0].y>=Math.max(p1.y, p3.y))
					|| (intersectPoint[1]!=null && (intersectPoint[1].x<=Math.min(p1.x, p3.x) && intersectPoint[1].x>=Math.max(p1.x, p3.x) //check if there is a second point, if there is check if is valid
						&& intersectPoint[1].y<=Math.min(p1.y, p3.y)&& intersectPoint[1].y>=Math.max(p1.y, p3.y))))){
			return true;
		}
		//check if it itersect line 3 between p2 and p3
		intersectPoint = line3.intersectPoint(circle);


		
		if(intersectPoint!=null    																				//check if there is an intesection
				&& ((intersectPoint[0].x<=Math.min(p2.x, p3.x) && intersectPoint[0].x>=Math.max(p2.x, p3.x)		//if there is it is stored in [0] (in case there is a second one will be store in [1], check now if its a valid collision
						&& intersectPoint[0].y<=Math.min(p2.y, p3.y)&& intersectPoint[0].y>=Math.max(p2.y, p3.y))
					|| (intersectPoint[1]!=null && (intersectPoint[1].x<=Math.min(p2.x, p3.x) && intersectPoint[1].x>=Math.max(p2.x, p3.x) //check if there is a second point, if there is check if is valid
						&& intersectPoint[1].y<=Math.min(p2.y, p3.y)&& intersectPoint[1].y>=Math.max(p2.y, p3.y))))){
			return true;
		}
		
		return false;
		
	}

}
