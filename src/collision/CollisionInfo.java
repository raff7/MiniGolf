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
	private boolean foundCollision;
	double nearestDistance;
	Vector3f intersectionPoint;

	public CollisionInfo(){
	}

	public boolean isFoundCollision() {
		return foundCollision;
	}

	public void setFoundCollision(boolean foundCollision) {
		this.foundCollision = foundCollision;
	}
}
/* Add this to ball class
 * private static Vector3f velocity;
 * private static Vector3f position;
 * private static Vector3f radius;
 * private static Vector3f eRadius;
 * */
