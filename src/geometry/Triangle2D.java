package geometry;

import org.lwjgl.util.vector.Vector2f;

import toolbox.Maths;

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
		if(distance(center)<=rad)
			return true;
		return false;
	}
	
	public float distance(Vector2f center) {
		if(contain(center)){
			return 0;
		}
		else{
			Vector2f closest = p1;
			float distance= Maths.squareDistance(p1, center);
			float d2 = Maths.squareDistance(p2,center);
			if(distance> d2){
				closest = p2;
				distance = d2;
			}
			d2 = Maths.squareDistance(p3,center);
			if(distance> d2){
				closest = p3;
				distance = d2;
			}
			Line lineToCheck=null;
			Line lineToCheck2=null;

			

			
			if(closest!=p1){
				lineToCheck = new Line(p1,closest);
			}
			if(closest != p2 ){
				if(lineToCheck == null)
					lineToCheck = new Line(p2,closest);
				else
					lineToCheck2 = new Line(p2,closest);
			}
			if(closest != p3){
				lineToCheck2 = new Line(p3, closest);
			}
			

			if(Maths.isInBetween(center,lineToCheck.getPerpendicoular(lineToCheck.pa),lineToCheck.getPerpendicoular(lineToCheck.pb))){
				return Maths.distancePointLine(center, lineToCheck);
			}else if(Maths.isInBetween(center,lineToCheck2.getPerpendicoular(lineToCheck2.pa),lineToCheck2.getPerpendicoular(lineToCheck2.pb))){
				return Maths.distancePointLine(center,lineToCheck2);
			}
			else
				return (float) Math.sqrt(distance);
		}
	}

	private boolean contain(Vector2f center) {
		if(line1.liesOnSameSide(center, p3)&&line2.liesOnSameSide(center, p2)&&line3.liesOnSameSide(center, p1))
			return true;
		return false;
	}



}
