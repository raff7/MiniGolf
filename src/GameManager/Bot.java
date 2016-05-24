package GameManager;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import collision.BoundingBox;
import entities.Ball;
import entities.Camera;
import entities.Entity;
import GameManager.Player;
import geometry.Line;
import terrains.Terrain;

public class Bot extends Player{
	ArrayList<Entity> obstaclesList;
	Hole hole;
	
	public Bot(Ball ball, Terrain terrain){
		super.setBall(ball);
		super.setCamera(new Camera(ball));
	}
	
	public void shoot(){
		if(isPathClear()){
			Vector3f straightShot = new Vector3f();
			 straightShot = Vector3f.sub(hole.getPositon(), super.getBall().getPosition(), straightShot);
			 super.getBall().setVelocity(straightShot);
		}else{
			
		}
			
	}
	
	public boolean isPathClear(){
		
		float[] planeEquation = getPlaneEquation();
		
		for(Entity entity: obstaclesList){
			BoundingBox box = entity.getBox();
			if( ! lieOnSameSideOfPlane(planeEquation, box.getMinX(), box.getMaxX()) );
				return false;
			if( ! lieOnSameSideOfPlane(planeEquation, box.getMinY(), box.getMaxY()) );
				return false;
			if( ! lieOnSameSideOfPlane(planeEquation, box.getMinZ(), box.getMaxZ()) );
				return false;
		}
		return true;
		
		
	}
	
	public float[] getPlaneEquation(){
		
		//get the vector from ball to hole
		Vector3f path = new Vector3f();
		Vector3f.sub(super.getBall().getPosition(), hole.getPosition(), path);
				
		//get the a vertical vector to help us getting the plane
		Vector3f verticalVector = new Vector3f(0,1,0);
				
		//So now we have 2 vectors that determine a plane. So we are looking for the normal of that plane
		//to get the plane's equation ax+by+cz+d=0 where n=(a,b,c)
		Vector3f planeNormal = new Vector3f();
		planeNormal = Vector3f.cross(path, planeNormal, planeNormal);
		planeNormal.normalise();
				
		//Now we have to find d. To do this we are going to plug in a point
		// of the plane and compute d. => d = -(ax+by+cz)
		float a = planeNormal.x;
		float b = planeNormal.y;
		float c = planeNormal.z;
		Vector3f ball = super.getBall().getPosition();
		float d = -(a*ball.x +b*ball.y+ c*ball.z );
				
		float[] planeEquation ={a,b,c,d};
		
		return planeEquation;
	}
	
	public boolean lieOnSameSideOfPlane(float[] planeEquation, Vector3f p1, Vector3f p2){
		float a = planeEquation[0];
		float b = planeEquation[1];
		float c = planeEquation[2];
		float d = planeEquation[3];
		//if those 2 values have the same sign => points are on the same side
		float result1 = a*p1.x + b*p1.y + c*p1.z +d;
		float result2 = a*p2.x + b*p2.y + c*p2.z + d;
		
		if(result1 * result2 > 0)
			return true;
		else 
			return false;
	}
}
