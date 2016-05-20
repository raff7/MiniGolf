package geometry;

import org.lwjgl.util.vector.Vector2f;

public class Circle2D {
	
	//equation  (x-h)^2+(y+k)^2=r^2
	private float h;
	private float k;
	private float r;
	public Circle2D(Vector2f center,float r) {
		this.r = r;
		h=center.x;
		k=center.y;
	}
	public float getH() {
		return h;
	}
	public float getK() {
		return k;
	}
	public float getR() {
		return r;
	}
	public String toString(){
	
		return "(x-("+h+")^2+(y-("+k+"))^2="+(r*r);
	}
	

}
