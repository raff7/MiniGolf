package physic;


public class CollisionHandler {
	
	private Ball ball ;
	private Obstacle obstacle;
	private double[] collisionPoint = new double[3] ;
	
	public CollisionHandler(Ball ball, Obstacle obstacle, double[] point){
		
		this.ball = ball ;
		this.obstacle = obstacle ;
		this.collisionPoint = point ;
				
	}
	

}
