
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
	public double[][] getSide1(double[] corner1, double[] corner2) ;
	public double[][] getside2(double[] corner2, double[] corner3) ;
	public double[][] getSide3(double[] corner3, double[] corner4) ;
	public double[][] getSide4(double[] corner4, double[] corner1) ;

}
