package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import textures.ModelTexture;

public class Ball extends Entity{
	private static final float RUN_SPEED = 50;
	private static final float 	TURN_SPEED = 160;
	private static final float GRAVITY = -100;
	private static final float JUMP_POWER=30;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardSpeed = 0;
	
	private boolean isInAir=false;
	
	//Nordine
	private static Vector3f velocity;
	private static Vector3f position;
	private static Vector3f radius;
	private static Vector3f eRadius;

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	public void move(Terrain terrain){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardSpeed+= GRAVITY*DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight;
		if(terrain != null)
			terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		else
			terrainHeight = 0;
		if(super.getPosition().y<terrainHeight){
			isInAir=false;
			upwardSpeed = 0;
			super.getPosition().y=terrainHeight;
		}
	}
	private void jump(){
		if(!isInAir){
			this.upwardSpeed=JUMP_POWER;
			isInAir=true;
		}
	}
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed = RUN_SPEED;	
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed = -RUN_SPEED;
		}else{
			this.currentSpeed=0;
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
			currentSpeed *= 4.5f;
		}
		
	}
	
	public Vector3f getPosition(){
		return position;
	}
	public Vector3f getVelocity(){
		return velocity;
	}
	public Vector3f getEradius(){
		return eRadius;
	}
	
	

}
