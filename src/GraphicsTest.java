import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GraphicsTest {
	public static void main(String[] args){
		Ball ball = new Ball(new double[]{150,0,100},new double[]{2,0,2});
		ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		Line line = new Line(new Point(10,0,400),new Point(500,0,400));
		Line line2 = new Line(new Point(10,0,50),new Point(500,0,50));
		obstacles.add(line);
		obstacles.add(line2);
		//PhysicsEngine pe = new PhysicsEngine(ball,obstacles);
		JFrame miniGolf = new JFrame();
		miniGolf.setSize(800,800);
		miniGolf.setTitle("MiniGolf");
		GameScreen gs = new GameScreen(ball,obstacles);
		miniGolf.add(gs);
		
		
		
		/*class upDate implements ActionListener{
			public void actionPerformed(ActionEvent event){
				pe.updateGameState();
				miniGolf.repaint();
			}
		}
		Timer timer = new Timer(17,new upDate());
		timer.start();*/
		miniGolf.setVisible(true);
	}
}
