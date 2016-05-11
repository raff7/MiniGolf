package collision;

import org.lwjgl.util.vector.Vector3f;

public class CheckCollision {
	public static double touchTriangle(Triangle triangle,Vector3f point){
		Vector3f normal = triangle.getNormal();
	/*	double numerator = normal.x*point.x+normal.y*point.y+normal.z*point.z+triangle.getConstant();
		double denominator = Math.sqrt(Math.pow(normal.x,2) + Math.pow(normal.y,2) + Math.pow(normal.z,2));
		//System.out.println(normal.x+" "+normal.y+"  "+normal.z);		
		System.out.println("point : "+point);
		System.out.println("  distance: "+numerator/denominator);
			return numerator/denominator;*/
		System.out.println("  distance: "+ (Vector3f.dot(point,normal) + triangle.getConstant()));
		return ((Vector3f.dot(point,normal)) + triangle.getConstant());
		}
}
