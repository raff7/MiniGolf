package collision;

import org.lwjgl.util.vector.Vector3f;

import entities.Ball;

public class ResponseStep{
	int collisionRecursionDepth;
	
	public void collideAndSlide(Ball ball, CollisionInfo colInfo,Vector3f velocity, Vector3f gravity){
	// Do collision detection:
	colInfo.R3Position = ball.getPosition();
	colInfo.R3Velocity = ball.getVelocity();
	colInfo.eRadius = ball.geteRadius();
	
	// calculate position and velocity in eSpace
	Vector3f eSpacePosition = Operation.divideVector(colInfo.R3Position, colInfo.eRadius);
	Vector3f eSpaceVelocity = Operation.divideVector(colInfo.R3Velocity, colInfo.eRadius);
	
	// Iterate until we have our final position.
	collisionRecursionDepth = 0;
	Vector3f finalPosition = collideWithWorld(colInfo,eSpacePosition,eSpaceVelocity);
	// Add gravity pull:
	
	// To remove gravity uncomment from here .....
	// Set the new R3 position (convert back from eSpace to R3
	colInfo.R3Position = Operation.multiplyVector(finalPosition,colInfo.eRadius);
	colInfo.R3Velocity = gravity;
	eSpaceVelocity = Operation.divideVector(gravity,colInfo.eRadius);
	collisionRecursionDepth = 0;
	finalPosition = collideWithWorld(colInfo,finalPosition,eSpaceVelocity);
	// ... to here
	
	// Convert final result back to R3:
	finalPosition = Operation.multiplyVector(finalPosition,colInfo.eRadius);
	// Move the entity (application specific function)
	
////////////////////////MoveTo(finalPosition);\\\\\\\\\\\\\\\
	}
	
	
	// Set this to match application scale..
	float unitsPerMeter = 100.0f;
	public Vector3f collideWithWorld(CollisionInfo colInfo,Vector3f position,Vector3f velocity){
	// All hard-coded distances in this function is
	// scaled to fit the setting above..
	float unitScale = unitsPerMeter / 100.0f;
	float veryCloseDistance = 0.005f * unitScale;
	//do we need to worry?
	if (collisionRecursionDepth>5)
		return position;
	//Ok, we need to worry:
	colInfo.velocity = velocity;

	/*colInfo.normalizedVelocity = velocity; is the same thing as what I do below (I think)
	colInfo.normalizedVelocity.normalize();*/
	velocity.normalise(colInfo.normalizedVelocity);
	
	colInfo.basePoint = position;
	colInfo.foundCollision = false;
	// Check for collision (calls the collision routines)
	// Application specific!!
////////	world->checkCollision(colInfo); \\\\\\\
	
	// If no collision we just move along the velocity
	if (colInfo.foundCollision == false) {
		return Operation.add(position,velocity);
	}
	
	// *** Collision occurred ***
	// The original destination point
	Vector3f destinationPoint = Operation.add(position, velocity);
	Vector3f newBasePoint = position;
	// only update if we are not already very close
	// and if so we only move very close to intersection, not to the exact spot.
	if (colInfo.nearestDistance >= veryCloseDistance){
		Vector3f v = velocity;
		
		//v.SetLength(colInfo.nearestDistance - veryCloseDistance); I changed it in this way 
		v.normalise(v);
		float k =(float) (colInfo.nearestDistance - veryCloseDistance);
		v= Operation.multiplyByScalar(k, v);
		
		newBasePoint = Operation.add(colInfo.basePoint,v);
		// Adjust polygon intersection point (so sliding plane will be unaffected by the fact that we
		// move slightly less than collision tells us)
		v.normalise(v);
		colInfo.intersectionPoint =Operation.subtract(colInfo.intersectionPoint, Operation.multiplyByScalar(veryCloseDistance, v));
	}
	
	// Determine the sliding plane
	Vector3f slidePlaneOrigin = colInfo.intersectionPoint;
	Vector3f slidePlaneNormal = Operation.subtract(newBasePoint,colInfo.intersectionPoint);
	slidePlaneNormal.normalise(slidePlaneNormal);
	Plane slidingPlane = new Plane(slidePlaneOrigin,slidePlaneNormal);
	
	// Again, sorry about formatting.. but look carefully ;)
	float signedDistance = (float) slidingPlane.signedDistanceTo(destinationPoint);
	Vector3f newDestinationPoint = Operation.subtract(destinationPoint, Operation.multiplyByScalar(signedDistance, slidePlaneNormal));
	
	// Generate the slide vector, which will become our new velocity vector for the next iteration
	Vector3f newVelocityVector = Operation.subtract(newDestinationPoint,colInfo.intersectionPoint);
	// Recurse:
	// don't recurse if the new velocity is very small
	if (newVelocityVector.length() < veryCloseDistance){
		return newBasePoint;
	}
	collisionRecursionDepth++;
		return collideWithWorld(colInfo,newBasePoint,newVelocityVector);
	}
}