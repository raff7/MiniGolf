public class Ball {
	double speed;
	double mass;
	double[] coordinates = new double[3];
	/*Allows us to know in which direction the ball is going
	 * Is composed of ±1 or 0 meaning that the ball is moving along the positive or negative axis 
	 * or is static.
	 */
	double[] movement= new double[3];
	double radius = 0.02;
	//friction with the ground
	
	public Ball(double[] c, double[] m){
		coordinates = c;
		movement = m;
	}
	//Some getters
		public double[] getPosition(){
			return coordinates;
		}
		
		public double getRadius(){
			return radius;
		}
		
		public double getSpeed(){
			computeSpeed() ;
			return speed;
		}
		
		public double[] getMovement(){
			return movement;
		}
		public double getX(){
			return coordinates[0];
		}
		public double getY(){
			return coordinates[1];
		}
		public double getZ(){
			return coordinates[2];
		}
		
		public double[] getLeftMostPoint(){
			return new double[]{coordinates[0] - getRadius(), coordinates[1], coordinates[2]};
		}
		
		public double[] getRightMostPoint(){
			return new double[]{coordinates[0] + getRadius(), coordinates[1], coordinates[2]};
		}
		
		public double[] getFurthestPoint(){
			return new double[]{coordinates[0],coordinates[1],coordinates[2]-getRadius()}; 
		}
		
		public double[] getClosestPoint(){
			return new double[]{coordinates[0],coordinates[1],coordinates[2]+getRadius()}; 
		}
	//Some setters
		public void setX(double x){
			coordinates[0] =x;
		}
		
		public void setY(double y){
			coordinates[1] =y;
		}
		
		public void setZ(double z){
			coordinates[2] =z;
		}
		
		public void setMovement(double[] d){
			movement[0]=d[0];
			movement[1]=d[1];
			movement[2]=d[2];
		}
		
		public double getMovement0(){
			return movement[0] ;
		}
		
		public double getMovement1(){
			return movement[1] ;
		}
	
		public double getMovement2(){
			return movement[2] ;
		}
		public void computeSpeed(){	
			speed = Math.abs(Math.sqrt(Math.pow(getMovement0(), 2) + Math.pow(getMovement1(), 2) + Math.pow(getMovement2(), 2))) ;	
		}
		
		public void setCoordinates(double[] movement){
			for(int i=0; i<coordinates.length; i++)
				coordinates[i]+=movement[i];
		}
	
	
	
}

