package collision;

import org.lwjgl.util.vector.Vector3f;

import entities.Ball;

public class CollisionInfo {

	private Vector3f eRadius;
	//Information about the move being requested: (in R3)
	private Vector3f R3Velocity;
	private Vector3f R3Position;

	//Information about the move being requested: (in ellipsoid space)
	private Vector3f velocity;
	private Vector3f normalizedVelocity;
	private Vector3f basePoint;

	//Hit information
	private boolean foundCollision;
	private double nearestDistance;
	private Vector3f intersectionPoint;

	public CollisionInfo(Vector3f ellipsoidRadius,Vector3f R3vel, Vector3f startingPos){
		eRadius = ellipsoidRadius;
		R3Velocity = R3vel;
		R3Position = startingPos;
		
		velocity = new Vector3f();
		normalizedVelocity = new Vector3f();
		basePoint = new Vector3f();
		intersectionPoint= new Vector3f();
	}

	public boolean isFoundCollision() {
		return foundCollision;
	}

	public void setFoundCollision(boolean foundCollision) {
		this.foundCollision = foundCollision;
	}
	public Vector3f getEradius(){
		return eRadius;
	}
	public void setBasePoint(Vector3f v){
		basePoint =v;
	}
	public void setBasePoint(float x, float y, float z){
		basePoint.x =x;
		basePoint.y=y;
		basePoint.z=z;
	}
	public Vector3f getBasePoint(){
		return basePoint;
	}
	public Vector3f getR3Velocity(){
		return R3Velocity;
	}
	//return the velocity in the ellipsoid space
	public Vector3f getVelocity(){
		return velocity;
	}
	//set the velocity in the ellipsoid space
	public void setVelocity(Vector3f v){
		velocity.x=v.x;
		velocity.y=v.y;
		velocity.z=v.z;
	}
	public void setNormalizedVelocity(float x, float y, float z){
		normalizedVelocity.x = x;
		normalizedVelocity.y = y;
		normalizedVelocity.z = z;
	}
	public Vector3f getNormalizedVelocity(){
		return normalizedVelocity;
	}
	public Vector3f getIntersectionPoint(){
		return intersectionPoint;
	}
	public void setIntersectionPoint(Vector3f v){
		intersectionPoint.x = v.x;
		intersectionPoint.y=v.y;
		intersectionPoint.z=v.z;
	}
	public double getNearestDistance(){
		return nearestDistance;
	}
	public void setNearestDistance(double dist){
		nearestDistance=dist;
	}
}
