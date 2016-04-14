package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private static final float Y_OFFSET = 10;
	private float distanceFromBall = 70;
	private float angleAroundBall = 0;
	
	private Vector3f position= new Vector3f(0,5,0); 
	private float pitch = 5;
	private float yaw ;
	private float roll;
	private Ball ball;
	
	public Camera(Ball ball){
	this.ball = ball;
	}
	
	public void move(){
		
		calculateZoom();
		calculatePitch();
		calculateAngleAroundBall();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance,verticalDistance);
		this.yaw = 180-(ball.getRotY()+angleAroundBall);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	private void calculateCameraPosition(float horizontalDistance,float verticalDistance){
		float theta = ball.getRotY()+angleAroundBall;
		float offsetZ= (float) (horizontalDistance *Math.cos(Math.toRadians(theta)));
		float offsetX= (float) (horizontalDistance *Math.sin(Math.toRadians(theta)));
		position.x =ball.getPosition().x -offsetX;
		position.z = ball.getPosition().z - offsetZ;
		position.y= ball.getPosition().y +verticalDistance+Y_OFFSET;
	}
	private float calculateHorizontalDistance(){
		return (float) ( distanceFromBall*Math.cos((Math.toRadians(pitch))));
	}
	private float calculateVerticalDistance(){
		return (float) ( distanceFromBall*Math.sin((Math.toRadians(pitch))));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel()*0.05f;
		distanceFromBall = Math.min(Math.max(distanceFromBall-zoomLevel,50),400);
	}
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY()*0.1f;
			pitch = Math.min(Math.max(pitch-pitchChange,5),80);
		}
	}
	private void calculateAngleAroundBall(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX()*0.3f;
			angleAroundBall -=angleChange;
		}
	}
	

}
