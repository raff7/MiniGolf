package physic;

import org.lwjgl.util.vector.Vector3f;
import entities.Ball;
import entities.Camera;

public class HumanInputController {
	
	private Vector3f direction ;
	private double power ;
	Camera camera ;
	Ball ball ;
	
	public HumanInputController(Camera camera , Ball ball){
		
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
	
	private void setPower(double pow){
		
		power = pow ;
	}
	
	private void makeAShot(Ball ball){
		
		direction = computeShotDirection() ;
	}
	
	private Vector3f computeShotDirection(){
		
		Vector3f temp = null ;
		Vector3f.sub(ball.getPosition(), camera.getPosition(), temp) ;
		temp.set(temp.getX() , 0 , temp.getZ()) ;
		return temp ;
		
	}
	
	private double computShotPower(){
		double pow ;
		
		return pow ;
		
	}

}
