import java.util.ArrayList;

public class Cube implements Obstacle {

	private Point location;
	private ArrayList<Point> vertices;
	private double width ;
	private double height ;
	private double depth ;
	private double bouncyness ;
	
	
	public Cube(double x, double y, double z, double width, double height, double depth, double bouncyness){
		
		location.setX(x);
		location.setY(y);
		location.setZ(z);
		this.width = width ;
		this.height = height ;
		this.depth = depth ;
		this.bouncyness = bouncyness ;
		vertices.add(new Point(x,y,z));
		Point corner2 = new Point(location.getX() + width, location.getY(), location.getZ()); 
		vertices.add(corner2) ;
		Point corner3 = new Point(location.getX()+width, location.getY(), location.getZ()+depth);
		vertices.add(corner3);
		Point corner4 = new Point(location.getX(),location.getY(),location.getZ()+depth);
		vertices.add(corner4);
		
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
