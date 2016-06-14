package geometry;

import java.io.Serializable;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class Triangle implements Serializable{
	private Vector3f p1;
	private Vector3f p2;
	private Vector3f p3;
	private Vector3f edgeP1P2;
	private Vector3f edgeP1P3;
	private Vector3f edgeP2P3;
	private float[] equation = new float[4];
	public Vector3f origin= new Vector3f();
	private Vector3f normal=new Vector3f();

	
	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3){
		//System.out.println();
		//System.out.println("points: "+ p1+" "+p2+" "+p3);
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
		
		edgeP1P2 =new Vector3f(p2.getX()-p1.getX(),p2.getY()-p1.getY(),p2.getZ()-p1.getZ());
		edgeP1P3 =new Vector3f(p3.getX()-p1.getX(),p3.getY()-p1.getY(),p3.getZ()-p1.getZ());
		edgeP2P3 = new Vector3f(p3.getX()-p2.getX(),p3.getY()-p2.getY(),p3.getZ()-p2.getZ());
		Vector3f.cross(edgeP1P2,edgeP1P3,normal);
		
		if(normal.length() !=0)
			normal.normalise();
		origin = this.p1;
		equation[0] = normal.x;
		equation[1] = normal.y;
		equation[2] = normal.z;
		equation[3] = -(normal.x*origin.x + normal.y*origin.y + normal.z*origin.z);
		equation[3]= origin.z;
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
		String s ="p1: "+p1+"  p2  "+p2+"  p3  " +p3+"    equation[3]:"+equation[3]+"   normal: "+normal+"  // \\  ";
		return s;
	}
	public Vector3f getNormal(){
		return normal;
	}
	public  float[] getEquation(){
		return equation;
	}
	public void upDateEquation(Vector3f p1){
		origin=p1;
		equation[3] = -(normal.x*origin.x + normal.y*origin.y + normal.z*origin.z);
	}
	public Vector3f getEdgeP1P2() {
		return edgeP1P2;
	}
	public Vector3f getEdgeP1P3() {
		return edgeP1P3;
	}
	public Vector3f getEdgeP2P3() {
		return edgeP2P3;
	}
	
	public Vector3f getCentroid(){
		float x = (p1.getX()+p2.getX()+p3.getX()) /3;
		float y = (p1.getY()+p2.getY()+p3.getY()) /3;
		float z = (p1.getZ()+p2.getZ()+p3.getZ()) /3;
		return new Vector3f(x,y,z);
	}
	
	public ArrayList<Integer> getNeighbourTriangles(ArrayList<Triangle> trianglesList){
		int counter;
		ArrayList<Integer> connectedTrianglesIndexes = new ArrayList<Integer>();
		for(int i=0; i<trianglesList.size(); i++){
			Triangle triangle = trianglesList.get(i);
			counter=0;
			
				if(triangle.getP1() == p1){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP1() == p2){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP1() == p3){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP2() == p1){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP2() == p2){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP2() == p3){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP3() == p1){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP3() == p2){
					connectedTrianglesIndexes.add(i);
				}
				if(triangle.getP3() == p3){
					connectedTrianglesIndexes.add(i);
				}
		}
		return connectedTrianglesIndexes;
	}
	

	public ArrayList<Integer> isConnected2(ArrayList<Triangle> trianglesList){
		
		ArrayList<Integer> connectedTrianglesIndexes = new ArrayList<Integer>();
		for(int i=0; i<trianglesList.size(); i++){
			
			Vector3f p1 = trianglesList.get(i).getP1();
			Vector3f p2 = trianglesList.get(i).getP2();
			Vector3f p3 = trianglesList.get(i).getP3();
			Vector3f a = new Vector3f();
			Vector3f b = new Vector3f();
			float epsilon = 0.0001f;
			
			//First Vertex
			Vector3f point = this.p1;
			//A=p1, B=p2
			a = p1;
			b = p2;
			float deltaX = (point.x - a.x)/(b.x - a.x);
			float deltaY = (point.y - a.y)/(b.y - a.y);
			float deltaZ = (point.z - a.z)/(b.z - a.z);
			System.out.println(deltaX+" "+deltaY+" "+deltaZ);

			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon){
				System.out.println("HELLO MOTHERFUCKER");
				connectedTrianglesIndexes.add(i);
				continue;
			}
			//A=p1, B=p3
			a = p1;
			b = p3;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon  ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			//A=p2, B=p3
			a = p2;
			b = p3;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			System.out.println("first not connected");
			
			
			//Second Vertex
			point = this.p2;
			//A=p1, B=p2
			a = p1;
			b = p2;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			//A=p1, B=p3
			a = p1;
			b = p3;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			//A=p2, B=p3
			a = p2;
			b = p3;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon  ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			System.out.println("second not connected");
			
			
			//Third Vertex
			point = this.p3;
			//A=p1, B=p2
			a = p1;
			b = p2;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			//A=p1, B=p3
			a = p1;
			b = p3;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			//A=p2, B=p3
			a = p2;
			b = p3;
			deltaX = (point.x - a.x)/(b.x - a.x);
			deltaY = (point.y - a.y)/(b.y - a.y);
			deltaZ = (point.z - a.z)/(b.z - a.z);
			if( Math.abs(deltaX-deltaY)<epsilon && Math.abs(deltaX-deltaZ)< epsilon && Math.abs(deltaY-deltaZ)< epsilon ){
				connectedTrianglesIndexes.add(i);
				continue;
			}
			System.out.println("third not connected");
		}
		return connectedTrianglesIndexes;
	}

}
