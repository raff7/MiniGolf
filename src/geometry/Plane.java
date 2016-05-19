package geometry;

import org.lwjgl.util.vector.Vector3f;

public class Plane{
		float[] equation = new float[4];
		Vector3f origin = new Vector3f();
		public Vector3f normal = new Vector3f();
		
		 static boolean debug=false;

		public Plane(Vector3f origin, Vector3f normal){
			this.normal = normal;
			this.origin = origin;
			equation[0] = normal.x;
			equation[1] = normal.y;
			equation[2] = normal.z;
			equation[3] = -(normal.x*origin.x + normal.y*origin.y + normal.z*origin.z);
		}
		
		//construct from triangle:
		public Plane(Vector3f p1, Vector3f p2, Vector3f p3){
			Vector3f edgeP1P2 =new Vector3f(p2.getX()-p1.getX(),p2.getY()-p1.getY(),p2.getZ()-p1.getZ());
			Vector3f edgeP1P3 =new Vector3f(p3.getX()-p1.getX(),p3.getY()-p1.getY(),p3.getZ()-p1.getZ());
	//System.out.println("p1P2 "+edgeP1P2);
	//System.out.println("P1P3 "+edgeP1P3);

			Vector3f.cross(edgeP1P2,edgeP1P3,normal);
			
	//System.out.println(normal);
	//System.out.println();
	//System.out.println(edgeP1P2+"  "+edgeP1P3);
			normal.normalise(normal);
			origin = p1;
			equation[0] = normal.x;
			equation[1] = normal.y;
			equation[2] = normal.z;
			equation[3] = -(normal.x*origin.x + normal.y*origin.y + normal.z*origin.z);
			if(debug){
				System.out.println();	
				System.out.println("p1 "+p1);
				System.out.println("p2 "+p2);
				System.out.println("p3 "+p3);
				System.out.println("edge p1p2 "+edgeP1P2);
				System.out.println("egde p1p3 "+edgeP1P3);
				System.out.println("normal "+normal);
				debug=false;
			}
		}
		public boolean isFrontFacingTo(Vector3f direction){
			double dot = Vector3f.dot(normal,direction);
			return (dot <= 0);
		}
		public double signedDistanceTo(Vector3f point){
			//if(Math.abs((Vector3f.dot(point,normal)) + equation[3])<=1){
				//System.out.println(equation[0]+" "+equation[1]+" "+equation[2]+" "+equation[3]);
			//}
			return ((Vector3f.dot(point,normal)) + equation[3]);
		}
		/*public double signedDistanceTo(Vector3f point){
			double numerator = normal.x*point.x+normal.y*point.y+normal.z*point.z+equation[3];
			double denominator = Math.sqrt(Math.pow(normal.x,2) + Math.pow(normal.y,2) + Math.pow(normal.z,2));
			//System.out.println(normal.x+" "+normal.y+"  "+normal.z);
			//System.out.println("point : "+point);
			//System.out.println(numerator/denominator);
			return numerator/denominator;
		}*/
}
