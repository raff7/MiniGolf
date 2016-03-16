import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
		if(ball.movement[0] == 0 && ball.movement[1] == 0 && ball.movement[2] == 0){
			return;
		}else{
			collisionHandler.checkWallCollision();
			}
			ball.setMovement(ball.getMovement());
		}
	
	public void frictionEffect(){
		for(int i=0; i<ball.movement.length; i++)
			ball.movement[i]=ball.movement[i]-u;
	}
	
	public void gravityEffect(){
		//Checking if the center of the ball is actually inside the circle determining the hole in the XZ plan
		if(Math.pow((ball.getX()-hole.getX()),2) + Math.pow((ball.getZ()-hole.getZ()),2) < Math.pow(hole.radius,2)){
			ball.setY(ball.getY()-g/delay);
		}
	}
}