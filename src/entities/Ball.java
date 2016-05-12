package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import collision.BoundingBox;
//import collision.CheckCollision;
import collision.CollisionHandler;
import collision.CollisionInfo;
import collision.Operation;
import collision.ResponseStep;
import collision.Triangle;
import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import textures.ModelTexture;

public class Ball extends Entity{
	private static final float RUN_SPEED = 2;
	private static final float 	TURN_SPEED = 160;
	private static final float GRAVITY = -100;
	private static final float JUMP_POWER=30;
	private static final float FRICTION = 0.001f;
	private static final float MAX_RUN_SPEED = 100;
	

	private float currentTurnSpeed = 0;
	
	private boolean isInAir=false;
	
	private Vector3f velocity;
	private CollisionInfo colInfo;
	private Vector3f radius;
	private Vector3f eRadius;
	
	private boolean debug=true;
	

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		super(model,0, position, rotX, rotY, rotZ, scale);
		velocity = new Vector3f(0,0,0);
		colInfo = new CollisionInfo(new Vector3f(1,5,1),velocity,getPosition());
	}
	/*
	 * move as a free camera
	 */
	public void move(){
		checkFreeCameraInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();
		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
	}
	
	public void move(Terrain terrain,ArrayList<Entity> entitiesList){ 	
		//System.out.println("ball: "+getPosition());
		/*BoundingBox box = entitiesList.get(0).getBox();
		if(Math.abs(this.getBox().getMaxZ() - box.getMaxX() ) <= 1)
			setVelocity(new Vector3f(0,0,0));*/
		
		//get the coordinates of the velocity, normalized velocity and the basePoint(=the center) in the ellipsoid space
		colInfo.setVelocity(Operation.divideVector(colInfo.getR3Velocity(),colInfo.getEradius()));
		colInfo.setNormalizedVelocity(colInfo.getVelocity().x,colInfo.getVelocity().y,colInfo.getVelocity().z);
		if(colInfo.getNormalizedVelocity().lengthSquared() != 0)
			colInfo.getNormalizedVelocity().normalise();
		colInfo.setBasePoint(Operation.divideVector(getPosition(),colInfo.getEradius()));
		
		if(debug){	
			/*System.out.println();
			System.out.println("velocity "+colInfo.getVelocity());
			System.out.println("normalised velo"+colInfo.getNormalizedVelocity());
			System.out.println("basePoint "+colInfo.getBasePoint());
			System.out.println("pos "+colInfo.getBasePoint());
			System.out.println("IP: "+colInfo.getIntersectionPoint());*/
		}
		ArrayList<Triangle> trianglesList = entitiesList.get(0).getModel().getRawModel().getTriangles();
		
		//System.out.println(trianglesList.get(0));
		int i=0;
		while(i<trianglesList.size()){
			Triangle triangle = trianglesList.get(i);
			CollisionHandler.checkTriangle(colInfo,triangle.getP1(),triangle.getP2(),triangle.getP3());
			//CheckCollision.touchTriangle(triangle, colInfo.getBasePoint());
			i++;
		}
		//System.out.println("collision : "+colInfo.isFoundCollision());
		//if(colInfo.isFoundCollision())
			//setVelocity(new Vector3f(0,0,0));
		if(colInfo.isFoundCollision()){
			System.out.println("collision : "+colInfo.isFoundCollision());
			//ResponseStep.collideAndSlide(colInfo,new Vector3f(0,0,0));
			//System.out.println("R3 vel: "+colInfo.getR3Velocity());
			//setVelocity(colInfo.getR3Velocity());
			
			//setVelocity(new Vector3f(0,0,0));
			//setPosition(ResponseStep.collideAndSlide(colInfo,new Vector3f(0,0,0)));
		}
		
		colInfo.setFoundCollision(false);
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = velocity.x * DisplayManager.getFrameTimeSeconds();
		float dz = velocity.z * DisplayManager.getFrameTimeSeconds();
        velocity.y+= GRAVITY*DisplayManager.getFrameTimeSeconds();
		float dy = velocity.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
	
		float terrainHeight;
		if(terrain != null)
			terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		else
			terrainHeight = 0;
		if(super.getPosition().y<terrainHeight){
			isInAir=false;
			velocity.y = 0;//-velocity.y*0.8f;
			super.getPosition().y=terrainHeight;
		}
	}
	private void jump(){
		if(!isInAir){
			this.velocity.y=JUMP_POWER;
			isInAir=true;
		}
	}
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.velocity.x += RUN_SPEED*Math.sin(Math.toRadians(getRotY()));	
			this.velocity.z += RUN_SPEED*Math.cos(Math.toRadians(getRotY()));		
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.velocity.x -= RUN_SPEED*Math.sin(Math.toRadians(getRotY()));	
			this.velocity.z -= RUN_SPEED*Math.cos(Math.toRadians(getRotY()));			
			}else{
				float temp = this.velocity.y;
				this.velocity.scale(FRICTION);
				this.velocity.y=temp;
//				if(this.velocity.x>0)
//					this.velocity.x=Math.max(this.velocity.x-FRICTION, 0);
//				else
//					this.velocity.x=Math.min(this.velocity.x+FRICTION, 0);
//				if(this.velocity.z>0)
//					this.velocity.z=Math.max(this.velocity.z-FRICTION, 0);
//				else
//					this.velocity.z=Math.min(this.velocity.z+FRICTION, 0);

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
			velocity.x = (float) (MAX_RUN_SPEED*Math.sin(Math.toRadians(getRotY())));
			velocity.z = (float) (MAX_RUN_SPEED*Math.cos(Math.toRadians(getRotY())));

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
	public Vector3f getRadius() {
		return radius;
	}
	public void setRadius(Vector3f radius) {
		this.radius = radius;
	}
	public Vector3f geteRadius() {
		return eRadius;
	}
	public void seteRadius(Vector3f eRadius) {
		this.eRadius = eRadius;
	}
	public CollisionInfo getColInfo(){
		return colInfo;
	}
	
	
	

}
