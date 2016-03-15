
public interface Obstacle {
	
	public void setBounceyness(double x) ;
	public double getBouncyness() ;
	public void setLocation(double x, double y, double z) ;
	public double[] getLocation() ;
	public void setCorner2(double x, double y, double z) ;
	public void setCorner3(double x, double y, double z) ;
	public void setCorner4(double x, double y, double z) ;
	public double[] getCorner2() ;
	public double[] getCorner3() ;
	public double[] getCorner4() ;

}
