package entities;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import bot.Edge;
import bot.Node;
import geometry.Triangle;
import toolbox.Operation;

public class Hole {
	private ArrayList<Vector3f> points;
	private Node center;
	
	public Hole(ArrayList<Vector3f> points){
		this.points = points;
		//Create the center of the Hole
		float x=0;
		float y=0;
		float z=0;
		for(int i=0;i<points.size(); i++){
			x +=points.get(i).x;
			y +=points.get(i).y;
			z +=points.get(i).z;
		}
		x=x/points.size();
		y=y/points.size();
		z=z/points.size();
		center = new Node(new Triangle( new Vector3f(x,y,z), new Vector3f(x,y,z), new Vector3f(x,y,z)));
			
	}
	public ArrayList<Vector3f> getPoints(){
		return points;
	}
	
	public ArrayList<Node> getNodesNetwork(ArrayList<Node> nodesList){
		
		ArrayList<Node> network = new ArrayList<Node>();
		network.add(center);
		center.setVisited(true);
		
		Vector3f a = new Vector3f();
		Vector3f b = new Vector3f();
		Vector3f point = new Vector3f();
		Vector3f crossProduct= new Vector3f();
		float dotProduct=0;
		Vector3f bMinusA = new Vector3f();
		Vector3f pointMinusA = new Vector3f();
		float epsilon = 0.000001f;
		
		//For the transformation of triangles into nodes
		Node node;
		float distance;
		
		for(int i=0; i<nodesList.size(); i++){
			
			Vector3f p1 = nodesList.get(i).getP1();
			Vector3f p2 = nodesList.get(i).getP2();
			Vector3f p3 = nodesList.get(i).getP3();
			
			for(int j=0; j<points.size(); j++){
				point = points.get(j);
				
				//a=p1, b=p2
				a=p1;
				b=p2;
				Vector3f.sub(b, a, bMinusA);
				Vector3f.sub(point, a, pointMinusA);
				Vector3f.cross(bMinusA, pointMinusA, crossProduct);		
				if(Math.abs( crossProduct.length() ) < epsilon){
					dotProduct = Vector3f.dot(bMinusA, pointMinusA);
					if(dotProduct >= 0){
						if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
							//if(//check dot product or normal vector)
							node = nodesList.get(i);
							distance = Operation.subtract(center.getPosition(),node.getPosition()).length();
							distance = Math.abs(distance);
							Edge edge = new Edge(distance);
							node.addEdge(edge);
							center.addEdge(edge);
							network.add(node);
							network.addAll(node.getNeighbourNodes(nodesList));
							continue;
						}
					}
					
				}else{
					//a=p1, b=p3
					a=p1;
					b=p3;
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);	
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);			
					if(Math.abs( crossProduct.length() ) < epsilon){
						dotProduct = Vector3f.dot(bMinusA, pointMinusA);
						if(dotProduct >= 0){
							if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
								//if(//check dot product or normal vector)
								node = nodesList.get(i);
								distance = Operation.subtract(center.getPosition(),node.getPosition()).length();
								distance = Math.abs(distance);
								Edge edge = new Edge(distance);
								node.addEdge(edge);
								center.addEdge(edge);
								network.add(node);
								network.addAll(node.getNeighbourNodes(nodesList));
								continue;
							}
						}
				}else{
					//a=p2, b=p3
					a=p2;
					b=p3;
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);	
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);				
					if(Math.abs( crossProduct.length() ) < epsilon){
						dotProduct = Vector3f.dot(bMinusA, pointMinusA);
						if(dotProduct >= 0){
							if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
								//if(//check dot product or normal vector)
								node = nodesList.get(i);
								distance = Operation.subtract(center.getPosition(),node.getPosition()).length();
								distance = Math.abs(distance);
								Edge edge = new Edge(distance);
								node.addEdge(edge);
								center.addEdge(edge);
								network.add(node);
								network.addAll(node.getNeighbourNodes(nodesList));
								continue;
							}
						}
					}
					}
				}
			}
		}
		
		return network;
		}
}