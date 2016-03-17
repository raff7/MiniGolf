package graphic;

import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import physic.Ball;
import physic.Hole;
import physic.Obstacle;
import physic.Point;
 
public class Mouse implements MouseListener {
 
    private static final double WIDTH = 50;
	private static final double HEIGHT = 50;
	private static final double DEPTH = 50;
	private int x ;
    private int z ;
    private int firstX,firstZ;
    private ArrayList<Line> borders;
    private int buttonPressed;
    private int obstacleID;
	private Course map;
	private int timeToPress;
    private int timePressed;
	private int secondX=-1;
	private int secondZ=-1;
	private int evenX;
	private int evenZ;
    public void setButtonPressed(int buttonPressed){
    	this.buttonPressed = buttonPressed;
    }
    public void setTimeToPress(int timeToPress){
    	this.timeToPress=timeToPress;
    	timePressed=0;
    }
    public int getLastID(){
    	return obstacleID-1;
    }
    public Mouse(Course map){
    	obstacleID=0;
    	this.map = map;
    }
  
	@Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX() ;
        z = e.getY() ;
       
        if(buttonPressed==1){
        	 System.out.println(x +" "+ z+" pressed 1");
        	Obstacle obstacle = new Cube(x, z, WIDTH, HEIGHT, DEPTH, obstacleID);
        	System.out.println(obstacleID);
        	obstacleID++;
        	map.addObstacles(obstacle);
        	buttonPressed=0;
        }else if(buttonPressed==2){
        	 System.out.println(x +" "+ z+" pressed 2");
        	 Hole hole = new Hole(new Point(x,0,z));
        	 map.setHole(hole);
        	 buttonPressed=0;
        }else if(buttonPressed==3){
        	 System.out.println(x +" "+ z+" pressed 3");
        	 Ball ball = new Ball(new Point(x,0,z),new double[]{0,0,0});
        	 map.setBall(ball);
        	 buttonPressed=0;
        } else if(buttonPressed==4){
        	 System.out.println(x +" "+ z+" pressed 4");
        	 if(timePressed==0){
        		  borders = new ArrayList<Line>();
        		  firstX=x;
        		  firstZ=z;
        	 }
        	 if(timePressed%2 ==0){
        		 evenX=x;
        		 evenZ=z;
        		 if(secondX!=-1 && secondZ!=-1){
        			 borders.add(new Line(new Point(secondX,0,secondZ),new Point(evenX,0,evenZ)));
        		 }
        	 }else if(timePressed%2==1){
        		secondX=x;
        		secondZ=z;
        		 borders.add(new Line(new Point(evenX,0,evenZ), new Point(secondX,0,secondZ)));
        	  }
        	 if(timePressed>=timeToPress-1){
        	 	buttonPressed=0;
        	 	timePressed=0;
        	 	timeToPress=0;
        	 	borders.add(new Line(new Point(firstX,0,firstZ),new Point(secondX,0,secondZ)));
        	 	for(int i=0;i<borders.size();i++)
        	 		map.addBorder(borders.get(i));
        	 }else
        		 timePressed++;
        }
        
        map.repaint();
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        return ;
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        return ;
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        return ;
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        return ;
    }
   
    public int getXCoordinate(){
        return x ;
    }
   
    public int getZCoordinate(){
        return z ;
    }
	public void setNextID(int i) {
		this.obstacleID = i;
	}
}
 
