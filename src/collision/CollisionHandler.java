package collision;

import org.lwjgl.util.vector.Vector3f;

public class CollisionHandler {

	// Assumes: p1,p2 and p3 are given in ellipsoid space:
	public void checkTriangle(CollisionInfo colInfo, Vector3f p1, Vector3f p2, Vector3f p3){
		// Make the plane containing this triangle.
		Plane trianglePlane = new Plane(p1,p2,p3);

		// Is triangle front-facing to the velocity vector?
		// We only check front-facing triangles
		if (trianglePlane.isFrontFacingTo(colInfo.normalizedVelocity)){
		// Get interval of plane intersection:
		double t0, t1;
		boolean embeddedInPlane = false;
		// Calculate the signed distance from sphere
		// position to triangle plane
		double signedDistToTrianglePlane = trianglePlane.signedDistanceTo(colInfo.basePoint);
		// cache this as we’re going to use it a few times below:
		float normalDotVelocity = Vector3f.dot(trianglePlane.normal,colInfo.velocity);

		// if sphere is traveling parallel to the plane:
		if(normalDotVelocity == 0.0f) {
			if(Math.abs(signedDistToTrianglePlane) >= 1.0f) {
				// Sphere is not embedded in plane.
				// No collision possible:
				return;
			}
		else {
			// sphere is embedded in plane.
			// It intersects in the whole range [0..1]
			embeddedInPlane = true;
			t0 = 0.0;
			t1 = 1.0;
			}

		}
		else {
			// N dot D is not 0. Calculate intersection interval:
			t0=(-1.0-signedDistToTrianglePlane)/normalDotVelocity;
			t1=( 1.0-signedDistToTrianglePlane)/normalDotVelocity;
			// Swap so t0 < t1
			if (t0 > t1) {
			double temp = t1;
			t1 = t0;
			t0 = temp;
			}
			// Check that at least one result is within range:
			if (t0 > 1.0f || t1 < 0.0f) {
			// Both t values are outside values [0,1]
			// No collision possible:
			return;
			}
			// Clamp to [0,1]
			if (t0 < 0.0) t0 = 0.0;
			if (t1 < 0.0) t1 = 0.0;
			if (t0 > 1.0) t0 = 1.0;
			if (t1 > 1.0) t1 = 1.0;
		}
		// OK, at this point we have two time values t0 and t1
		// between which the swept sphere intersects with the
		// triangle plane. If any collision is to occur it must
		// happen within this interval.
		Vector3f collisionPoint=new Vector3f();
		boolean foundCollison = false;
		double t = 1.0f;

		// First we check for the easy case - collision inside
		// the triangle. If this happens it must be at time t0
		// as this is when the sphere rests on the front side
		// of the triangle plane. Note, this can only happen if
		// the sphere is not embedded in the triangle plane.
		if (!embeddedInPlane) {
			Vector3f planeIntersectionPoint=new Vector3f();
			Vector3f.add(Operation.subtract(colInfo.basePoint,trianglePlane.normal),Operation.multiplyByScalar((float)t0, colInfo.velocity),planeIntersectionPoint);
			if (Operation.checkPointInTriangle(planeIntersectionPoint,p1,p2,p3)){
				foundCollison = true;
				t = t0;
				collisionPoint = planeIntersectionPoint;
			}
		}
		// if we haven’t found a collision already we’ll have to
		// sweep sphere against points and edges of the triangle.
		// Note: A collision inside the triangle (the check above)
		// will always happen before a vertex or edge collision!
		// This is why we can skip the swept test if the above
		// gives a collision!
		if (foundCollison == false) {
			// some commonly used terms:
			Vector3f velocity = colInfo.velocity;
			Vector3f base = colInfo.basePoint;
			float velocitySquaredLength = velocity.lengthSquared();
			float a,b,c; // Params for equation
			float newT;

			// For each vertex or edge a quadratic equation has to
			// be solved. We parameterize this equation as
			// a*t^2 + b*t + c = 0 and below we calculate the
			// parameters a,b and c for each test.
			// Check against points:
			a = velocitySquaredLength;

			// P1
			b = 2.0f*(Vector3f.dot(velocity,Operation.subtract(base,p1)));
			c = (Operation.subtract(p1,base)).lengthSquared() - 1.0f;
			if (Operation.getLowestRoot(a,b,c, t)){
				t = Operation.getRoot();
				foundCollison = true;
				collisionPoint = p1;
			}

			// P2
			b = 2.0f*(Vector3f.dot(velocity,Operation.subtract(base,p2)));
			c = (Operation.subtract(p2,base)).lengthSquared() - 1.0f;
			if (Operation.getLowestRoot(a,b,c, t)) {
				t = Operation.getRoot();
				foundCollison = true;
				collisionPoint = p2;
			}

			// P3
			b = 2.0f*(Vector3f.dot(velocity,Operation.subtract(base,p3)));
			c = (Operation.subtract(p3,base)).lengthSquared() - 1.0f;
			if (Operation.getLowestRoot(a,b,c, t)) {
				t = Operation.getRoot();
				foundCollison = true;
				collisionPoint = p3;
			}

			// Check against edges:
			// p1 -> p2:
			Vector3f edge = Operation.subtract(p2, p1);
			Vector3f baseToVertex =  Operation.subtract(p1, base);
			float edgeSquaredLength = edge.lengthSquared();
			float edgeDotVelocity = Vector3f.dot(edge,velocity);
			float edgeDotBaseToVertex = Vector3f.dot(edge,baseToVertex);
			// Calculate parameters for equation
			a = edgeSquaredLength*-velocitySquaredLength + edgeDotVelocity*edgeDotVelocity;
			b = edgeSquaredLength*(2.0f*Vector3f.dot(velocity,baseToVertex)) - 2.0f*edgeDotVelocity*edgeDotBaseToVertex;
			c = edgeSquaredLength*(1-baseToVertex.lengthSquared())+ edgeDotBaseToVertex*edgeDotBaseToVertex;

			// Does the swept sphere collide against infinite edge?
			if (Operation.getLowestRoot(a,b,c, t)) {
				// Check if intersection is within line segment:
				float f=(edgeDotVelocity*Operation.getRoot()-edgeDotBaseToVertex)/edgeSquaredLength;
				if (f >= 0.0 && f <= 1.0) {
					// intersection took place within segment.
					t = Operation.getRoot();
					foundCollison = true;
					Vector3f.add(p1, Operation.multiplyByScalar(f,edge), collisionPoint);
				}
			}

			// p2 -> p3:
			edge = Operation.subtract(p3, p2);
			baseToVertex = Operation.subtract(p2, base);
			edgeSquaredLength = edge.lengthSquared();
			edgeDotVelocity = Vector3f.dot(edge,velocity);
			edgeDotBaseToVertex = Vector3f.dot(edge,baseToVertex);
			a = edgeSquaredLength*-velocitySquaredLength + edgeDotVelocity*edgeDotVelocity;
			b = edgeSquaredLength*(2.0f*Vector3f.dot(velocity,baseToVertex)) - 2.0f*edgeDotVelocity*edgeDotBaseToVertex;
			c = edgeSquaredLength*(1-baseToVertex.lengthSquared())+ edgeDotBaseToVertex*edgeDotBaseToVertex;
			if (Operation.getLowestRoot(a,b,c, t)) {
			float f=(edgeDotVelocity*Operation.getRoot()-edgeDotBaseToVertex)/edgeSquaredLength;
			if (f >= 0.0 && f <= 1.0) {
				t = Operation.getRoot();
				foundCollison = true;
				Vector3f.add(p2, Operation.multiplyByScalar(f,edge), collisionPoint);
			}
			}

			// p3 -> p1:
			edge = Operation.subtract(p1, p3);
			baseToVertex =Operation.subtract(p3, base);
			edgeSquaredLength = edge.lengthSquared();
			edgeDotVelocity = Vector3f.dot(edge,velocity);
			edgeDotBaseToVertex = Vector3f.dot(edge,baseToVertex);
			a = edgeSquaredLength*-velocitySquaredLength + edgeDotVelocity*edgeDotVelocity;
			b =edgeSquaredLength*(2.0f*Vector3f.dot(velocity,baseToVertex)) - 2.0f*edgeDotVelocity*edgeDotBaseToVertex;
			c = edgeSquaredLength*(1-baseToVertex.lengthSquared()) + edgeDotBaseToVertex*edgeDotBaseToVertex;
			if (Operation.getLowestRoot(a,b,c, t)){
				float f=(edgeDotVelocity*Operation.getRoot()-edgeDotBaseToVertex)/edgeSquaredLength;
				if (f >= 0.0 && f <= 1.0) {
					t = Operation.getRoot();
					foundCollison = true;
					Vector3f.add(p3, Operation.multiplyByScalar(f,edge), collisionPoint);
				}
			}
		}

		// Set result:
		if (foundCollison == true){
			// distance to collision: ’t’ is time of collision
			double distToCollision = t*colInfo.velocity.length();
			// Does this triangle qualify for the closest hit?
			// it does if it’s the first hit or the closest
			if (colInfo.foundCollision == false || distToCollision < colInfo.nearestDistance) {
				// Collision information necessary for sliding
				colInfo.nearestDistance = distToCollision;
				colInfo.intersectionPoint=collisionPoint;
				colInfo.foundCollision = true;
		}
		}
		} // if not backface
		}
}
