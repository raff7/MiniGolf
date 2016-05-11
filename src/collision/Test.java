package collision;

import org.lwjgl.util.vector.Vector3f;

public class Test {
	public static void main(String[] args){
		Vector3f v = new Vector3f(10,2,30);
		System.out.println(v);
		Vector3f u = new Vector3f();
		v.normalise(u);
		
		System.out.println(v);
		System.out.println(u);
		
		/*CollisionInfo colInfo = new CollisionInfo();
		colInfo.eRadius = new Vector3f(1,1,1);
		
		colInfo.R3Velocity = new Vector3f(3,1,0);
		colInfo.R3Position = new Vector3f(100,100,0) ;
		//Let's say radius=20
		colInfo.basePoint = new Vector3f(100/20,0,100/20);
		colInfo.velocity = new Vector3f(3/20,0,1/20);  
		(colInfo.velocity).normalise(colInfo.normalizedVelocity);
		
		Vector3f p1 = new Vector3f(0,0,0);
		Vector3f p2 = new Vector3f(0,0,400/20);
		Vector3f p3 = new Vector3f(0,400/20,200/20);
		
		CollisionHandler.checkTriangle(colInfo,p1,p2,p3);
		ResponseStep.collideAndSlide(new Ball(), colInfo, velocity, gravity);
	}
	
	ArrayList<Triangle> trianglesList = terrain.getModel().getTriangles();
	int i=0;
	while(this.colInfo.foundCollision == false){
		Triangle triangle = trianglesList.get(i);
		CollisionHandler.checkTriangle(colInfo,triangle.getP1(),triangle.getP2(),triangle.getP3());
		i++;
	}
	ResponseStep.collideAndSlide(this,colInfo,getVelocity(),new Vector3f(0,0,0));
*/
	}
}
