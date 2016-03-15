
public class Cube implements Obstacle {

	private double[] location = new double[3] ;
	private double[] corner2 = new double[3] ;
	private double[] corner3 = new double[3] ;
	private double[] corner4 = new double[3] ; 
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

	@Override
	public double[] getCorner2() {
		// TODO Auto-generated method stub
		return corner2 ;
	}

	@Override
	public double[] getCorner3() {
		// TODO Auto-generated method stub
		return corner3 ;
	}

	@Override
	public double[] getCorner4() {
		// TODO Auto-generated method stub
		return corner4 ;
	}

	@Override
	public void setCorner2(double x, double y, double z) {
		// TODO Auto-generated method stub
		corner2[0] = x ;
		corner2[1] = y ;
		corner2[2] = z ;
		
	}

	@Override
	public void setCorner3(double x, double y, double z) {
		// TODO Auto-generated method stub
		corner3[0] = x ;
		corner3[1] = y ;
		corner3[2] = z ;
		
	}

	@Override
	public void setCorner4(double x, double y, double z) {
		// TODO Auto-generated method stub
		corner4[0] = x ;
		corner4[1] = y ;
		corner4[2] = z ;
		
	}

	@Override
	public double[][] getSide1(double[] corner1, double[] corner2) {
		double[][] temp = new double[2][3] ;
		temp[0][0] = corner1[0] ;
		temp[0][1] = corner1[1] ;
		temp[0][2] = corner1[2] ;
		temp[1][0] = corner2[0] ;
		temp[1][1] = corner2[1] ;
		temp[1][2] = corner2[2] ;
		return temp ;
	}

	@Override
	public double[][] getside2(double[] corner2, double[] corner3) {
		// TODO Auto-generated method stub
		double[][] temp = new double[2][3] ;
		temp[0][0] = corner2[0] ;
		temp[0][1] = corner2[1] ;
		temp[0][2] = corner2[2] ;
		temp[1][0] = corner3[0] ;
		temp[1][1] = corner3[1] ;
		temp[1][2] = corner3[2] ;
		return temp ;
	}

	@Override
	public double[][] getSide3(double[] corner3, double[] corner4) {
		// TODO Auto-generated method stub
		double[][] temp = new double[2][3] ;
		temp[0][0] = corner3[0] ;
		temp[0][1] = corner3[1] ;
		temp[0][2] = corner3[2] ;
		temp[1][0] = corner4[0] ;
		temp[1][1] = corner4[1] ;
		temp[1][2] = corner4[2] ;
		return temp ;
	}

	@Override
	public double[][] getSide4(double[] corner4, double[] corner1) {
		// TODO Auto-generated method stub
		double[][] temp = new double[2][3] ; 
		temp[0][0] = corner4[0] ;
		temp[0][1] = corner4[1] ;
		temp[0][2] = corner4[2] ;
		temp[1][0] = corner1[0] ;
		temp[1][1] = corner1[1] ;
		temp[1][2] = corner1[2] ;
		return temp ;
	}

}
