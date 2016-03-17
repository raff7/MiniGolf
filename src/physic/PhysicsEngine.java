package physic;

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
	//List storing the vertices determining each wall
	ArrayList<double[]> vertices;
	int delay = 17;
	Timer timer;
	final static boolean DEBUG = true;
	
	public PhysicsEngine(Ball ball, ArrayList<double[]> vertices ){
		this.ball = ball;
		this.vertices = vertices;
		
		ActionListener moveBall = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ball.setX(ball.getX()+ball.movement[0]/delay);
				ball.setY(ball.getY()+ball.movement[1]/delay);
				ball.setZ(ball.getZ()+ball.movement[2]/delay);
			}
		};
		timer = new Timer(delay,moveBall);
	}
	
	public void update(){
		frictionEffect();
		gravityEffect();
		if(ball.movement[0] == 0 && ball.movement[1] == 0 && ball.movement[2] == 0){
			return;
		}else{
			double[][] data = getCollisionWall2();
			if(data != null){
				double[] point1 = new double[]{data[0][0],data[1][0],data[2][0]};
				double[] point2 = new double[]{data[0][1],data[1][1],data[2][1]};
				double[] collisionPoint = new double[]{data[0][2],data[1][2],data[2][2]};
				double[] newMovement = getNewVectorAfterCollision(point1,point2,collisionPoint);
				ball.setMovement(newMovement);
			}
		}
	}
		/*else{
			double[][] wall1 = getCollisionWall(getHeadingPoint());
			if(wall1 != null ){
				double[] point1 = {wall1[0][0],wall1[0][1],wall1[0][2]};
				double[] point2 = {wall1[1][0],wall1[1][1],wall1[2][2]};
				double[] vectorOut = getNewVectorAfterCollision(point1, point2, getHeadingPoint());
				ball.setMovement(vectorOut);
			}
			double[][] wall2 = getCollisionWall(ball.getLeftMostPoint());
			if(wall2 != null ){
				double[] point1 = {wall2[0][0],wall2[0][1],wall2[0][2]};
				double[] point2 = {wall2[1][0],wall2[1][1],wall2[2][2]};
				double[] vectorOut = getNewVectorAfterCollision(point1, point2,ball.getLeftMostPoint());
				ball.setMovement(vectorOut);
			}
			double[][] wall3 = getCollisionWall(ball.getRightMostPoint());
			if(wall3 != null ){
				double[] point1 = {wall3[0][0],wall3[0][1],wall3[0][2]};
				double[] point2 = {wall3[1][0],wall3[1][1],wall3[2][2]};
				double[] vectorOut = getNewVectorAfterCollision(point1, point2,ball.getRightMostPoint());
				ball.setMovement(vectorOut);
			}
			double[][] wall4 = getCollisionWall(ball.getFurthestPoint());
			if(wall4 != null ){
				double[] point1 = {wall4[0][0],wall4[0][1],wall4[0][2]};
				double[] point2 = {wall4[1][0],wall4[1][1],wall4[2][2]};
				double[] vectorOut = getNewVectorAfterCollision(point1, point2,ball.getFurthestPoint());
				ball.setMovement(vectorOut);
			}
			double[][] wall5 = getCollisionWall(ball.getClosestPoint());
			if(wall5 != null ){
				double[] point1 = {wall5[0][0],wall5[0][1],wall5[0][2]};
				double[] point2 = {wall5[1][0],wall5[1][1],wall5[2][2]};
				double[] vectorOut = getNewVectorAfterCollision(point1, point2,ball.getClosestPoint());
				ball.setMovement(vectorOut);
			}
		}*/
	
	public double[] getHeadingPoint(){
		double sumOfSquaredVectorElements = Math.pow(ball.movement[0],2) +Math.pow(ball.movement[1],2)+Math.pow(ball.movement[2],2);
		double lengthV = Math.sqrt(sumOfSquaredVectorElements);
		double k = ball.getRadius()/lengthV;
		
		double x = ball.getX()+k*ball.movement[0];
		double y = ball.getY()+k*ball.movement[1];
		double z = ball.getZ()+k*ball.movement[2];
		
		return new double[]{x,y,z};
	}
	
	/*public double getCollisionAngle(double[] vectorIn, double[] point1, double[] point2){
		double deltaX = point1[0]-point2[0];
		double deltaY = point1[1]-point2[1];
		double deltaZ = point1[2]-point2[2];
		double[] wall = {deltaX,deltaY,deltaZ};
		
		//Lengths of both vectors
		double sumOfSquaredVectorElements = Math.pow(vectorIn[0],2) +Math.pow(vectorIn[1],2)+Math.pow(vectorIn[2],2);
		double lengthVectorIn =Math.sqrt(sumOfSquaredVectorElements);
		double sumOfSquaredVectorElements2 = Math.pow(wall[0],2) +Math.pow(wall[1],2)+Math.pow(wall[2],2);
		double lengthWall = Math.sqrt(sumOfSquaredVectorElements2);
		
		double dotProduct = vectorIn[0]*wall[0]+vectorIn[1]*wall[1]+vectorIn[2]*wall[2];
		double productOfLength = lengthVectorIn*lengthWall;
		double cos = dotProduct/productOfLength;
		double angle = Math.acos(cos);
		
		return angle;
	}*/
	
	public double[][] getCollisionWall(double[] point){
		for(int i =0; i<vertices.size(); i++){
			if(i < vertices.size() - 1){
				double[] startPoint = vertices.get(i);
				double[] endPoint = vertices.get(i+1);
				double[] wallVector = new double[]{endPoint[0]-startPoint[0], endPoint[1]-startPoint[1],endPoint[2]-startPoint[2]};
				//now we determine the parametric equation of the line determining the wall.
				double k0 = (point[0]-startPoint[0])/wallVector[0];
				double k1 = (point[1]-startPoint[1])/wallVector[1];
				double k2 = (point[2]-startPoint[2])/wallVector[2];
				
				if(Math.abs(k0-k1)<0.3 && Math.abs(k0-k2)<0.3 && Math.abs(k1-k2)<0.3)
					return new double[][]{{startPoint[0],endPoint[0]},
										  {startPoint[1],endPoint[1]},
										  {startPoint[2],endPoint[2]}};
				
			}else{
				double[] startPoint = vertices.get(i);
				double[] endPoint = vertices.get(0);
				double[] wallVector = new double[]{endPoint[0]-startPoint[0], endPoint[1]-startPoint[1],endPoint[2]-startPoint[2]};
				//now we determine the parametric equation of the line determining the wall.
				double k0 = (point[0]-startPoint[0])/wallVector[0];
				double k1 = (point[1]-startPoint[1])/wallVector[1];
				double k2 = (point[2]-startPoint[2])/wallVector[2];
				
				if(Math.abs(k0-k1)<0.3 && Math.abs(k0-k2)<0.3 && Math.abs(k1-k2)<0.3)
					return new double[][]{{startPoint[0],endPoint[0]},
					  					  {startPoint[1],endPoint[1]},
					  					  {startPoint[2],endPoint[2]}};
			}
		}
		return null;
		}
	
	public double[][] getCollisionWall2(){
		double[] startPoint;
		double[] endPoint;
		for(int i = 0; i<vertices.size(); i++){
			if(i < vertices.size() - 1){
				startPoint = vertices.get(i);
				endPoint = vertices.get(i+1);
			}else{
				startPoint = vertices.get(i);
				endPoint = vertices.get(0);
			}
				for(double x = startPoint[0]; x<endPoint[0]; x= x+(endPoint[0]-startPoint[0])/100 ){
					for(double y = startPoint[1]; y<endPoint[1]; y= y+(endPoint[1]-startPoint[1])/100 ){
						for(double z = startPoint[2]; z<endPoint[2]; z= z+(endPoint[2]-startPoint[2])/100 ){
							double d = Math.pow((ball.getX()-x),2) + Math.pow((ball.getY()-y),2) + Math.pow((ball.getZ()-z),2);
							if(Math.sqrt(d) - ball.getRadius() < 0.1)
								return new double[][]{{startPoint[0],endPoint[0],x},
			  					  					  {startPoint[1],endPoint[1],y},
			  					  					  {startPoint[2],endPoint[2],z}};
						}
					}
				}
		}if(DEBUG)
			System.out.println("no collision wall");
			
			return null;
	}
	
	public double[] getNewVectorAfterCollision(double[] point1, double[] point2, double[] collisionPoint){
		//Finding the orthogonal projection of the incomingPoint on the wall the ball just hit
		double[] incomingPoint = new double[]{ball.getX()-ball.movement[0],ball.getY()-ball.movement[1],ball.getZ()-ball.movement[2]};
		double[] wallVector = new double[]{ point1[0]-point2[0],point1[1]-point2[1], point1[2]-point2[2]};
		double numerator = wallVector[0]*(incomingPoint[0]- point1[0]) + wallVector[1]*(incomingPoint[1]- point1[1]) + wallVector[2]*(incomingPoint[2]- point1[2]);
		double denominator = Math.pow(wallVector[0],2)+Math.pow(wallVector[1],2)+Math.pow(wallVector[2],2);
		double k = numerator/denominator;
		double x = point1[0]+k*wallVector[0];
		double y = point1[1]+k*wallVector[1];
		double z = point1[2]+k*wallVector[2];
		
		//Ortho projection of the center onto the wall
		double[] orthoProj = new double[]{x,y,z};
		
		//Now we can get the vector determined by the collision point and the orthogonal projection
		double[] vector = new double[]{collisionPoint[0]-orthoProj[0],collisionPoint[1]-orthoProj[1],collisionPoint[2]-orthoProj[2]};
		
		//And simply add it twice to incomingPoint to get the outComingPoint
		double[] outcomingPoint = new double[3];
		for(int i =0; i<outcomingPoint.length; i++)
			outcomingPoint[i] = incomingPoint[i]+2*vector[i]; 
		
		double[] newMovement = new double[]{outcomingPoint[0]-collisionPoint[0],outcomingPoint[1]-collisionPoint[1],outcomingPoint[2]-collisionPoint[2]};
		if(DEBUG){
			System.out.println("ortho project: "+x+" "+y+" "+z);
			System.out.println("vector: "+vector[0]+" "+vector[1]+" "+vector[2]);
			System.out.println("incomingPoint: "+incomingPoint[0]+" "+incomingPoint[1]+" "+incomingPoint[2]);
			System.out.println("outcomingPoint: "+outcomingPoint[0]+" "+outcomingPoint[1]+" "+outcomingPoint[2]);
			System.out.println("New movement: "+newMovement[0]+" "+newMovement[1]+" "+newMovement[2]);
		}
		return newMovement;
		
		}
	/*public double[] getNewVectorAfterCollision(double[] point1, double[] point2){
		//Finding the orthogonal projection of the incomingPoint on the wall the ball just hit
		double[] contactPoint = getContactPoint();
		double[] incomingPoint = new double[]{ball.getX()-ball.movement[0],ball.getY()-ball.movement[1],ball.getZ()-ball.movement[2]};
		double[] wallVector = new double[]{ point1[0]-point2[0],point1[1]-point2[1], point1[2]-point2[2]};
		double numerator = wallVector[0]*(incomingPoint[0]- point1[0]) + wallVector[1]*(incomingPoint[1]- point1[1]) + wallVector[2]*(incomingPoint[2]- point1[2]);
		double denominator = Math.pow(wallVector[0],2)+Math.pow(wallVector[1],2)+Math.pow(wallVector[2],2);
		double k = numerator/denominator;
		double x = point1[0]+k*wallVector[0];
		double y = point1[1]+k*wallVector[1];
		double z = point1[2]+k*wallVector[2];
		
		}*/
	
	public void frictionEffect(){
		for(int i=0; i<ball.movement.length; i++)
			ball.movement[i]=ball.movement[i]-u;
	}
	
	public void gravityEffect(){
		//Checking if the center of the ball is actually inside the circle determining the hole in the XZ plan
		if(Math.pow((ball.getX()-hole.getX()),2) + Math.pow((ball.getZ()-hole.getZ()),2) < Math.pow(hole.getRadius(),2)){
			ball.setY(ball.getY()-g/delay);
		}
	}
}
