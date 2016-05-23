package GameManager;

import entities.Ball;
import entities.Camera;

public class HumanPlayer extends Player{
	
	public HumanPlayer(Ball ball){
		super.setBall(ball);
		super.setCamera(new Camera(ball));
	}
	
	
}
