package collision;

import org.lwjgl.util.vector.Vector3f;

/* This is a class dedicated to Vector and math operations.
 * It only contains static methods that are used in the CollisionHandler class
 */

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
		System.out.println("Values of x,y,z,k BEFORE :"+v.getX()+"  "+v.getY()+"  "+v.getZ()+"  "+k);
		float x = (float) v.getX()*k;
		float y = (float) v.getY()*k;
		float z = (float) v.getZ()*k;
		System.out.println("Values of x,y,z AFTER:"+x+"  "+y+"  "+z);
		return new Vector3f(x,y,z);
	}

	public static boolean getLowestRoot(float a, float b, float c, double maxR) {
		// Check if a solution exists
		double determinant = b*b - 4.0f*a*c;
		// If determinant is negative it means no solutions.
		if (determinant < 0.0f)
			return false;
		// calculate the two roots: (if determinant == 0 then
		// x1==x2 but let’s disregard that slight optimization)
		float sqrtD = (float) Math.sqrt(determinant);
		float r1 = (-b - sqrtD) / (2*a);
		float r2 = (-b + sqrtD) / (2*a);

		// Sort so x1 <= x2
		if (r1 > r2) {
			float temp = r2;
			r2 = r1;
			r1 = temp;
		}
		// Get lowest root:
		if (r1 > 0 && r1 < maxR){
			setRoot(r1);
			return true;
		}
		// It is possible that we want x2, this can happen if x1 < 0
		if (r2 > 0 && r2 < maxR) {
			setRoot(r2);
			return true;
			}
			// No (valid) solutions
			return false;
		}
	public static boolean checkPointInTriangle(Vector3f point,Vector3f pa,Vector3f pb, Vector3f pc){
		Vector3f e10=Operation.subtract(pb, pa);
		Vector3f e20=Operation.subtract(pc, pa);
		float a = Vector3f.dot(e10,e10);
		float b = Vector3f.dot(e10,e20);
		float c = Vector3f.dot(e20,e20);
		float ac_bb=(a*c)-(b*b);
		Vector3f vp = new Vector3f(point.x-pa.x, point.y-pa.y, point.z-pa.z);
		float d = Vector3f.dot(vp,e10);
		float e = Vector3f.dot(vp,e20);
		float x = (d*c)-(e*b);
		float y = (e*a)-(d*b);
		float z = x+y-ac_bb;
		return z < 0 && x >= 0 && y >= 0;
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

