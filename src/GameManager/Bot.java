package GameManager;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import collision.BoundingBox;
import collision.Operation;
import entities.Ball;
import entities.Camera;
import entities.Course;
import entities.Entity;
import GameManager.Player;
import geometry.Line;
import terrains.Terrain;

public class Bot extends Player{
	ArrayList<Entity> ground = new ArrayList<Entity>();
	ArrayList<Entity> obstaclesList = new ArrayList<Entity>();
	Vector3f hole = new Vector3f(0.81f,10.2f,16.26f);
	Vector3f straightShot;
	private final float CONSTANT = 30;
	public Entity collisionObject;
	
	public Bot(Ball ball, Course course){
		super.setBall(ball);
		super.setCamera(new Camera(ball));
		ground = course.getEntities();
		obstaclesList = new ArrayList<Entity>();
		obstaclesList.add(course.getEntities().get(0));
		//obstaclesList.add(course.getEntities().get(1));
		//obstaclesList.add(course.getEntities().get(2));
	}
	
	
	public void shoot(){
		System.out.println("shoot");
		straightShot = new Vector3f();
		straightShot = Vector3f.sub(hole, super.getBall().getPosition(), straightShot);
		
		if(isPathClear(getBall().getPosition())){
			
					//calculate the offset to add to the straightShot because of the friction\\
			//Version 3
			straightShot.normalise();
			Ball testBall = new Ball(getBall().getModel(), getBall().getPosition(), ground, getBall().getRotX(), getBall().getRotY(), getBall().getRotZ(), getBall().getScale());
			float a=0;
			float b = 100;
			float d = Operation.subtract(getBall().getPosition(),hole).length();
		
			float d2;
			Vector3f shot = new Vector3f(straightShot.x, straightShot.y ,straightShot.z);
			testBall.setPosition(getBall().getPosition());
			
			while( testBall.getDistanceFromHole(hole) > 2){
				System.out.println("ball: "+testBall.getPosition());
				//System.out.println("while loop");
				//System.out.println("distance from hole: "+testBall.getDistanceFromHole(hole));
				
				float power = (a+b)/2;
				testBall.setPosition(getBall().getPosition());
				shot = new Vector3f(straightShot.x, straightShot.y ,straightShot.z);
			//System.out.println("shot: "+shot);
				shot = Operation.multiplyByScalar(power, shot);
				testBall.setVelocity(shot);
				//System.out.println("shot: "+shot);
				System.out.println("fake ball position before: "+testBall.getPosition());
				//System.out.println("real ball position: "+ getBall().getPosition());
				testBall.simulateShot(ground);
				System.out.println("fake ball position after: "+testBall.getPosition());
			
				d2=Operation.subtract(getBall().getPosition(),testBall.getPosition()).length();
				
			//	System.out.println(getBall().getPosition()+"  "+testBall.getPosition());
				System.out.println("d: "+d+ "d2: "+d2);
				if(d2 > d){
					b = power;
				}else{
					a= power;
				}
			
				System.out.println(power);
			}
			straightShot=Operation.multiplyByScalar(power, straightShot);
			 getBall().setVelocity(straightShot);
			 
		}else{
			System.out.println();
			System.out.println("path stuck try something else");
			//try MinX side	
			System.out.println("colObj "+collisionObject.getPosition());
			Vector3f newDestination = new Vector3f(collisionObject.getPosition().x+CONSTANT, collisionObject.getPosition().y, collisionObject.getPosition().z);
			Vector3f otherShot = Operation.subtract(newDestination, getBall().getPosition());
			Vector3f newPosition = Operation.add(getBall().getPosition(), otherShot);
			System.out.println("new pos: "+newPosition);
			
			 super.getBall().setVelocity(otherShot);
			/*if(isPathClear(newPosition)){
				System.out.println("other shot");
				 super.getBall().setVelocity(otherShot);
			}*/
		}	
	}
	
	
	public boolean isPathClear(Vector3f position){
		
		float[] planeEquation = getPlaneEquation(position);
		
		for(Entity entity: obstaclesList){
			BoundingBox box = entity.getBox();
			Vector3f entityHole = Operation.subtract(hole, entity.getPosition());
			Vector3f ballHole = Operation.subtract(hole, getBall().getPosition());
			
			if( ! lieOnSameSideOfPlane(planeEquation, box.getMinX(), box.getMaxX()) ){
				System.out.println("dot: "+Vector3f.dot(entityHole, ballHole));
				if(Vector3f.dot(entityHole, ballHole) > 0){
					System.out.println("1st if");
					System.out.println("minX: "+box.getMinX()+"   maxX: "+box.getMaxX());
					System.out.println("Collides with: "+entity.getPosition());
					collisionObject = entity;
					return false;
				}
			}
			if( ! lieOnSameSideOfPlane(planeEquation, box.getMinZ(), box.getMaxZ()) ){
				
				System.out.println("dot: "+Vector3f.dot(entityHole, ballHole));
				if(Vector3f.dot(entityHole, ballHole) >0){
					System.out.println("2nd if");
					System.out.println("minZ: "+box.getMinZ()+"   maxZ: "+box.getMaxZ());
					System.out.println("Collides with: "+entity.getPosition());
					collisionObject = entity;
					return false;
				}
			}
			if( ! lieOnSameSideOfPlane(planeEquation, box.getMinY(), box.getMaxY()) ){
				
				System.out.println("dot: "+Vector3f.dot(entityHole, ballHole));
				if(Vector3f.dot(entityHole, ballHole) >0){
					System.out.println("3rd if");
					System.out.println("minY: "+box.getMinY()+"   maxY: "+box.getMaxY());
					System.out.println("Collides with: "+entity.getPosition());
					collisionObject = entity;
					return false;
				}
			}
			
		}
		return true;	
	}
	
	public float[] getPlaneEquation(Vector3f position){
		
		//get the vector from ball to hole
		Vector3f path = new Vector3f();
		Vector3f.sub(position, hole, path);
		
		//get the a vertical vector to help us determining the plane
		Vector3f verticalVector = new Vector3f(0,1,0);
				
		//So now we have 2 vectors that determine a plane. So we are looking for the normal of that plane
		//to get the plane's equation ax+by+cz+d=0 where normal=(a,b,c)
		Vector3f planeNormal = new Vector3f();
		Vector3f.cross(path, verticalVector, planeNormal);
		planeNormal.normalise();
				
		//Now we have to find d. To do this we are going to plug in a point
		// of the plane and compute d. => d = -(ax+by+cz)
		float a = planeNormal.x;
		float b = planeNormal.y;
		float c = planeNormal.z;
		Vector3f ball = position;
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
	
	// return the number of time that the friction  has been applied
	public int getNumberOfFrictionCall(Vector3f shot){
		int numberOfFrictionCall = 0;
		Vector3f fakeVelocity = new Vector3f(shot.x, shot.y,shot.z);
		
		while(Math.abs(fakeVelocity.length()) > getBall().getMinimalSpeed()){
			fakeVelocity.scale(getBall().getFriction());
			numberOfFrictionCall++;
		}
		return 	numberOfFrictionCall++;
	}
	//return the min velocity we reached after a shot because of the friction
	public Vector3f getMinVelocityDueToFriction(Vector3f shot){
		Vector3f velocity = new Vector3f(shot.x, shot.y, shot.z);
		
		while(Math.abs(velocity.length()) > getBall().getMinimalSpeed()){
			velocity.scale(getBall().getFriction());
		}
		return velocity;
	}
}
