package graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CourseMaker extends JPanel{
	private JPanel  settings, dimention;
	private Course map;
	public CourseMaker(){
		super();
		map = new Course();
		map.setPreferredSize(new Dimension(900,500));
		map.buildField(50,0,700,400);
		Mouse mouse = new Mouse();
		map.addMouseListener(mouse);
		this.setLayout(new BorderLayout());
		this.add(map,BorderLayout.CENTER);
		settings = new JPanel();
		settings.setPreferredSize(new Dimension(900,250));
		makeSettings();
		this.add(settings, BorderLayout.NORTH);
		dimention = new JPanel();
		dimention.setPreferredSize(new Dimension(10,700));
		this.add(dimention, BorderLayout.EAST);
		makeDimention();
	}
	private void makeDimention() {
		// TODO add a bar to set dimention with map.buildField(50,0,newWidth,newHeigth);
		
	}
	private void makeSettings() {
		//buttonListener listener = new ButtonListener();

		JButton addObstacle = new JButton("Add Obstacle");
		addObstacle.addActionListener(null);//button listener that trigger an actionlistener that cet thecoordinates of the point where you click and make a new obstacle in there. new buttonListener());
		settings.add(addObstacle);
		JButton addHole = new JButton("Add Hole");
		//addHole.addActionListener(similar to the button before but with a hole);
		settings.add(addHole);
		JButton addBall = new JButton("Add Ball");
		//addBall.addActionListener(same as above, with the ball);
		settings.add(addBall);
		JButton addBorder = new JButton("Add border");
		JTextField numberVertecis = new JTextField("Number of verticis");
		//addBorder.addActionListener(an action listener that see the number of verdices in the fieldtext and make you click in every vertices to create the borders);
		settings.add(numberVertecis);
		settings.add(addBorder);
		JButton start = new JButton("START");
		//start.addActionListener(actionlistener that make a new curse with all the actual impostations and put it in to the mail fraim);
		settings.add(start);
	}
	
}

