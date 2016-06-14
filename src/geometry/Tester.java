package geometry;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;

public class Tester {
	public static void main(String[] arg){
//		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
//		for(int i=0;i<20000;i++){
//			Triangle t = new Triangle(new Vector3f((float)Math.random(),(float)Math.random(),(float)Math.random()), new Vector3f((float)Math.random(),(float)Math.random(),(float)Math.random()),new Vector3f((float)Math.random(),(float)Math.random(),(float)Math.random()));
//			triangles.add(t);
//		}
//		ArrayList<Integer> indexs;
//		for(Triangle triangle:triangles)
//			indexs = triangle.isConnected2(triangles);
//		System.out.println("done");
		ArrayList<Integer> indexs=new ArrayList<Integer>();
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		Triangle t1 = new Triangle(new Vector3f(0,0,0), new Vector3f(5,0,0), new Vector3f(3,5,0));
		Triangle t2 = new Triangle(new Vector3f(0,0,0), new Vector3f(-5,0,0), new Vector3f(-3,5,0));
		Triangle t3 = new Triangle(new Vector3f(1,0,0), new Vector3f(-6,0,0), new Vector3f(-7,5,0));
	
		triangles.add(t1);
		triangles.add(t2);
		triangles.add(t3);
		indexs = t1.isConnected2(triangles);
		System.out.println(indexs);
		
	}
}
