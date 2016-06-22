package bot;

import javax.management.OperationsException;

import org.lwjgl.util.vector.Vector3f;

import GameManager.Player;
import algorithms.BotAlgorithm;
import collision.CollisionHandler;
import entities.Ball;
import entities.Course;
import toolbox.Operation;

public class Shooter extends BotAlgorithm{
	
	//weights values.
	private int shortestPathDistance=1;
	
	
	private static final int NUMBER_OF_ANGLES = 36;
	private static final int NUMBER_OF_POWERS = 5;
	
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
				System.out.println(currentAngle);
				currentPower = (Player.MAX_POWER/NUMBER_OF_POWERS)*(j+1);
				currentShot = new Vector3f( (float)Math.sin(Math.toRadians(currentAngle)),0, (float)Math.cos(Math.toRadians(currentAngle)));
				currentShot.normalise();
				currentShot = Operation.multiplyByScalar(currentPower, currentShot);
				
				currentValue = testShot(currentShot);
				if(currentValue >= bestShotValue){
					bestShotValue=currentValue;
					bestShot = currentShot;
				}
			}
		}
		return bestShot;
	}


	private float testShot(Vector3f shot) {
		Node endingNode = null;
		Vector3f position = new Vector3f(this.ball.getPosition().x, this.ball.getPosition().y, this.ball.getPosition().z);
		Ball ball = new Ball(this.ball.getModel(), position, this.ball.getRotX(), this.ball.getRotY(), this.ball.getRotZ(), this.ball.getScale());
		ball.simulateShot(course.getEntities(),shot);
		
		for(Node node : course.getNodes()){
			if(ball.getLastTriangleHit()!=null && node.isEqual(ball.getLastTriangleHit())){
				endingNode = node;
			}
		}
		float finale = 0;
		

		if(endingNode == null){
			finale = Float.MAX_VALUE;
			return finale;
		}
		
		//shortestPathDistance
		finale -= endingNode.getDistance()*shortestPathDistance;
		
		return finale;
	}
}

