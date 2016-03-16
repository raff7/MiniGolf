
public class Hole {
	double[] coordinates;
	double radius;
	public Hole(double x, double y, double z, double radius){
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
