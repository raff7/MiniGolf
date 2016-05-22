package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import geometry.Line;

public class Maths {

	public static final float EPSILON = 0.1f;
	public static float barryCentric(Vector3f p1, Vector3f p2,Vector3f p3,Vector2f pos){
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
			float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
				viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	public static float squareDistance(Vector2f p1, Vector2f p2) {
		// return the square distance between 2 2D points
		return ((p1.x-p2.x)*(p1.x-p2.x))+((p1.y-p2.y)*(p1.y-p2.y));
	}
	public static float distancePointLine(Vector2f point, Line line) {
		// return the distance between a point and a line
		return (float) Math.abs((line.a*point.x+line.b*point.y+line.c)/(Math.sqrt((line.a*line.a)+(line.b*line.b))));
	}
	public static boolean isInBetween(Vector2f point, Line line1, Line line2) {
		// check if a point is in between 2 parallel lines
		if(line1.m!=line2.m){
			//if(Math.abs(line1.m-line2.m)>EPSILON){
				//System.err.println("error, non parallel lines: line 1 : "+line1+" line2  "+line2);
			//}
				line1.m=line2.m;
		}

		float l1;
		float l2;
		if(line1.isVertical()){
			 l1= line1.pa.x - point.getX();
			 l2= line2.pa.x - point.getX();
		}else{
			float yLine1 = line1.m*point.getX()+line1.p;
			float yLine2 = line2.m*point.getX()+line2.p;
			
			l1= yLine1-point.getY();
			l2= yLine2-point.getY();
			

		}
		if(l1*l2<=0)
			return true;
		return false;
	}


}
