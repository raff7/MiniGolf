package physic;

import org.lwjgl.util.vector.Vector3f;

import collision.Operation;
import entities.Ball;
import entities.Camera;

public class HumanInputController {
	
	private Vector3f direction;
	private float power;
	private Vector3f shot;
	Camera camera ;
	Ball ball ;
	
	public HumanInputController(Camera camera , Ball ball){
		this.camera = camera;
		this.ball =  ball;
	}
	
	public void makeAShot(Ball ball){	
		direction = computeShotDirection();
		computeShotPower();
		shot = Operation.multiplyByScalar(power, direction);
		ball.setVelocity(shot);
	}
	
	private Vector3f computeShotDirection(){
		
		Vector3f temp = new Vector3f();
		Vector3f.sub(ball.getPosition(), camera.getPosition(), temp) ;
		temp.set(temp.getX() , 0 , temp.getZ()) ;
		temp.normalise();
		return temp ;
	}
	
	private void computeShotPower(){
		power=100f;	
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
		power = pow;
	}
}
