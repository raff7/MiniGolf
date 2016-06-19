package entities;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import GameManager.Observer;
import collision.BoundingBox;
import collision.CollisionHandler;
import geometry.Line;
import toolbox.Operation;
import geometry.Triangle;
import models.TexturedModel;
import renderEngine.DisplayManager;
import toolbox.Maths;
import toolbox.Operation;

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

	private int stopCr;
	
	private Triangle lastTriangleHit = null;
	
	//private Hole hole;

	//only for testBall
	private ArrayList<Entity> entitiesListTestBall;

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		super(model, 0, position, rotX, rotY, rotZ, scale);
		velocity = new Vector3f(0,0,0);
	}
	
	//constructor for testBall
	public Ball(TexturedModel model, Vector3f position, ArrayList<Entity> entitiesList, float rotX, float rotY, float rotZ, float scale){
		super(model,0, position, rotX, rotY, rotZ, scale);
		velocity = new Vector3f(0,0,0);
		this.entitiesListTestBall = entitiesList;
	}
	
	// move as a free camera
	public void moving(){
		checkFreeCameraInputs();
		
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();
		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
		
		
		
	}
	
	//move as a ball
	public void moveNonPlayer(ArrayList<Entity> entitiesList){
		
		//collision
		ArrayList<Triangle> trianglesList = new ArrayList();
		ArrayList<BoundingBox> boxes = new ArrayList();
		
		for(Entity entity:entitiesList){
			if(entity!=this){
				if(!(entity instanceof Ball)){
					trianglesList.addAll(entity.getTriangles());
					//System.out.println("triangles list size: "+trianglesList.size());
					boxes.add(entity.getBox());
				}
			}
		}
		for(Triangle triangle:trianglesList){
				if(CollisionHandler.collide(this, triangle)){
					frictionEffect();
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
	

	public void move(ArrayList<Entity> entitiesList){
		checkTestingInputs();
		
		//collision
		ArrayList<Triangle> trianglesList = new ArrayList();
		ArrayList<BoundingBox> boxes = new ArrayList();
		
		for(Entity entity:entitiesList){
		trianglesList.addAll(entity.getTriangles());
	//System.out.println("triangles list size: "+trianglesList.size());
		boxes.add(entity.getBox());
		}
		for(Triangle triangle:trianglesList){
				if(CollisionHandler.collide(this, triangle)){
					frictionEffect();
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
	

	/*private void ballToBallCollision(Ball otherBall) {
		if(Maths.distancePointPoint(this.getPosition(), otherBall.getPosition())<=2*RADIUS){
			Vector2f p1=new Vector2f(this.getPosition().x,this.getPosition().z);
			Vector2f p2=new Vector2f(otherBall.getPosition().x,otherBall.getPosition().y);
			Vector2f V = new Vector2f(this.getVelocity().x,this.getVelocity().z);
			Line l1 = new Line(p1,p2);
			Line l2 = l1.getPerpendicoular(p1);
			Line lV1 = l1.getPerpendicoular(V);
			Vector2f A = l1.getInterseptionPoint(lV1);
			Line lV2 = l2.getPerpendicoular(V);
			Vector2f B = l2.getInterseptionPoint(lV2);
			Vector2f velocity2D1= new Vector2f(A.x-p1.x,A.y-p1.y);
			Vector2f velocity2D2= new Vector2f(B.x-p1.x,B.y-p1.y);
			float y1=0;
			float y2=0;
			this.setVelocity(new Vector3f(velocity2D1.x,y1,velocity2D1.y));
			otherBall.setVelocity(new Vector3f(velocity2D2.x,y2,velocity2D2.y));
			System.out.println(otherBall.getVelocity());
		}
	}*/
	
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
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			jump();
		}
		if(Mouse.isButtonDown(0)){
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			velocity.x = 0;
			velocity.z = 0;
		}
	}
	
	private void checkingInputs(){
		 if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;

		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			velocity.x = 0;
			velocity.z = 0;
		}
	}
	
	private void checkFreeCameraInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.velocity.x = (float) (RUN_SPEED*2*Math.sin(Math.toRadians(getRotY())));	
			this.velocity.z = (float) (RUN_SPEED*2*Math.cos(Math.toRadians(getRotY())));	
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.velocity.x = (float) (-RUN_SPEED*2*Math.sin(Math.toRadians(getRotY())));	
			this.velocity.z = (float) (-RUN_SPEED*2*Math.cos(Math.toRadians(getRotY())));	
		
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
			velocity.y=-GRAVITY;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			velocity.y=GRAVITY;
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

	private void frictionEffect(){
		velocity.scale(friction);
		if(Math.abs(velocity.length()) < minimalSpeed){
			velocity.set(0f, velocity.y, 0f);
		}
	}

	public float getFriction(){
		return friction;
	}
	
	public float getMinimalSpeed() {
		return minimalSpeed;
	}
	
	//observer: game
	public void Notify(){
		for(Observer observer:observers){
			observer.updateObserver();
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
	
	public void simulateShot(ArrayList<Entity> ground){
		System.out.println("velo length: "+velocity.length());
		while( Math.abs(velocity.length()) > minimalSpeed+2){
			move(ground);
			System.out.println("position testBall: " +getPosition());
			if(velocity.x > 100 || velocity.y > 100 || velocity.z > 100)
				setVelocity(new Vector3f(0,0,0));
		}
	}
	
	public float getDistanceFromHole(Vector3f hole){
		return Operation.subtract(hole, getPosition() ).length();
	}
	
	public void setLastTriangleHit(Triangle triangle){
		lastTriangleHit = triangle;
	}
	
	public Triangle getLastTriangleHit(){
		return lastTriangleHit;
	}
	

}