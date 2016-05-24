package entities;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import GameManager.Observer;
import collision.BoundingBox;
import collision.Operation;
import geometry.Triangle;
import geometry.Triangle2D;
import models.TexturedModel;
import toolbox.HumanPowerController;
import renderEngine.DisplayManager;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Maths;

public class Ball extends Entity{
	
	private static final float RUN_SPEED = 50;
	private static final float 	TURN_SPEED = 100;
	private static final float GRAVITY = -100;
	private static final float JUMP_POWER=30;
	private static final float FRICTION = 0.01f;
	private static final float MAX_RUN_SPEED = 1000;
	private float currentTurnSpeed = 0;
		
	private Vector3f velocity;
	private final float  RADIUS = 1f;
	
	//for friction effect
	private float friction = 0.99f ;
	private float minimalSpeed = 1f ;
	
	//for observer
	ArrayList<Observer> observers = new ArrayList<Observer>();
	private boolean ballIsInHole=false;
	private boolean isShoted = false;
	//private Hole hole;


	
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
	public void move(ArrayList<Entity> entitiesList){
		checkTestingInputs();
		
		//collision
		ArrayList<Triangle> trianglesList = new ArrayList();
		ArrayList<BoundingBox> boxes = new ArrayList();
		
		for(Entity entity:entitiesList){
		trianglesList.addAll(entity.getModel().getRawModel().getTriangles());
		boxes.add(entity.getBox());
		}
		for(Triangle triangle:trianglesList){
				if(collide(triangle)){
					frictionEffect() ;
					break;
				}
		}
		//end of collision
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();
        velocity.y+= GRAVITY*DisplayManager.getFrameTimeSeconds();
		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
	
	}
	

	private void jump(){
		
			this.velocity.y=JUMP_POWER;
	}
	private void checkTestingInputs(){
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
			//jump();
		}
		if(Mouse.isButtonDown(0)){
			//if(getVelocity().x == 0 && getVelocity().y == 0 && getVelocity().z ==0)
				
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
		return RADIUS;
	}
	
	public boolean isInTriangle(Triangle triangle){
		
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
			
			
			
			position2D = new Vector2f(super.getPosition().x, super.getPosition().z);
		}
		if( position2D== null || triangle2D.ballIsIn(position2D,RADIUS)){
			
			if(Math.abs(triangle.getNormal().getZ()) > Maths.EPSILON ){
				p1 = new Vector2f(P1_3D.getX(), P1_3D.getY());
				p2 = new Vector2f(P2_3D.getX(), P2_3D.getY());
				p3 = new Vector2f(P3_3D.getX(), P3_3D.getY());
				
				triangle2D = new Triangle2D(p1,p2,p3);
				
				
				position2D = new Vector2f(super.getPosition().x, super.getPosition().y);

			}else{
				position2D=null;

			}
			if( position2D== null || triangle2D.ballIsIn(position2D,RADIUS)){
				if(Math.abs(triangle.getNormal().getX()) > Maths.EPSILON ){
					
					p1 = new Vector2f(P1_3D.getZ(), P1_3D.getY());
					p2 = new Vector2f(P2_3D.getZ(), P2_3D.getY());
					p3 = new Vector2f(P3_3D.getZ(), P3_3D.getY());
					
					triangle2D = new Triangle2D(p1,p2,p3);
					
					position2D = new Vector2f(super.getPosition().z, super.getPosition().y);

				}else{
					position2D=null;
				}
				if(position2D==null || triangle2D.ballIsIn(position2D,RADIUS)){
					return true;
				}
			}
		}
		return false;
	}
	

	private boolean collide(Triangle triangle){

		float distance = Vector3f.dot(super.getPosition(), triangle.getNormal()) + triangle.getEquation()[3];
		//check collision
		
		//step 1, bounding box, TBI

		if( Math.abs(distance)<= RADIUS){//step 2, plane distance
			if( isInTriangle(triangle)){//step 3, triangle/ball overlap

				//do collision
				
				Vector3f normal = new Vector3f( triangle.getNormal().x, triangle.getNormal().y, triangle.getNormal().z);
				//System.out.println("velocity before: "+getVelocity());
				if(Vector3f.dot(velocity,normal) >= 0){
					return false;
				}
			
				float dotTimes2 = 2*(Vector3f.dot(normal, getVelocity()));
				//System.out.println("dot times 2: "+dotTimes2);
				Vector3f almostFinalVelocity = Operation.multiplyByScalar(dotTimes2, normal);
				Vector3f finalVelocity = Operation.subtract(almostFinalVelocity, getVelocity());
				
				if(Math.abs(finalVelocity.y)<10f){
					finalVelocity.y=0;
				}
					
				if(Math.abs(Vector3f.dot(velocity, normal))>2)
					finalVelocity = Operation.multiplyByScalar(0.8f,(Vector3f)finalVelocity);
				setVelocity((Vector3f)finalVelocity.negate());
//				//push it back
				float pushFactor=RADIUS/150;

				while(Math.abs(distance)<RADIUS){
					Vector3f distancePush = Operation.multiplyByScalar(pushFactor,normal);
					super.increasePosition(distancePush.x,distancePush.y,distancePush.z);
					distance = Vector3f.dot(super.getPosition(), triangle.getNormal()) + triangle.getEquation()[3];
				}				

				return true;
			}
		}
		return false;
	}
	private void frictionEffect(){

		velocity.scale(friction) ;
		if(Math.abs(velocity.length()) < minimalSpeed){
			velocity.set(0f, velocity.y, 0f) ;
		}
	}
	
	
	
	//observer: game
	
	public void Notify(){
		for(Observer observer:observers){
			observer.update();
		}
	}
	public void attach(Observer observer){
		observers.add(observer);
	}
	public void detach(Observer observer){
		observers.remove(observer);
	}

	public boolean getBallIsInHole() {
		return ballIsInHole;
	}

}