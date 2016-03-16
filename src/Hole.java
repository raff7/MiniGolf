
public class Hole {
	
	double[] coordinates;
	double radius = 0.06 ;
	
	public Hole(double x, double y, double z){
		coordinates = new double[]{x,y,z};
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
}
