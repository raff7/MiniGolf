import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScreen extends JPanel{
	public double x;
	public double z;
	public double radius=20;
	Timer timer;
	ArrayList<Obstacle> obstacles;
	Ball ball;
	PhysicsEngine pe;
	
	public GameScreen(Ball ball,ArrayList<Obstacle> obstacles){
		this.ball = ball;
		this.obstacles = obstacles;
		//this.pe = pe;
		pe = new PhysicsEngine(ball,obstacles);
		class upDateListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				pe.updateGameState();
				x=ball.getX();
				z=ball.getZ();
				repaint();
				System.out.println("graphics x and z "+x +" "+z);
			}
		}
		timer = new Timer(17,new upDateListener());
		timer.start();
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine((int)obstacles.get(0).getVertices().get(0).getX(),(int) obstacles.get(0).getVertices().get(0).getZ(),(int) obstacles.get(0).getVertices().get(1).getX(), (int)obstacles.get(0).getVertices().get(1).getZ());
		g2.drawLine((int)obstacles.get(1).getVertices().get(0).getX(),(int) obstacles.get(0).getVertices().get(0).getZ(),(int) obstacles.get(0).getVertices().get(1).getX(), (int)obstacles.get(0).getVertices().get(1).getZ());
		g2.fillOval((int) (x-radius),(int) (z-radius),(int) radius, (int)radius);
}
}
