package bot;

import javax.management.OperationsException;

import org.lwjgl.util.vector.Vector3f;

import GameManager.Player;
import algorithms.BotAlgorithm;
import collision.CollisionHandler;
import entities.Ball;
import entities.Course;
import toolbox.Operation;

public class shooter extends BotAlgorithm{
	
	//weights values.
	private int shortestPathDistance=-1;
	
	
	private static final int NUMBER_OF_ANGLES = 36;
	private static final int NUMBER_OF_POWERS = 5;
	
	private Ball ball;
	private Course course;
	

	public shooter(Course course){
		this.course = course;
	}
	
	public Vector3f execute(Ball ball) {
		this.ball = ball;
		Vector3f bestShot = null;
		float bestShotValue=Integer.MIN_VALUE;
		int currentAngle=0;
		float currentPower=0;
		Vector3f currentShot;
		float currentValue;
		for(int i=0;i<NUMBER_OF_ANGLES;i++){
			currentAngle= i*NUMBER_OF_ANGLES/360;
			for(int j=0;j<NUMBER_OF_POWERS;j++){
				currentPower = (Player.MAX_POWER/NUMBER_OF_POWERS)*(j+1);
				currentShot = new Vector3f((float)Math.sin(Math.toRadians(currentAngle)),0,(float)Math.cos(Math.toRadians(currentAngle)));
				currentShot.normalise();
				currentShot = Operation.multiplyByScalar(currentPower, currentShot);
				
				currentValue = testShot(currentShot);
				if(currentValue>bestShotValue){
					bestShotValue=currentValue;
					bestShot = currentShot;
				}
			}
		}
		return bestShot;
	}


	private float testShot(Vector3f shot) {
		Node endingNode = null;
		Ball ball = new Ball(this.ball.getModel(), this.ball.getPosition(), this.ball.getRotX(), this.ball.getRotY(), this.ball.getRotZ(), this.ball.getScale());
		ball.setVelocity(shot);
		
		while (ball.getVelocity().length()>2){
			ball.move(course.getEntities());
		}
		for(Node node:course.getNodes()){
			if(CollisionHandler.collide(ball, node)){
				endingNode = node;
			}
		}
		float finale=0;
		
		//shortestPathDistance
		finale += endingNode.getDistance()*shortestPathDistance;
		
		return finale;
	}
}