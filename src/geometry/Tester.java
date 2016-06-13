package geometry;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;

public class Tester {
	public static void main(String[] arg){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for(int i=0;i<600;i++){
			Triangle t = new Triangle(new Vector3f((float)Math.random(),(float)Math.random(),(float)Math.random()), new Vector3f((float)Math.random(),(float)Math.random(),(float)Math.random()),new Vector3f((float)Math.random(),(float)Math.random(),(float)Math.random()));
			triangles.add(t);
		}
	}
}
