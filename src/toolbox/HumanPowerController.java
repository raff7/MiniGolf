package toolbox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import collision.Operation;
import entities.Ball;
import entities.Camera;

public class HumanPowerController {
	
	private float power;

	public HumanPowerController(){}
		
	public float getPower(){
		power=0;
		while(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && power < 150){
			power += 0.001;
			System.out.println(Keyboard.isKeyDown(Keyboard.KEY_SPACE));
			System.out.println(power);
		}
		return power;
	}
}
