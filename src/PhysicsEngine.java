import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class PhysicsEngine {
	//gravitational constant
	final double g = 9.81;
	//friction's coefficient
	final double u = 0;
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
		
		/*ActionListener moveBall = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ball.setX(ball.getX()+ball.movement[0]/59);
				ball.setY(ball.getY()+ball.movement[1]/59);
				ball.setZ(ball.getZ()+ball.movement[2]/59);
			}
		};
		timer = new Timer(delay,moveBall);
		timer.start();*/
	}
	
	public void updateGameState(){
		//ball.setCoordinates(ball.movement);
		//System.out.println("real movement "+ball.movement[0]+" "+ball.movement[1]+" "+ball.movement[2]);
		ball.setX(ball.getX()+ball.movement[0]);
		ball.setY(ball.getY()+ball.movement[1]);
		ball.setZ(ball.getZ()+ball.movement[2]);
		//System.out.println("physics x and z "+ball.getX()+" "+ball.getY()+" "+ball.getZ());
		frictionEffect();
		//gravityEffect();
		if(ball.movement[0] == 0 && ball.movement[1] == 0 && ball.movement[2] == 0){
			return;
		}else{
			/*if(!ball.collision){
				System.out.println(ball.collision);
				collisionHandler.checkWallCollision();
			}
			ball.setCoordinates(ball.movement);
		*/
			collisionHandler.checkWallCollision();
			}
			
			//ball.setMovement(ball.getMovement());
		}
	
	public void frictionEffect(){
		for(int i=0; i<ball.movement.length; i++){
			if(ball.movement[i] > 0){
				if(ball.movement[i]-u > 0)
					ball.movement[i] = ball.movement[i]-u;
				else
					ball.movement[i] = 0;
			}else{
				if(ball.movement[i]+u < 0)
					ball.movement[i] += u;
				else
					ball.movement[i]=0;
			}
		}
	}
	
	/*public void gravityEffect(){
		//Checking if the center of the ball is actually inside the circle determining the hole in the XZ plan
		if(Math.pow((ball.getX()-hole.getX()),2) + Math.pow((ball.getZ()-hole.getZ()),2) < Math.pow(hole.radius,2)){
			if(ball.)
			ball.setY(ball.getY()-g/59);
		}
	}*/
}