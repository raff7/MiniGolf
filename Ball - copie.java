
public class Ball {
	double speed;
	double mass;
	double[] coordinates = new double[3];
	/*Allows us to know in which direction the ball is going
	 * Is composed of ±1 or 0 meaning that the ball is moving along the positive or negative axis 
	 * or is static.
	 */
	double[] movement= new double[3];
	double radius = 1;
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
		
		public void setSpeed(double s){
			speed =s;
		}
		
		public void setMovement(double[] d){
			movement[0]=d[0];
			movement[1]=d[1];
			movement[2]=d[2];
		}
	
	
	
	
	
}
