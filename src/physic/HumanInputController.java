package physic;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import entities.Ball;
import entities.Camera;
import entities.Course;

public class HumanInputController {
	
	private Vector3f direction ;
	private float power ;
	Camera camera ;
	Ball ball ;
	
	public HumanInputController(Course course, Camera cam){
		ball = course.getBall() ;
		camera = cam ;
		
	}
	
	private Vector3f getDirection(){
		
		return direction ;
	}
	
	private void setDirection(Vector3f dir){
		
		direction = dir ;
	}
	
	private double getPower(){
		
		return power ;
	}
	
	private void setPower(float pow){
		
		power = pow ;
	}
	
	public Vector3f makeAShot(){
		Vector3f shot = null ;
		direction = computeShotDirection() ;
		power = computeShotPower() ;
		direction.scale(power) ;
		shot = direction ;
		return shot ;
	}
	
	
	

	private Vector3f computeShotDirection(){
		System.out.println("ball position " + ball.getPosition().getX() + " , " + ball.getPosition().getY() + " , " + ball.getPosition().getZ());
		System.out.println("camera position " + camera.getPosition().getX() + " , " + camera.getPosition().getY() + " , " + camera.getPosition().getZ());
		Vector3f temp = null ;
		Vector3f.sub(ball.getPosition(), camera.getPosition(), temp) ;
		temp.set(temp.getX() , 0 , temp.getZ()) ;
		return temp ;
		
	}
	
	private float computeShotPower(){
		float pow = 0f ;
		while(Mouse.isButtonDown(0)){
			while(pow <= 100){
				pow++ ;
			}
			while(pow >= 0){
				pow-- ;
			} 
		}
		return pow ;
		
	}
	
	

}
