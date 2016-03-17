package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import physic.Ball;
import physic.Hole;
import physic.Point;

public class Course extends JPanel{

	private ArrayList<physic.Obstacle> obstacles;
	ArrayList<Polygon> list;
	private Polygon field;
	private Ball ball=null;
	private Hole hole=null;

	public Course(){
		obstacles = new ArrayList<physic.Obstacle>();
		
	}
	public ArrayList<physic.Obstacle> getObstacles() {
		return obstacles;
	}
	public void addObstacles(physic.Obstacle obstacle) {
		this.obstacles.add(obstacle);
	}
	public void deleteObstacle(int obstacleID){
		for(int i = 0; i<obstacles.size(); i++){
			if(obstacles.get(i).getID()==obstacleID){
				obstacles.remove(i);
			}
		}
	}
	
	public Ball getBall() {
		return ball;
	}
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	public Hole getHole() {
		return hole;
	}
	public void setHole(Hole hole) {
		this.hole = hole;
	}
	public void buildField(int xField, int zField, int width, int heigth){
		
		field = new Polygon();
		field.addPoint(xField, zField);
		field.addPoint(xField+width, zField);
		field.addPoint(xField+width, zField+heigth);
		field.addPoint(xField, zField+heigth);
		list = new ArrayList<Polygon>();
		for(int i=0;i<obstacles.size();i++){
			ArrayList<Point> points = obstacles.get(i).getVertices();
			list.get(i).addPoint((int) points.get(0).getX(), (int) points.get(0).getZ());
			list.get(i).addPoint((int) points.get(1).getX(), (int) points.get(1).getZ());
			list.get(i).addPoint((int) points.get(2).getX(), (int) points.get(2).getZ());
			list.get(i).addPoint((int) points.get(3).getX(), (int) points.get(3).getZ());
			
		}
		
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2= (Graphics2D) g;
		g.setColor(new Color(0.35f,0.80f, 0.30f));
		g.fillPolygon(field);
		g.setColor(new Color(0,0,0));
		for(int i=0;i<list.size();i++){
			g.fillPolygon(list.get(i));
		}if(hole!= null)g.drawOval((int)(hole.getX()-hole.getRadius()), (int)(hole.getZ()-hole.getRadius()), (int)hole.getRadius()*2, (int)hole.getRadius()*2);
		g.setColor(new Color(1f,1f,1f));
		if(ball != null)g.drawOval((int)(ball.getX()-ball.getRadius()),(int) (ball.getZ()-ball.getRadius()),(int) ball.getRadius()*2, (int) ball.getRadius()*2);
		
		
	}
}
