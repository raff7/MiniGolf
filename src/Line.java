import java.util.ArrayList;

public class Line implements Obstacle{
	double x;
	double y; 
	double x2;
	double y2;
	ArrayList<Point> vertices= new ArrayList<Point>();
	public Line(Point point1, Point point2){
		vertices.add(point1);
		vertices.add(point2);
	}
	@Override
	public void setBounceyness(double x) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public double getBouncyness() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setLocation(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public double[] getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setCorner2(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCorner3(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCorner4(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public double[] getCorner2() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double[] getCorner3() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double[] getCorner4() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double[][] getSide1(double[] corner1, double[] corner2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double[][] getside2(double[] corner2, double[] corner3) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double[][] getSide3(double[] corner3, double[] corner4) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double[][] getSide4(double[] corner4, double[] corner1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Point> getVertices() {
		return vertices;
	}
}
