import java.util.ArrayList;

public class Cube implements Obstacle {

	private double[] location = new double[3] ;
	private ArrayList<double[]> vertices;
	private double width ;
	private double height ;
	private double depth ;
	private double bouncyness ;
	
	
	public Cube(double x, double y, double z, double width, double height, double depth, double bouncyness){
		
		location[0] = x ;
		location[1] = y ;
		location[2] = z ;
		this.width = width ;
		this.height = height ;
		this.depth = depth ;
		this.bouncyness = bouncyness ;
		corner2[0] = location[0] + width ;
		corner2[1] = location[1] ;
		corner2[2] = location[2] ;
		corner3[0] = corner2[0] ;
		corner3[1] = corner2[1] ;
		corner3[2] = corner2[2] + depth ;
		corner4[0] = corner3[0] - width ;
		corner4[1] = corner3[1] ;
		corner4[2] = corner3[2] ;
		
	}
	
	@Override
	public void setBounceyness(double x) {
		// TODO Auto-generated method stub
		bouncyness = x ;

	}

	@Override
	public double getBouncyness() {
		// TODO Auto-generated method stub
		return bouncyness ;
	}

	@Override
	public void setLocation(double x, double y, double z) {
		// TODO Auto-generated method stub
		location[0] = x ;
		location[1] = y ;
		location[2] = z ;
		 

	}

	@Override
	public double[] getLocation() {
		// TODO Auto-generated method stub
		return location ;
	}

	
	public ArrayList<double[]> getVertices() {
		return vertices;
	}

}
