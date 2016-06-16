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
		center = new Node(new Triangle( new Vector3f(x,y,z), new Vector3f(x,y,z), new Vector3f(x,y,z)), "Center");
			
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
		
		//Contains all the edges
		ArrayList<Vector3f[]> triangleEdgesList = new ArrayList();
		triangleEdgesList.add(null);
		triangleEdgesList.add(null);
		triangleEdgesList.add(null);
		//The different possible edges
		Vector3f[] edge1 = new Vector3f[2];
		Vector3f[] edge2 = new Vector3f[2];
		Vector3f[] edge3 = new Vector3f[2];	
		
		//The current edge
		Vector3f[] currentEdge = new Vector3f[2];
		
		Node node;
		float distance;
		
		//Going through the points forming the center
		for(int i=0; i<points.size(); i++){
			point = points.get(i);
			
			//Going through the list of unordered nodes
			for(int j=0; j<nodesList.size(); j++){
				if(nodesList.get(j)==this.getNode()){
					continue;
				}
				
				Vector3f p1 = nodesList.get(j).getP1();
				Vector3f p2 = nodesList.get(j).getP2();
				Vector3f p3 = nodesList.get(j).getP3();
				
				edge1[0] = p1; edge1[1] = p2;
				edge2[0] = p1;  edge2[1] = p3;
				edge3[0] = p2;  edge3[1] = p3;
				
				triangleEdgesList.set(0,edge1);
				triangleEdgesList.set(1,edge2);
				triangleEdgesList.set(2,edge3);
				
				//Going through the edges of the the nodes's triangle
				for(int k=0; k<triangleEdgesList.size(); k++){
					currentEdge = triangleEdgesList.get(k);
				
		
					a=currentEdge[0];
					b=currentEdge[1];
					Vector3f.sub(b, a, bMinusA);
					Vector3f.sub(point, a, pointMinusA);
					Vector3f.cross(bMinusA, pointMinusA, crossProduct);	
					
					if(Math.abs( crossProduct.length() ) < epsilon){
						
						dotProduct = Vector3f.dot(bMinusA, pointMinusA);
						if(dotProduct >= 0){
							if(dotProduct <= Math.pow(bMinusA.length(), 2) ){
								
								node = nodesList.get(j);
								double squaredDot = Math.pow(Vector3f.dot(node.getNormal(), new Vector3f(0,1,0)), 2);
								if( 0.5<=squaredDot && squaredDot<=1 ){
									
									if( !center.isConnected(node)){
										
										distance = Operation.subtract(center.getPosition(),node.getPosition()).length();
										distance = Math.abs(distance);
										Edge edge = new Edge(distance);
										node.addEdge(edge);
										center.addEdge(edge);
									}
									if( !node.isVisited()){
										network.add(node);
										network.addAll(node.getNeighbourNodes(nodesList));
									}
									break;
								}
							}
						}
					}
				}//Loops through the edges of the other node
			}//Loop through the nodes of the nodesList 
		}//Loops through the points of the center
	return network;
	}
	
	public Node getNode(){
		return center;

	}
}