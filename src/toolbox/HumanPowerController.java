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
		
		/*while(Keyboard.isKeyDown(Keyboard.KEY_SPACE) ){
				power += 0.001;
				System.out.println("loop");
		}*/
		
		while (Keyboard.next()) {
			   if (Keyboard.getEventKeyState()) {
			   while(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				   power += 0.001;
					System.out.println("loop");
			   }
			   }
			 }
		return power;
	}
}
