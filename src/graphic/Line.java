package graphic;

import java.util.ArrayList;

import physic.Obstacle;
import physic.Point;

public class Line implements Obstacle{
	Point point1; 
	Point point2;
	ArrayList<Point> vertices= new ArrayList<Point>();
	public Line(Point point1, Point point2){
		this.point1=point1;
		this.point2=point2;
		vertices.add(point1);
		vertices.add(point2);
	}
	public void setBounceyness(double x) {
		// TODO Auto-generated method stub
		
	}
	public double getBouncyness() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setLocation(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Point> getVertices() {
		return vertices;
	}
	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return -7;
	}
	@Override
	public void rescale() {
		vertices.clear();
		point1= new Point((point1.getX()-50)*2.74,point1.getY()*2.74,point1.getZ()*2.74);
		point2= new Point((point2.getX()-50)*2.74,point2.getY()*2.74,point2.getZ()*2.74);
		vertices.add(point1);
		vertices.add(point2);
	}
}
