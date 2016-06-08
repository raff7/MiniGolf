package toolbox;

import org.lwjgl.util.vector.Vector3f;

//This is a class dedicated to Vector and math operations.

public class Operation {
	private static float root;

	public static float getRoot(){
		return root;
	}
	public static void setRoot(float x){
		root=x;
	}
	public static Vector3f subtract(Vector3f v, Vector3f u){
		float x = v.getX() - u.getX();
		float y = v.getY() - u.getY();
		float z = v.getZ() - u.getZ();
		return new Vector3f(x,y,z);
	}

	public static Vector3f add(Vector3f v, Vector3f u){
		float x = v.getX() + u.getX();
		float y = v.getY() + u.getY();
		float z = v.getZ() + u.getZ();
		return new Vector3f(x,y,z);
	}

	public static Vector3f multiplyByScalar(float k, Vector3f v){
		float x = (float) v.getX()*k;
		float y = (float) v.getY()*k;
		float z = (float) v.getZ()*k;
		
		return new Vector3f(x,y,z);
	}
	
	public static Vector3f divideByScalar(float k, Vector3f v){
			float x = (float) v.getX()/k;
			float y = (float) v.getY()/k;
			float z = (float) v.getZ()/k;
		
			return new Vector3f(x,y,z);
		}

	public static Vector3f divideVector(Vector3f v, Vector3f u){
		float x = v.getX()/u.getX();
		float y = v.getY()/u.getY();
		float z = v.getZ()/u.getZ();
		return new Vector3f(x,y,z);
	}
	
	public static Vector3f multiplyVector(Vector3f v, Vector3f u){
		float x = v.getX()*u.getX();
		float y = v.getY()*u.getY();
		float z = v.getZ()*u.getZ();
		return new Vector3f(x,y,z);
	}
}

