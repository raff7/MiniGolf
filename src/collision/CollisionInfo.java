package collision;

import org.lwjgl.util.vector.Vector3f;

import entities.Ball;

public class CollisionInfo {
	
	Vector3f eRadius;
	//Information about the move being requested: (in R3)
	Vector3f R3Velocity;
	Vector3f R3Position;
	
	//Information about the move being requested: (in ellipsoid space) 
	Vector3f velocity;
	Vector3f normalizedVelocity;
	Vector3f basePoint;
	
	//Hit information
	boolean foundCollision;
	double nearestDistance;
	Vector3f intersectionPoint;
	
	public CollisionInfo(){
	}
}
