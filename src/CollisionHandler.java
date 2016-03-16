import java.util.ArrayList;

public class CollisionHandler {
	
	private Ball ball ;
	private ArrayList<Obstacle> obstacles;
	private int delay = 17;

	public CollisionHandler(Ball ball, ArrayList<Obstacle> obstacles){
		
		this.ball = ball ;
		this.obstacles = obstacles ;
	}
	
	public void checkWallCollision(){
		double[] startPoint;
		double[] endPoint;
		for(int n = 0; n<obstacles.size(); n++){
			for(int i = 0; i<obstacles.get(n).getVertices().size(); i++){
				if(i < obstacles.get(n).getVertices.size() - 1){
					startPoint = obstacles.get(n).getVertices().get(i);
					endPoint = obstacles.get(n).getVertices().get(i+1);
				}else{
					startPoint = obstacles.get(n).getVertices().get(i);
					endPoint = obstacles.get(n).getVertices().get(0);
				}
			//Looking for the ortho proj of the center onto the wall (Technique found online)
			double[] wallVector = new double[]{ startPoint[0]-endPoint[0],startPoint[1]-endPoint[1], startPoint[2]-endPoint[2]};
			double numerator = wallVector[0]*(ball.getX()- startPoint[0]) + wallVector[1]*(ball.getY()- startPoint[1]) + wallVector[2]*(ball.getZ()- startPoint[2]);
			double denominator = Math.pow(wallVector[0],2)+Math.pow(wallVector[1],2)+Math.pow(wallVector[2],2);
			double k = numerator/denominator;
			double x = startPoint[0]+k*wallVector[0];
			double y = startPoint[1]+k*wallVector[1];
			double z = startPoint[2]+k*wallVector[2];
			double[] orthoProj = new double[]{x,y,z};
			
			//Check if the distance between the ball is small enough
			double normalDistance = Math.sqrt(Math.pow((orthoProj[0]-ball.getX()), 2) + Math.pow(orthoProj[1]-ball.getY(), 2) + Math.pow(orthoProj[2]-ball.getX(), 2));
			double movementLength = Math.sqrt(Math.pow(ball.getX(), 2) + Math.pow(ball.getY(), 2) + Math.pow(ball.getZ(), 2));
			
			if(normalDistance - ball.radius < movementLength/delay){
				//now have to check if the ball is indeed in between those 2 vertices and not beside (again technique found online)
				double[] vector = new double[]{startPoint[0]-ball.getX(),startPoint[1]-ball.getY(),startPoint[2]-ball.getZ()};//Vector from startPoint to center
				double dotProduct = vector[0]*wallVector[0]+vector[1]*wallVector[1]+vector[2]*wallVector[2];
				double lineVectorLength = Math.sqrt(Math.pow(wallVector[0], 2) + Math.pow(wallVector[1], 2) + Math.pow(wallVector[2], 2));
				double untilProjectLength = Math.sqrt(Math.pow(orthoProj[0]-startPoint[0], 2) + Math.pow(orthoProj[1]-startPoint[1], 2) + Math.pow(orthoProj[2]-startPoint[2], 2));;
				if(dotProduct > 0 && untilProjectLength < lineVectorLength){
					//We have indeed a real collision
					ball.setMovement(getNewVectorAfterCollision(startPoint, endPoint, orthoProj));
				}
			}
		}
		}
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
		if(true){
			System.out.println("ortho project: "+x+" "+y+" "+z);
			System.out.println("vector: "+vector[0]+" "+vector[1]+" "+vector[2]);
			System.out.println("incomingPoint: "+incomingPoint[0]+" "+incomingPoint[1]+" "+incomingPoint[2]);
			System.out.println("outcomingPoint: "+outcomingPoint[0]+" "+outcomingPoint[1]+" "+outcomingPoint[2]);
			System.out.println("New movement: "+newMovement[0]+" "+newMovement[1]+" "+newMovement[2]);
		}
		return newMovement;
		
		}
	

}