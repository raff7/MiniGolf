package entities;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import GameManager.Observer;
import GameManager.Test;
import bot.Node;
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
	private float currentTurnSpeed = 0;
		
	private Vector3f velocity;
	private final float  RADIUS = 1f;
	
	//for friction effect and noise
	private ArrayList<Noise> noises = new ArrayList<Noise>();
	private float friction =10f;
	private float minimalSpeed = 0.4f ;
	
	private Triangle lastTriangleHit = null;
	private boolean isSimulation=false;
	private ArrayList<Triangle> path;
	
	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		super(model, 0, position, rotX, rotY, rotZ, scale);
		velocity = new Vector3f(0,0,0);
	}
	
	//constructor for testBall
	public Ball(TexturedModel model, Vector3f position, ArrayList<Entity> entitiesList, float rotX, float rotY, float rotZ, float scale){
		super(model,0, position, rotX, rotY, rotZ, scale);
		velocity = new Vector3f(0,0,0);
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
				CollisionHandler.collide(this, triangle);

		}
		//end of collision

		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();

		velocity.y+= GRAVITY*DisplayManager.getFrameTimeSeconds();

		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
		frictionEffect(DisplayManager.getFrameTimeSeconds());

	}
	

	public void move(ArrayList<Entity> entitiesList){
		long generalTime=System.nanoTime();
		checkingInputs();
		//collision
		ArrayList<Triangle> trianglesList = new ArrayList<Triangle>();
		ArrayList<BoundingBox> boxes = new ArrayList<BoundingBox>();
		
		for(Entity entity:entitiesList){
			if(!(entity instanceof Ball)){
				trianglesList.addAll(entity.getTriangles());
				boxes.add(entity.getBox());
			}
		}
		long time = System.nanoTime();
		for(Triangle triangle:trianglesList){
			if(CollisionHandler.collide(this, triangle) && isSimulation)
					path.add(triangle);		
		}


		//end of collision

		super.increaseRotation(0, currentTurnSpeed*0.009f, 0);
		float dx = velocity.x * 0.009f;
		float dz = velocity.z * 0.009f;

		velocity.y+= GRAVITY*0.009f;

		float dy = velocity.y*0.009f;
		
		super.increasePosition(dx, dy, dz);
		frictionEffect(0.009f);

	}
//	public void simulateShot(ArrayList<Entity> entitiesList){
//		//collision
//		ArrayList<Triangle> trianglesList = new ArrayList<Triangle>();
//		ArrayList<BoundingBox> boxes = new ArrayList<BoundingBox>();
//		 
//		for(Entity entity:entitiesList){
//			if(!(entity instanceof Ball)){
//				trianglesList.addAll(entity.getTriangles());
//				boxes.add(entity.getBox());
//			}
//		}
//		long currentTime = Sys.getTime()*1000/Sys.getTimerResolution();
//		float delta = (currentTime-lastSimulationCall)/1000f;
//		System.out.println(delta);
//		System.out.println(DisplayManager.getFrameTimeSeconds());
//		for(Triangle triangle:trianglesList){
//				if(CollisionHandler.collide(this, triangle)){
//					frictionEffect(delta);
//				}
//		}
//		//end of collision
//		
//		super.increaseRotation(0, currentTurnSpeed*delta, 0);
//		float dx = velocity.x *delta;
//		float dz = velocity.z *delta;
//
//		velocity.y+= GRAVITY*delta;
//
//		float dy = velocity.y*delta;
//		super.increasePosition(dx, dy, dz);		
//
//		lastSimulationCall = currentTime;
//	
//	}
	public void amove(ArrayList<Entity> entitiesList){
		checkingInputs();
		//collision
		ArrayList<Triangle> trianglesList = new ArrayList<Triangle>();
		
		for(Entity entity:entitiesList){
			if(!(entity instanceof Ball)){
				trianglesList.addAll(entity.getTriangles());
			}
		}
		for(Triangle triangle:trianglesList){
			CollisionHandler.collide(this, triangle);
				
		}
		//end of collision

		super.increaseRotation(0, currentTurnSpeed*0.008f, 0);
		float dx = velocity.x * 0.008f;
		float dz = velocity.z * 0.008f;

		velocity.y+= GRAVITY* 0.008f;

		float dy = velocity.y* 0.008f;
		super.increasePosition(dx, dy, dz);
		frictionEffect(0.008f);
	
	}
	public Ball simulateShot(ArrayList<Entity>entityList, Vector3f shot){
		isSimulation = true;
		path=new ArrayList<Triangle>();
		Vector3f position = new Vector3f(this.getPosition().x,this.getPosition().y,this.getPosition().z);
		Ball ball = new Ball(this.getModel(), position , this.getRotX(), this.getRotY(), this.getRotZ(), this.getScale());
		ball.setVelocity(shot);
		//long time = 0;//Sys.getTime();
		while((Math.abs(ball.velocity.x)>=011 || Math.abs(ball.velocity.z)>=0.01 || Math.abs(ball.velocity.y)>=Math.abs(0.8+0.01))  && ball.getPosition().y>-1000){
			ball.move(entityList);
			//time++;
			
		}
		//System.out.println("TIME: "+(time));
		isSimulation=false;
		return ball;
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
		else{
			this.velocity.x = 0;	
			this.velocity.z = 0;
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
			this.velocity.y = (float) (RUN_SPEED);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			this.velocity.y = (float) (RUN_SPEED*(-1));
		}else{
			this.velocity.y=0;
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

	private void frictionEffect(float delta){
		if(velocity.length()>0.1){
			//add noise
//			for(Noise noise:noises){
//				noise.apply(this);
//			}
			//add friction
			Vector3f friction = new Vector3f(velocity.x,velocity.y,velocity.z);
			friction.negate();
			friction.normalise();
			friction = Operation.multiplyByScalar(this.friction*delta, friction);
			velocity = Operation.add(friction, velocity);
			}if(Math.abs(velocity.x)<=0.01&& Math.abs(velocity.z)<=0.01 && Math.abs(velocity.y)<=Math.abs(GRAVITY*delta+0.01)){
				velocity.y=GRAVITY*delta;
				velocity.x=0;
				velocity.z=0;
			}

	}

	public float getFriction(){
		return friction;
	}
	
	public float getMinimalSpeed() {
		return minimalSpeed;
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

	public ArrayList<Triangle> getPath() {
		return path;
	}

	public ArrayList<Noise> getNoises() {
		return noises;
	}

	public void addNoises(Noise noise) {
		this.noises.add(noise);
	}
	

}