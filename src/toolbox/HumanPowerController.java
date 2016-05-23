package toolbox;

import org.lwjgl.input.Keyboard;
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
		//computeShotPower();
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
	
	public void computeShotPower(){
		power=100f;	
	}
	
	public Vector3f getDirection(){
		
		return direction ;
	}
	
	public void setDirection(Vector3f dir){
		
		direction = dir ;
	}
	
	public double getPower(){
		
		return power ;
	}
	
	public void setPower(){
		float pow = 0;
		while(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && pow < 150){
			pow+=0.001;
			System.out.println(Keyboard.isKeyDown(Keyboard.KEY_SPACE));
			System.out.println(pow);
		}
		power=pow;
	}
}
