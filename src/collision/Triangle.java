package collision;

import org.lwjgl.util.vector.Vector3f;

public class Triangle {
	private Vector3f p1;
	private Vector3f p2;
	private Vector3f p3;
	
	private Vector3f normal=new Vector3f();
	private float constant;
	
	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3){
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
		
		/*Vector3f edgeP1P2 =new Vector3f(p2.getX()-p1.getX(),p2.getY()-p1.getY(),p2.getZ()-p1.getZ());
		Vector3f edgeP1P3 =new Vector3f(p3.getX()-p1.getX(),p3.getY()-p1.getY(),p3.getZ()-p1.getZ());
		Vector3f.cross(edgeP1P2,edgeP1P3,normal);
		constant = -(normal.x*p1.x + normal.y*p1.y + normal.z*p1.z);*/
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
	public void setP1(Vector3f v){
		p1=v;
	}
	public void setP2(Vector3f v){
		p2=v;
	}
	public void setP3(Vector3f v){
		p3=v;
	}
	
	public String toString(){
		String s ="p1: "+p1+"  p2  "+p2+"  p3  " +p3;
		return s;
	}
	public Vector3f getNormal(){
		return normal;
	}
	public  float getConstant(){
		return constant;
	}
}
