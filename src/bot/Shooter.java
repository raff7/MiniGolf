package bot;

import javax.management.OperationsException;

import org.lwjgl.util.vector.Vector3f;

import GameManager.Player;
import algorithms.BotAlgorithm;
import collision.CollisionHandler;
import entities.Ball;
import entities.Course;
import renderEngine.DisplayManager;
import toolbox.Operation;

public class Shooter extends BotAlgorithm{
	
	//weights values.
	private int shortestPathDistance=1;
	
	
	private static final int NUMBER_OF_ANGLES = 36;
	private static final int NUMBER_OF_POWERS = 8;
	
	private Ball ball;
	private Course course;

	
	public Shooter(Course course){
		this.course = course;
	}
	
	public Vector3f execute(Ball ball) {
		
		this.ball = ball;
		Vector3f bestShot = null;
		float bestShotValue = Integer.MIN_VALUE;
		float currentAngle=0;
		float currentPower=0;
		Vector3f currentShot;
		float currentValue;
		
		for(int i=0; i<NUMBER_OF_ANGLES; i++){
			currentAngle = i*(360/NUMBER_OF_ANGLES);
			for(int j = 0; j<NUMBER_OF_POWERS; j++){
				currentPower = (Player.MAX_POWER/NUMBER_OF_POWERS)*(j+1);

				currentShot = new Vector3f( (float)Math.sin(Math.toRadians(currentAngle)),0, (float)Math.cos(Math.toRadians(currentAngle)));
				currentShot.normalise();
				currentShot = Operation.multiplyByScalar(currentPower, currentShot);
				
				currentValue = testShot(currentShot);


				if(currentValue > bestShotValue){
					bestShotValue=currentValue;
					bestShot = currentShot;

				}				

			}
		}
		return bestShot;
	}


	private float testShot(Vector3f shot) {
		Node endingNode = null;	
		Ball ballAfterSimulation = this.ball.simulateShot(course.getEntities(),shot);
		if(ballAfterSimulation.getPosition().y<-1000){
			return Float.MIN_VALUE;

		}

		for(Node node : course.getNodes()){
			if(ballAfterSimulation.getLastTriangleHit()!=null && node.isEqual(ballAfterSimulation.getLastTriangleHit())){
				endingNode = node;
			}
		}
		
		if(endingNode == null){
			return 	Float.MAX_VALUE;

		}
		float finale = 0;

		//shortestPathDistance
		finale -= endingNode.getDistance()*shortestPathDistance;
		
		return finale;
	}
}
