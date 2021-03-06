package entities;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import collision.BoundingBox;
import collision.Operation;
import geometry.Line;
import geometry.Triangle;
import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import textures.ModelTexture;

public class Ball extends Entity{
	private static final float RUN_SPEED = 50;
	private static final float 	TURN_SPEED = 100;
	private static final float GRAVITY = -100;
	private static final float JUMP_POWER=30;
	private static final float FRICTION = 0.01f;
	private static final float MAX_RUN_SPEED = 1000;
	private float currentTurnSpeed = 0;
	
	private boolean isInAir=false;
	
	private Vector3f velocity;
	private float radius = 1f;
	private Vector3f eRadius;
	
	//for friction effect
	private float friction = 0.99f ;
	private float minimalSpeed = 1f ;
	
	private boolean debug=false;
	

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		super(model,0, position, rotX, rotY, rotZ, scale);
		velocity = new Vector3f(0,0,0);
	}
	
	// move as a free camera
	public void move(){
		checkFreeCameraInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();
		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
	}
	//move as a ball
	public void move(Terrain terrain,ArrayList<Entity> entitiesList){ 	
		ArrayList<Triangle> trianglesList = entitiesList.get(0).getModel().getRawModel().getTriangles();
		BoundingBox box =entitiesList.get(0).getBox();
	
		for(Triangle triangle:trianglesList){
			ArrayList<Vector3f> points = new ArrayList<Vector3f>();
			
			Vector3f normalisedVelocityDummy = new Vector3f();
			getVelocity().normalise(normalisedVelocityDummy);
			
			Vector3f pointA = Operation.add(getPosition(),new Vector3f(radius,0,0));
			Vector3f pointB = Operation.add(getPosition(),new Vector3f(-radius,0,0));
			Vector3f pointC = Operation.add(getPosition(),new Vector3f(0,radius,0));
			Vector3f pointD = Operation.add(getPosition(),new Vector3f(0,-radius,0));
			Vector3f pointE = Operation.add(getPosition(),new Vector3f(0,0,radius));
			Vector3f pointF = Operation.add(getPosition(),new Vector3f(0,0,-radius));
			
			Vector3f pointG = Operation.add(getPosition(),Operation.multiplyByScalar(radius, normalisedVelocityDummy));
			Vector3f pointH = Operation.add(getPosition(),Operation.multiplyByScalar(-radius, normalisedVelocityDummy));
			
			
			/*points.add(pointA);
			points.add(pointB);
			points.add(pointC);
			points.add(pointD);
			points.add(pointE);
			points.add(pointF);
			
			points.add(pointG);
			points.add(pointH);*/
			points.add(getPosition());
			
			/*Vector3f normal = new Vector3f( triangle.getNormal().x, triangle.getNormal().y, triangle.getNormal().z);
			if(Vector3f.dot(velocity,normal) > 0)
				normal.negate();
			
			Vector3f supportPoint = ;*/
			
			for(Vector3f point:points)
				if(collide(triangle,point))
					break;
		}
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();
        velocity.y+= GRAVITY*DisplayManager.getFrameTimeSeconds();
		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
	
		float terrainHeight;
		if(terrain != null)
			terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z)+1;
		else
			terrainHeight = 1;
		if(super.getPosition().y < terrainHeight){
			isInAir=false;
			velocity.y = 0;
			super.getPosition().y=terrainHeight;
		}	
		frictionEffect();
		
	}
	
	private void frictionEffect(){
		velocity.scale(friction) ;
		if(Math.abs(velocity.length()) < minimalSpeed){
			velocity.set(0f, 0f, 0f) ;
		}
	}
	private void jump(){
		if(!isInAir||isInAir){
			this.velocity.y=JUMP_POWER;
			isInAir=true;
		}
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.velocity.x = (float) (RUN_SPEED*Math.sin(Math.toRadians(getRotY())));	
			this.velocity.z = (float) (RUN_SPEED*Math.cos(Math.toRadians(getRotY())));		
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.velocity.x = (float) (-RUN_SPEED*Math.sin(Math.toRadians(getRotY())));	
			this.velocity.z = (float) (-RUN_SPEED*Math.cos(Math.toRadians(getRotY())));			
			}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			velocity.x = 0;
			velocity.z = 0;
		}
	}
	private void checkFreeCameraInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.velocity.x = (float) (RUN_SPEED*100*Math.sin(Math.toRadians(getRotY())));	
			this.velocity.z = (float) (RUN_SPEED*100*Math.cos(Math.toRadians(getRotY())));	
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.velocity.x = (float) (-RUN_SPEED*100*Math.sin(Math.toRadians(getRotY())));	
			this.velocity.z = (float) (-RUN_SPEED*100*Math.cos(Math.toRadians(getRotY())));	
		
			}else{
			this.velocity.x=0;
			this.velocity.z=0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			velocity.y=-GRAVITY*1.5f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			velocity.y=GRAVITY*1.5f;
		}else{
			velocity.y=0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			velocity.x *= 1.5f;
			velocity.y *= 1.5f;
			velocity.z *= 1.5f;
		}		
	}
	public  Vector3f getVelocity() {
		return velocity;
	}
	public  void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
		
	private boolean collide(Triangle triangle, Vector3f point){

		float distance = Vector3f.dot(point, triangle.getNormal()) + triangle.getEquation()[3];
		//System.out.println("distance: "+distance);
		
		if( Math.abs(distance) <= getRadius()){
			if( isInTriangle(triangle,point)){
				Vector3f normal = new Vector3f( triangle.getNormal().x, triangle.getNormal().y, triangle.getNormal().z);
				//System.out.println("velocity before: "+getVelocity());
				if(Vector3f.dot(velocity,normal) > 0){
					normal.negate();
				}
				//push it back
				float factor=50;
				Vector3f distancePush = Operation.multiplyByScalar(velocity.length()/factor,normal);
				float dx = distancePush.x;
				float dz = distancePush.z;
				float dy = distancePush.y;
				super.increasePosition(dx,dy,dz);
				
				float dotTimes2 = 2*(Vector3f.dot(normal, getVelocity()));
				//System.out.println("dot times 2: "+dotTimes2);
				Vector3f almostFinalVelocity = Operation.multiplyByScalar(dotTimes2, normal);
				Vector3f finalVelocity = Operation.subtract(almostFinalVelocity, getVelocity());
				
				setVelocity(Operation.multiplyByScalar(0.7f,(Vector3f)finalVelocity.negate()));
				/*System.out.println("velocity after: "+getVelocity());
				System.out.println();
				System.out.println();*/
				return true;
			}
		}
		return false;
	}

	public boolean isInTriangle(Triangle triangle, Vector3f point){

		Vector3f P1_3D = triangle.getP1();
		Vector3f P2_3D = triangle.getP2();
		Vector3f P3_3D = triangle.getP3();
		
		Vector2f p1=null;
		Vector2f p2=null;
		Vector2f p3=null;
		
		Line line1=null;
		Line line2=null;
		Line line3=null;
		
		Vector2f position2D=null;
		
		if( triangle.getNormal().getY() != 0 ){
			
			p1 = new Vector2f(P1_3D.getX(), P1_3D.getZ());
			p2 = new Vector2f(P2_3D.getX(), P2_3D.getZ());
			p3 = new Vector2f(P3_3D.getX(), P3_3D.getZ());
			
			line1 = new Line(p1, p2);
			line2 = new Line(p1, p3);
			line3 = new Line(p2, p3);
			
			position2D = new Vector2f(point.x, point.z);
		}

		if( position2D== null || (line1.liesOnSameSide(position2D, p3) && line2.liesOnSameSide(position2D, p2) && line3.liesOnSameSide(position2D,p1))){

			if(triangle.getNormal().getZ() != 0 ){
				
				p1 = new Vector2f(P1_3D.getX(), P1_3D.getY());
				p2 = new Vector2f(P2_3D.getX(), P2_3D.getY());
				p3 = new Vector2f(P3_3D.getX(), P3_3D.getY());
				
				line1 = new Line(p1, p2);
				line2 = new Line(p1, p3);
				line3 = new Line(p2, p3);
				
				position2D = new Vector2f(point.x, point.y);
			}else{
				position2D=null;
			}
			if( position2D== null || (line1.liesOnSameSide(position2D, p3) && line2.liesOnSameSide(position2D, p2) && line3.liesOnSameSide(position2D,p1))){

				if(triangle.getNormal().getX() != 0 ){
					
					p1 = new Vector2f(P1_3D.getZ(), P1_3D.getY());
					p2 = new Vector2f(P2_3D.getZ(), P2_3D.getY());
					p3 = new Vector2f(P3_3D.getZ(), P3_3D.getY());
					
					line1 = new Line(p1, p2);
					line2 = new Line(p1, p3);
					line3 = new Line(p2, p3);
					
					position2D = new Vector2f(point.z, point.y);

				}else{
					position2D=null;
				}
				if(position2D==null || (line1.liesOnSameSide(position2D, p3) && line2.liesOnSameSide(position2D, p2) && line3.liesOnSameSide(position2D,p1))){
					return true;
				}
			}
		}
		return false;
	}

}