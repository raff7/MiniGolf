package collision;

import org.lwjgl.util.vector.Vector3f;

public class Plane{
		float[] equation = new float[4];
		Vector3f origin;
		Vector3f normal;
		
		Plane(Vector3f origin, Vector3f normal){
		this.normal = normal;
		this.origin = origin;
		equation[0] = normal.x;
		equation[1] = normal.y;
		equation[2] = normal.z;
		equation[3] = -(normal.x*origin.x + normal.y*origin.y + normal.z*origin.z);
		}
		//construct from triangle:
		Plane(Vector3f p1, Vector3f p2, Vector3f p3){
		Vector3f edgeP1P2 =new Vector3f(p2.getX()-p1.getX(),p2.getY()-p1.getY(),p2.getZ()-p1.getZ());
		Vector3f edgeP1P3 =new Vector3f(p3.getX()-p1.getX(),p3.getY()-p1.getY(),p3.getZ()-p1.getZ());
		Vector3f.cross(edgeP1P2,edgeP1P3,normal);
		normal.normalise(normal);
		origin = p1;
		equation[0] = normal.x;
		equation[1] = normal.y;
		equation[2] = normal.z;
		equation[3] = -(normal.x*origin.x + normal.y*origin.y + normal.z*origin.z);
		}
		public boolean isFrontFacingTo(Vector3f direction)  {
			double dot = Vector3f.dot(normal,direction);
			return (dot <= 0);
		}
		public double signedDistanceTo(Vector3f point){
			return (Vector3f.dot(point,normal)) + equation[3];
		}
}
