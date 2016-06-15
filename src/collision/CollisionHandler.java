package collision;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import GameManager.Player;
import entities.Ball;
import entities.Entity;
import geometry.Triangle;
import geometry.Triangle2D;
import toolbox.Maths;
import toolbox.MousePickerTraveler;
import toolbox.Operation;


public class CollisionHandler {
	
	static Vector3f travelerLocation = null ;
	
	ArrayList<Ball> ballsList = new ArrayList<Ball>();
	
	//First step using bounding boxes (WIP)
	/*public static ArrayList<Entity> getHitObstacles(ArrayList<Entity> obstacles, Ball ball){
		ArrayList<Entity> hitObstacles = new ArrayList<Entity>();
		boolean xIntersection = false, yIntersection=false, zIntersection = false;

		for(int i=0; i<obstacles.size(); i++){
			Entity obstacle = obstacles.get(i);
			BoundingBox obsBox = obstacle.getBox();
			BoundingBox ballBox = ball.getBox();

			if(ballBox.getMinX() < obsBox.getMaxX() && ballBox.getMaxX() > obsBox.getMinX()){
				//Boxes intersect on X axis
				xIntersection = true;
			}
			if(xIntersection && ballBox.getMinY() < obsBox.getMaxY() && ballBox.getMaxY() > obsBox.getMinY()){
				//Boxes intersect on Y axis
				yIntersection = true;
			}
			if(yIntersection && ballBox.getMinZ() < obsBox.getMaxZ() && ballBox.getMaxZ() > obsBox.getMinZ()){
				//Boxes intersect on X axis
				zIntersection = true;
			}

			if(xIntersection && yIntersection && zIntersection){
				hitObstacles.add(obstacle);
			}
		}
		return hitObstacles;
	}*/
	
	public static boolean collide(Ball ball, Triangle triangle){

		float distance = Vector3f.dot(ball.getPosition(), triangle.getNormal()) + triangle.getEquation()[3];
		//check collision
		//step 1, bounding box, TBI
		if( Math.abs(distance)<= ball.getRadius()){//step 2, plane distance
			if( isInTriangle(ball, triangle)){//step 3, triangle/ball overlap

				//do collision
				Vector3f normal = new Vector3f(triangle.getNormal().x, triangle.getNormal().y, triangle.getNormal().z);
			
				if(Vector3f.dot(ball.getVelocity(),normal) >= 0){
					return false;
				}
			
				float dotTimes2 = 2*(Vector3f.dot(normal, ball.getVelocity()));
			
				Vector3f almostFinalVelocity = Operation.multiplyByScalar(dotTimes2, normal);
				Vector3f finalVelocity = Operation.subtract(almostFinalVelocity, ball.getVelocity());
				
				if(Math.abs(finalVelocity.y)<10f){
					finalVelocity.y=0;
				}
					
				if(Math.abs(Vector3f.dot(ball.getVelocity(), normal))>2)
					finalVelocity = Operation.multiplyByScalar(0.8f,(Vector3f)finalVelocity);
				ball.setVelocity((Vector3f)finalVelocity.negate());
				//push it back
				float pushFactor=ball.getRadius()/150;

				while(Math.abs(distance)<ball.getRadius()){
					Vector3f distancePush = Operation.multiplyByScalar(pushFactor,normal);
					ball.increasePosition(distancePush.x,distancePush.y,distancePush.z);
					distance = Vector3f.dot(ball.getPosition(), triangle.getNormal()) + triangle.getEquation()[3];
				}				

				return true;
			}
		}
		return false;
	}
	
	public static boolean isInTriangle(Ball ball, Triangle triangle){
		
		Vector3f P1_3D = triangle.getP1();
		Vector3f P2_3D = triangle.getP2();
		Vector3f P3_3D = triangle.getP3();
		
		Vector2f p1=null;
		Vector2f p2=null;
		Vector2f p3=null;
		
		Vector2f position2D=null;
		Triangle2D triangle2D = null;
		
		if( Math.abs(triangle.getNormal().getY()) > Maths.EPSILON  ){
			p1 = new Vector2f(P1_3D.getX(), P1_3D.getZ());
			p2 = new Vector2f(P2_3D.getX(), P2_3D.getZ());
			p3 = new Vector2f(P3_3D.getX(), P3_3D.getZ());
			
			triangle2D = new Triangle2D(p1,p2,p3);
			position2D = new Vector2f(ball.getPosition().x, ball.getPosition().z);
		}
		
		if( position2D== null || triangle2D.ballIsIn(position2D,ball.getRadius())){
			
			if(Math.abs(triangle.getNormal().getZ()) > Maths.EPSILON ){
				p1 = new Vector2f(P1_3D.getX(), P1_3D.getY());
				p2 = new Vector2f(P2_3D.getX(), P2_3D.getY());
				p3 = new Vector2f(P3_3D.getX(), P3_3D.getY());
				
				triangle2D = new Triangle2D(p1,p2,p3);
				position2D = new Vector2f(ball.getPosition().x, ball.getPosition().y);

			}else{
				position2D=null;
			}
			
			if( position2D== null || triangle2D.ballIsIn(position2D, ball.getRadius())){
				if(Math.abs(triangle.getNormal().getX()) > Maths.EPSILON ){
					
					p1 = new Vector2f(P1_3D.getZ(), P1_3D.getY());
					p2 = new Vector2f(P2_3D.getZ(), P2_3D.getY());
					p3 = new Vector2f(P3_3D.getZ(), P3_3D.getY());
					
					triangle2D = new Triangle2D(p1,p2,p3);
					position2D = new Vector2f(ball.getPosition().z, ball.getPosition().y);

				}else{
					position2D=null;
				}
				
				if(position2D==null || triangle2D.ballIsIn(position2D, ball.getRadius())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean collide(MousePickerTraveler traveler, Triangle triangle){

		float distance = Vector3f.dot(traveler.getPosition(), triangle.getNormal()) + triangle.getEquation()[3];
		if( Math.abs(distance)<= traveler.radius){// plane distance
			if( isInTriangle(traveler, triangle)){//step 3, triangle/ball overlap
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInTriangle(MousePickerTraveler traveler, Triangle triangle){
		
		Vector3f P1_3D = triangle.getP1();
		Vector3f P2_3D = triangle.getP2();
		Vector3f P3_3D = triangle.getP3();
		
		Vector2f p1=null;
		Vector2f p2=null;
		Vector2f p3=null;
		
		Vector2f position2D=null;
		Triangle2D triangle2D = null;
		
		if( Math.abs(triangle.getNormal().getY()) > Maths.EPSILON  ){
			p1 = new Vector2f(P1_3D.getX(), P1_3D.getZ());
			p2 = new Vector2f(P2_3D.getX(), P2_3D.getZ());
			p3 = new Vector2f(P3_3D.getX(), P3_3D.getZ());
			
			triangle2D = new Triangle2D(p1,p2,p3);
			position2D = new Vector2f(traveler.getPosition().x,traveler.getPosition().z);
		}
		
		if( position2D== null || triangle2D.ballIsIn(position2D,traveler.radius)){
			
			if(Math.abs(triangle.getNormal().getZ()) > Maths.EPSILON ){
				p1 = new Vector2f(P1_3D.getX(), P1_3D.getY());
				p2 = new Vector2f(P2_3D.getX(), P2_3D.getY());
				p3 = new Vector2f(P3_3D.getX(), P3_3D.getY());
				
				triangle2D = new Triangle2D(p1,p2,p3);
				position2D = new Vector2f(traveler.getPosition().x, traveler.getPosition().y);

			}else{
				position2D=null;
			}
			
			if( position2D== null || triangle2D.ballIsIn(position2D, traveler.radius)){
				if(Math.abs(triangle.getNormal().getX()) > Maths.EPSILON ){
					
					p1 = new Vector2f(P1_3D.getZ(), P1_3D.getY());
					p2 = new Vector2f(P2_3D.getZ(), P2_3D.getY());
					p3 = new Vector2f(P3_3D.getZ(), P3_3D.getY());
					
					triangle2D = new Triangle2D(p1,p2,p3);
					position2D = new Vector2f(traveler.getPosition().z, traveler.getPosition().y);

				}else{
					position2D=null;
				}
				
				if(position2D==null || triangle2D.ballIsIn(position2D, traveler.radius)){
					return true;
				}
			}
		}
		
		return false;
	}
}
