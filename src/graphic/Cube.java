package graphic;

import java.util.ArrayList;

import physic.Obstacle;
import physic.Point;

public class Cube implements Obstacle {

	private Point location;
	private ArrayList<Point> vertices;
	private double width ;
	private double height ;
	private double depth ;
	private int ID;
	//private double bouncyness ;
	
	
	public Cube(double x, /*no needed for 2d double y,*/ double z, double width, double height, double depth/*not implemented double bouncyness*/,int ID){
		
		location = new Point(x,0,z);
		this.ID=ID;
		this.width = width ;
		this.height = height ;
		this.depth = depth ;
		// this.bouncyness = bouncyness ;
		vertices = new ArrayList<Point>();
		vertices.add(new Point(x,0/*y*/,z));
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
		//bouncyness = x ;

	}

	@Override
	public double getBouncyness() {
		// TODO Auto-generated method stub
		return 0;//bouncyness ;
	}

	
	public void setLocation(double x, double y, double z) {
		location= new Point(x,y,z);
		vertices.clear();
		vertices.add(new Point(x,0/*y*/,z));
		Point corner2 = new Point(location.getX() + width, location.getY(), location.getZ()); 
		vertices.add(corner2) ;
		Point corner3 = new Point(location.getX()+width, location.getY(), location.getZ()+depth);
		vertices.add(corner3);
		Point corner4 = new Point(location.getX(),location.getY(),location.getZ()+depth);
		vertices.add(corner4);

	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return location ;
	}

	
	public ArrayList<Point> getVertices() {
		return vertices;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
