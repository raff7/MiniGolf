import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

public class PhysicsEngine {
	//gravitational constant
	final double g = 9.81;
	//friction's coefficient
	final double u = 0.01;
	Ball ball;
	Hole hole;
	//List storing the borders of the course and all the obstacle objects. Each of them has list containing their vertices
	ArrayList<Obstacle> obstacles;
	int delay = 17;
	Timer timer;
	CollisionHandler collisionHandler;
	private boolean isInHole = false ;
	final static boolean DEBUG = true;
	
	public PhysicsEngine(Ball ball, ArrayList<Obstacle> obstacles ){
		this.ball = ball;
		this.obstacles = obstacles;
		this.collisionHandler = new CollisionHandler(ball,obstacles);
		
		ActionListener moveBall = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ball.setX(ball.getX()+ball.movement[0]/delay);
				ball.setY(ball.getY()+ball.movement[1]/delay);
				ball.setZ(ball.getZ()+ball.movement[2]/delay);
			}
		};
		timer = new Timer(delay,moveBall);
	}
	
	public void updateGameState(){
		frictionEffect();
		gravityEffect();
		checkIfInHole() ;
		if(isInHole == true){
			putting() ;
		}
		if(ball.movement[0] <= u && ball.movement[1] <= u && ball.movement[2] <= u){
			double[] end = new double[3] ;
			for(int i = 0 ; i < end.length ; i++){
				end[i] = 0 ;
			}
			ball.setMovement(end);
		}else{
			collisionHandler.checkWallCollision();
			}
			ball.setMovement(ball.getMovement());
	}
		
	public void checkIfInHole(){
		
		if(Math.pow((ball.getX()-hole.getX()),2) + Math.pow((ball.getZ()-hole.getZ()),2) < Math.pow(hole.radius,2)){
			isInHole = true ;
		}
		else{
			isInHole = false ;
		}
	}
	
	public void putting(){
		
		if(ball.getSpeed() < 2 ){
			double[] endSpeed = new double[3] ;
			for(int i = 0 ; i < endSpeed.length ; i++){
				endSpeed[i] = 0 ;
			}
			ball.setMovement(endSpeed);
		}
		else if(ball.getSpeed() >= 2 && ball.getSpeed() <= 10){
			Random rn = new Random() ;
			int number = rn.nextInt(10 - 1 + 1) + 1 ;
			if(number <= 3){
				double[] endSpeed = new double[3] ;
				for(int i = 0 ; i < endSpeed.length ; i++){
					endSpeed[i] = 0 ;
				}
				ball.setMovement(endSpeed);
			}
			else{
				return ;
			}
		}
		else{
			return ;
		}
		
	}
	
	public void frictionEffect(){
		for(int i=0; i<ball.movement.length; i++)
			ball.movement[i] = ball.movement[i] * 0.95 ;
	}
	
	public void gravityEffect(){
		//Checking if the center of the ball is actually inside the circle determining the hole in the XZ plan
		if(Math.pow((ball.getX()-hole.getX()),2) + Math.pow((ball.getZ()-hole.getZ()),2) < Math.pow(hole.radius,2)){
			ball.setY(ball.getY()-g/delay);
		}
	}
}