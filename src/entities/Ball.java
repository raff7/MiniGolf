package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

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
	

	private Vector3f currentSpeed;
	private float currentTurnSpeed = 0;
	
	private boolean isInAir=false;
	
	private static Vector3f velocity;
	private static Vector3f radius;
	private static Vector3f eRadius;
	

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		currentSpeed = new Vector3f(0,0,0);
	}
	/*
	 * move as a free camera
	 */
	public void move(){
		checkFreeCameraInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = currentSpeed.x * DisplayManager.getFrameTimeSeconds();
		float dz = currentSpeed.z * DisplayManager.getFrameTimeSeconds();
		float dy = currentSpeed.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
	}
	
	public void move(Terrain terrain){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float dx = currentSpeed.x * DisplayManager.getFrameTimeSeconds();
		float dz = currentSpeed.z * DisplayManager.getFrameTimeSeconds();
		currentSpeed.y+= GRAVITY*DisplayManager.getFrameTimeSeconds();
		float dy = currentSpeed.y*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, dy, dz);
		float terrainHeight;
		if(terrain != null)
			terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		else
			terrainHeight = 0;
		if(super.getPosition().y<terrainHeight){
			isInAir=false;
			currentSpeed.y = 0;//-currentSpeed.y*0.8f;
			super.getPosition().y=terrainHeight;
		}
	}
	private void jump(){
		if(!isInAir){
			this.currentSpeed.y=JUMP_POWER;
			isInAir=true;
		}
	}
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed.x += RUN_SPEED*Math.sin(Math.toRadians(getRotY()));	
			this.currentSpeed.z += RUN_SPEED*Math.cos(Math.toRadians(getRotY()));		
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed.x -= RUN_SPEED*Math.sin(Math.toRadians(getRotY()));	
			this.currentSpeed.z -= RUN_SPEED*Math.cos(Math.toRadians(getRotY()));			
			}else{
				float temp = this.currentSpeed.y;
				this.currentSpeed.scale(FRICTION);
				this.currentSpeed.y=temp;
//				if(this.currentSpeed.x>0)
//					this.currentSpeed.x=Math.max(this.currentSpeed.x-FRICTION, 0);
//				else
//					this.currentSpeed.x=Math.min(this.currentSpeed.x+FRICTION, 0);
//				if(this.currentSpeed.z>0)
//					this.currentSpeed.z=Math.max(this.currentSpeed.z-FRICTION, 0);
//				else
//					this.currentSpeed.z=Math.min(this.currentSpeed.z+FRICTION, 0);

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
			currentSpeed.x = (float) (MAX_RUN_SPEED*Math.sin(Math.toRadians(getRotY())));
			currentSpeed.z = (float) (MAX_RUN_SPEED*Math.cos(Math.toRadians(getRotY())));

		}
		
	}
	private void checkFreeCameraInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed.x = (float) (RUN_SPEED*100*Math.sin(Math.toRadians(getRotY())));	
			this.currentSpeed.z = (float) (RUN_SPEED*100*Math.cos(Math.toRadians(getRotY())));	
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed.x = (float) (-RUN_SPEED*100*Math.sin(Math.toRadians(getRotY())));	
			this.currentSpeed.z = (float) (-RUN_SPEED*100*Math.cos(Math.toRadians(getRotY())));	
		
			}else{
			this.currentSpeed.x=0;
			this.currentSpeed.z=0;
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
			currentSpeed.y=-GRAVITY*1.5f;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			currentSpeed.y=GRAVITY*1.5f;
		}else{
			currentSpeed.y=0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			currentSpeed.x *= 1.5f;
			currentSpeed.y *= 1.5f;
			currentSpeed.z *= 1.5f;

		}
		
	}
	public static Vector3f getVelocity() {
		return velocity;
	}
	public static void setVelocity(Vector3f velocity) {
		Ball.velocity = velocity;
	}
	public static Vector3f getRadius() {
		return radius;
	}
	public static void setRadius(Vector3f radius) {
		Ball.radius = radius;
	}
	public static Vector3f geteRadius() {
		return eRadius;
	}
	public static void seteRadius(Vector3f eRadius) {
		Ball.eRadius = eRadius;
	}
	
	

}
