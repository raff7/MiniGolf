package physic;

public class Ball {
	double mass;
	Point coordinates;
	/*Allows us to know in which direction the ball is going
	 * Is composed of ±1 or 0 meaning that the ball is moving along the positive or negative axis 
	 * or is static.
	 */
	double[] movement= new double[3];
	private double radius = 5;
	//friction with the ground
	
	public Ball(Point c, double[] m){
		coordinates = c;
		movement = m;
	}
	//Some getters
		public Point getPosition(){
			return coordinates;
		}
		
		public double getRadius(){
			return radius;
		}
		
		public double[] getMovement(){
			return movement;
		}
		public double getX(){
			return coordinates.getX();
		}
		public double getY(){
			return coordinates.getY();
		}
		public double getZ(){
			return coordinates.getZ();
		}
		
		public Point getLeftMostPoint(){
			return new Point(coordinates.getX() - getRadius(), coordinates.getY(), coordinates.getZ());
		}
		
		public Point getRightMostPoint(){
			return new Point(coordinates.getX() + getRadius(), coordinates.getY(), coordinates.getZ());
		}
		
		public Point getFurthestPoint(){
			return new Point(coordinates.getX(),coordinates.getY(),coordinates.getZ()-getRadius()); 
		}
		
		public Point getClosestPoint(){
			return new Point(coordinates.getX(),coordinates.getY(),coordinates.getZ()+getRadius()); 
		}
	//Some setters
		public void setX(double x){
			coordinates.setX(x);
		}
		
		public void setY(double y){
			coordinates.setY(y);
		}
		
		public void setZ(double z){
			coordinates.setZ(z);
		}
		
		public void setMovement(double[] d){
			movement[0]=d[0];
			movement[1]=d[1];
			movement[2]=d[2];
		}
		public void rescale() {
			radius = radius*2.74;
			coordinates.setX((coordinates.getX()-50)*2.74);
			coordinates.setZ(coordinates.getZ()*2.74);
		}
	
	
	
	
	
}

