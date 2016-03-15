
public class Ball {
	
	private double[] coordinates = new double[3] ;
	private double[] movement = new double[3] ;
	private double radius = 0.02 ;
	
	private Ball(double x, double y, double z){
		
		coordinates[0] = x ;
		coordinates[1] = y ;
		coordinates[2] = z ;
		
	}
	
	private double[] getCoordinates(){
		
		return coordinates ;
		
	}
	
	private void setCoordinates(double x, double y, double z){
		
		coordinates[0] = x ;
		coordinates[1] = y ;
		coordinates[2] = z ;
	
	}
	
	private double[] getMovement(){
		
		return movement ;
		
	}
	
	private void setMovement(double x, double y, double z){

		movement[0] = x ;
		movement[1] = y ;
		movement[2] = z ;
		
	}
	
	private double getX(){
		
		return coordinates[0] ;
	}
	
	private double getY(){
		
		return coordinates[1] ;
		
	}
	
	private double getZ(){
		
		return coordinates[2] ;
		
	}

}
