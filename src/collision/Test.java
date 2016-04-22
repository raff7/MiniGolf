package collision;

import org.lwjgl.util.vector.Vector3f;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector3f v =  new Vector3f(1,1,0);
		System.out.println(v);
		Vector3f v2= new Vector3f(-1,0.5f,1);
		//v.negate(v2);
		//float dot =Vector3f.dot(v, v2);
		Vector3f norm =new Vector3f();
				v.normalise(v);
		System.out.println(v);
		System.out.println(v2);
		System.out.println(norm);
		//System.out.println(dot);
	}

}
