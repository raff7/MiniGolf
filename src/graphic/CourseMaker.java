package graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CourseMaker extends JPanel{
	private JPanel  settings, dimention;
	private Course map;
	private Mouse mouse;
	public CourseMaker(){
		super();
		map = new Course();
		map.setPreferredSize(new Dimension(900,500));
		map.buildField(50,0,700,400);
		mouse = new Mouse(map);
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
		addObstacle.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				mouse.setButtonPressed(1);
			}
			
		});
		settings.add(addObstacle);
		JButton deleteObstacles = new JButton("CLEAR OBSTACLES");
		deleteObstacles.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("delete obstacle" + mouse.getLastID());
				if(map.deleteObstacle(mouse.getLastID()))
					mouse.setNextID(mouse.getLastID());
				map.repaint();
				
			}
			
		});
		settings.add(deleteObstacles);
		JButton addHole = new JButton("Add Hole");
		addHole.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				mouse.setButtonPressed(2);
			}
		});
		settings.add(addHole);
		JButton addBall = new JButton("Add Ball");
		addBall.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				mouse.setButtonPressed(3);
			}
		});
		settings.add(addBall);
		JButton addBorder = new JButton("Add border");
		JTextField numberVertecis = new JTextField("Number of verticis");
		addBorder.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent arg0) {
				mouse.setButtonPressed(4);
				mouse.setTimeToPress(Integer.parseInt(numberVertecis.getText()));
			}
		});
		settings.add(numberVertecis);
		settings.add(addBorder);
		JButton start = new JButton("START");
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame gameFrame = new JFrame();
				Course gameCourse = map;
				gameCourse.buildField(0, 0, 1920, 1080);
				gameCourse.rescale();
				gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				gameFrame.setUndecorated(true);
				gameFrame.add(gameCourse);
				gameFrame.setVisible(true);
			}
			
		});
		settings.add(start);
	}
	
}

