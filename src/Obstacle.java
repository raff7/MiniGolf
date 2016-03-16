import java.util.ArrayList;

public interface Obstacle {
	
	public void setBounceyness(double x) ;
	public double getBouncyness() ;
	public void setLocation(double x, double y, double z) ;
	public double[] getLocation() ;
	public ArrayList<double[]> getVertices();

}
