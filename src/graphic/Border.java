/*package graphic;

import java.util.ArrayList;

import physic.Obstacle;
import physic.Point;

public class Border implements Obstacle{
	private Point firstPointLocation;
	private ArrayList<Point> vertices;
	private double depth ;
	private int ID;
	public Border(ArrayList<Point> vertices,int ID){
		this.vertices = vertices;
		firstPointLocation = vertices.get(0);
		this.ID=ID;
	}
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
		firstPointLocation = new Point(x,y,z);		
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return firstPointLocation;
	}

	@Override
	public ArrayList<Point> getVertices() {
		// TODO Auto-generated method stub
		return vertices;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	@Override
	public void rescale() {
		// TODO Auto-generated method stub
		
	}

}*/
