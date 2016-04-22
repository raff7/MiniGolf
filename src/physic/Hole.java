package physic;


public class Hole {
	private Point coordinates;
	private  double  radius=12;
	public Hole(Point p){
		coordinates = p;
		
	}
	public double getRadius(){
		return radius;
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
	public void rescale(){
		radius = radius*2.74;
		coordinates.setX((coordinates.getX()-50)*2.74);
		coordinates.setZ(coordinates.getZ()*2.74);
	}
}
