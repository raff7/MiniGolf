package collision;

import org.lwjgl.util.vector.Vector3f;

public class Triangle {
	private Vector3f p1;
	private Vector3f p2;
	private Vector3f p3;
	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3){
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
	}
	public Vector3f getP1(){
		return p1;
	}
	public Vector3f getP2(){
		return p2;
	}
	public Vector3f getP3(){
		return p3;
	}
}
