package GameManager;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import collision.Operation;
import entities.Ball;
import entities.Camera;
import toolbox.HumanPowerController;

public class HumanPlayer extends Player{
	HumanPowerController powerControler;
	public HumanPlayer(Ball ball, HumanPowerController controler){
		super.setBall(ball);
		super.setCamera(new Camera(ball));
		powerControler = controler;
	}
	
	public void shot(){
		Vector3f direction = getShotDirection();
		float power = powerControler.getPower();
		Vector3f shot = Operation.multiplyByScalar(power, direction);
		super.getBall().setVelocity(shot);
	}
	
	private Vector3f getShotDirection(){
		Vector3f temp = new Vector3f();
		Vector3f.sub(super.getBall().getPosition(), super.getCamera().getPosition(), temp);
		temp.set(temp.getX() , 0 , temp.getZ());
		temp.normalise();
		return temp ;
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}
	
	
}
