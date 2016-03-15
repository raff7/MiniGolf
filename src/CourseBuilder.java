import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CourseBuilder {
	public static void main(String[] args){
		new CourseBuilder();
		
	}
	boolean buttonpressed, inside;
	MouseCatcher listener;
	MouseCatcher1 listener1;
	MouseMotion moveListener;
	
	JFrame frame;
	JPanel map; 
	JPanel control;
	JButton obs1;
	JButton obs2;
	JButton obs3;
	JButton start;
	CourseMap grid;
	Obstacle obs;
	public CourseBuilder(){
		listener = new MouseCatcher();
		moveListener = new MouseMotion();
		listener1 = new MouseCatcher1();
		grid = new CourseMap(new Dimension(490,400));
		grid.setPreferredSize(new Dimension(500,400));
		obs1 = new JButton("Starting Point", new BoxIcon(new Obstacle(1, new Dimension(30,30))));
		obs2 = new JButton("Obstacle");
		obs3 = new JButton("Putting Hole");
		start = new JButton("Play");
		obs1.addMouseListener(listener);
		obs2.addMouseListener(listener);
		obs3.addMouseListener(listener);
		obs1.addMouseMotionListener(moveListener);
		obs2.addMouseMotionListener(moveListener);
		obs3.addMouseMotionListener(moveListener);
		map = new JPanel();
		control = new JPanel();
		control.setLayout(new GridLayout(4,1));
		control.add(obs1);
		control.add(obs2);
		control.add(obs3);
		control.add(start);		
		map.setPreferredSize(new Dimension(500,400));
		control.setPreferredSize(new Dimension(100,400));
	
		frame = new JFrame();
		frame.setSize(650, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		grid.addMouseListener(listener1);
		grid.addMouseMotionListener(moveListener);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.add(grid,c);
		c.gridx = 1;
		frame.add(control,c);
		frame.setVisible(true);
	}
	class MouseMotion implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			if(buttonpressed){
				frame.repaint();
				Point point = MouseInfo.getPointerInfo().getLocation();
				point.x -= grid.getLocationOnScreen().x;
				point.y -= grid.getLocationOnScreen().y;
			    obs = new Obstacle(1, new Dimension(0,0));
				obs.origin = new Dimension(point.x - ((int)(obs.size.getWidth()/2)), point.y - ((int)(obs.size.getHeight()/2)));
				Graphics2D g2 = (Graphics2D)grid.getGraphics();
				obs.draw(g2, 1,1);
				
			

			}
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			
		}
		
	}
	class MouseCatcher1 implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			inside = true;
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			inside = false;			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			buttonpressed = false;
			grid.addComponent(obs);
			System.out.println("Released");		
			frame.repaint();
		}
		
	}
	class MouseCatcher implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			buttonpressed = true;
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			buttonpressed = false;
			if(inside)
				new MouseCatcher1().mouseReleased(e);
			
			
		}
		
	}
	class BoxIcon implements Icon{
		GridComponent comp;
		public BoxIcon(GridComponent c1){
			comp = c1;
			
		}
		@Override
		public int getIconHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getIconWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int arg2, int arg3) {
			Graphics2D g2 = (Graphics2D) g;
			((GridComponent) comp).draw(g2,1,1);
		}
		
	}
	
}
