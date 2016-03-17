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
import physic.Obstacle;
import physic.Point;

public class Course extends JPanel{

	private ArrayList<physic.Obstacle> obstacles;
	private ArrayList<Line> borders;
	ArrayList<Polygon> list;
	private Polygon field;
	private Ball ball;
	private Hole hole;

	public Course(){
		obstacles = new ArrayList<physic.Obstacle>();
		list = new ArrayList<Polygon>();
		borders = new ArrayList<Line>();
		ball= null;
		hole= null;
		
		
	}
	
	public ArrayList<physic.Obstacle> getObstacles() {
		return obstacles;
	}
	public void addBorder(Line border){
		borders.add(border);
	}
	public void addObstacles(Obstacle obstacle) {
		if(obstacle!= null)
			this.obstacles.add(obstacle);
		list.clear();
		for(int i=0;i<obstacles.size();i++){
			//create polygon and add it to list.
			ArrayList<Point> points = obstacles.get(i).getVertices();
			list.add(i,new Polygon());
			list.get(i).addPoint((int) points.get(0).getX(), (int) points.get(0).getZ());
			list.get(i).addPoint((int) points.get(1).getX(), (int) points.get(1).getZ());
			list.get(i).addPoint((int) points.get(2).getX(), (int) points.get(2).getZ());
			list.get(i).addPoint((int) points.get(3).getX(), (int) points.get(3).getZ());
			
		}
		
	}
	public boolean deleteObstacle(int obstacleID){
		boolean returner = false;
		for(int i = 0; i<obstacles.size(); i++){
			if(obstacles.get(i).getID()==obstacleID){
				returner=true;
				obstacles.remove(i);
				list.remove(i);
			}
		}
		return returner;
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
		
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2= (Graphics2D) g;
		g2.setColor(new Color(0.35f,0.80f, 0.30f));
		g2.fillPolygon(field);
		g2.setColor(new Color(0,0,0));
		addObstacles(null);
		for(int i=0;i<list.size();i++){
			g2.fillPolygon(list.get(i));
		}for(int i=0;i<borders.size();i++){
			g2.drawLine((int)borders.get(i).getVertices().get(0).getX(),(int) borders.get(i).getVertices().get(0).getZ(),  (int)borders.get(i).getVertices().get(1).getX(),(int) borders.get(i).getVertices().get(1).getZ());
		}
		if(hole!= null)g2.fillOval((int)(hole.getX()-hole.getRadius()), (int)(hole.getZ()-hole.getRadius()), (int)hole.getRadius()*2, (int)hole.getRadius()*2);
		g2.setColor(new Color(1f,1f,1f));
		if(ball != null)g2.fillOval((int)(ball.getX()-ball.getRadius()),(int) (ball.getZ()-ball.getRadius()),(int) ball.getRadius()*2, (int) ball.getRadius()*2);
		
		
	}

	public void rescale() {
		//rescale the ball and the ball coordinates
		ball.rescale();
		//rescale the hole and the hole coordinates
		hole.rescale();
		//rescale obstacles and obstacles coordinates
		for(int i=0;i<obstacles.size();i++){
			obstacles.get(i).rescale();
		}
		//rescale the borders andborders coordinates
		for(int i=0; i<borders.size();i++){
			borders.get(i).rescale();
		}
	}
}
