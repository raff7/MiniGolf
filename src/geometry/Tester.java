package geometry;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;

public class Tester {
	public static void main(String[] arg){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for(int i=0;i<1000;i++){
			Triangle t = new Triangle(new Vector3f(getNumber(),getNumber(),getNumber()), new Vector3f(getNumber(),getNumber(),getNumber()), new Vector3f(getNumber(),getNumber(),getNumber()));
			triangles.add(t);
		}
	
		ArrayList<Triangle> indexes=new ArrayList<Triangle>();
		Triangle t1 = new Triangle(new Vector3f(0.54f,0.65f,0.987f), new Vector3f(5,0,0), new Vector3f(0,5,0));
		Triangle t2 = new Triangle(new Vector3f(0.54f,0.65f,0.987f), new Vector3f(-5,-10,0), new Vector3f(2,-11,0));
		//Triangle t3 = new Triangle(new Vector3f(0,3,0), new Vector3f(-6,0,0), new Vector3f(-7,1,0));
		triangles.add(t2);
		indexes = t1.getNeighbourTriangles(triangles);
		
		for(int i =0; i<triangles.size(); i++)
			System.out.println(triangles.get(i));
		System.out.println();
		System.out.println();
		System.out.println("number of connections: "+indexes.size());
		for(Triangle triangle: indexes){
			System.out.println(triangle);
		}
		
	}
	public static  float getNumber(){
		float number = (float) (Math.random()*20);
		return number;
	}
}
