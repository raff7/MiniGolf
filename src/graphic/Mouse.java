package graphic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import physic.Hole;
import physic.Obstacle;
 
public class Mouse implements MouseListener {
 
    private static final double WIDTH = 100;
	private static final double HEIGHT = 100;
	private static final double DEPTH = 100;
	private static final double RADIUS = 30;
	private int x ;
    private int z ;
    private int buttonPressed;
    private int obstacleID;
	private Course map;
	private int timeToPress;
    private int timePressed;
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
        	 Hole hole = new Hole(x,0,z,RADIUS);
        	 map.setHole(hole);
        	 buttonPressed=0;
        }else if(buttonPressed==3){
        	 System.out.println(x +" "+ z+" pressed 3");
        	 buttonPressed=0;
        } else if(buttonPressed==4){
        	 System.out.println(x +" "+ z+" pressed 4");
        	 
        	 if(timePressed>=timeToPress-1){
        	 	buttonPressed=0;
        	 	timePressed=0;
        	 	timeToPress=0;
        	 }
        	 else
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
		this.obstacleID = 0;
	}
}
 
