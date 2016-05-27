package GameManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import collision.Operation;
import entities.Ball;
import entities.Camera;

public class HumanPlayer extends Player{
	
	public HumanPlayer(Camera camera){
		super.setBall(camera.getBall());
		super.setCamera(camera);
	}
	@Override
	public void shoot(){
		Vector3f direction = getShotDirection();
		Vector3f shot = Operation.multiplyByScalar(super.power, direction);
		super.getBall().setVelocity(shot);
	}
	
	private Vector3f getShotDirection(){
		Vector3f temp = new Vector3f();
		Vector3f.sub(super.getBall().getPosition(), super.getCamera().getPosition(), temp);
		temp.set(temp.getX() , 0 , temp.getZ());
		temp.normalise();
		return temp ;
	}
}