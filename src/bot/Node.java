package bot;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import geometry.Triangle;
import toolbox.Operation;

public class Node extends Triangle {
	private float distance;
	private ArrayList<Edge> edgesList = new ArrayList<Edge>();
	private boolean isVisited;
	String name;
	
	public Node(){		
		super(new Vector3f(0,0,0),new Vector3f(0,0,0),new Vector3f(0,0,0));
	}
	
	public Node(Triangle triangle,String name){
		super(triangle.getP1(), triangle.getP2(), triangle.getP3());
		this.name = name;
	}
	
	public ArrayList<Edge> getEdgesList(){
		return edgesList;
	}
	
	public void setEdgesList(ArrayList<Edge> edgesList){
		this.edgesList = edgesList;
	}
	
	public void addEdge(Edge edge){
		edgesList.add(edge);
	}
	
	public int getDegree(){
		return edgesList.size();
	}
	
	public boolean isConnected(Node node2){
		for(int i=0; i<edgesList.size(); i++){
			for(int j=0; j<node2.getEdgesList().size(); j++){
				if( edgesList.get(i) == node2.getEdgesList().get(j) )
					return true;
			}
		}
		return false;
	}
	
	public Edge getConnectionEdge(Node node2){
		for(int i=0; i<edgesList.size(); i++){
			for(int j=0; j<node2.getEdgesList().size(); j++){
				if( edgesList.get(i) == node2.getEdgesList().get(j) )
					return edgesList.get(i);
			}
		}
		System.out.println("No connecting edge");
		return null;
	}
	
	public float getDistance(){
		return distance;
	}
	public void setDistance(float distance){
		this.distance=distance;
	}
	public Vector3f getPosition(){
		return getCentroid();
	}
	
	public boolean isVisited(){
		return isVisited;
	}
	
	public void setVisited(boolean value){
		isVisited = value;
	}
	
	public Vector3f getNormal(){
		return super.getNormal();
	}
	
	public String toString(){
		return name;
	}
	
	public ArrayList<Node> getNeighbourNodes(ArrayList<Node> nodesList){

		
		
		ArrayList<Node> connectedNodes = new ArrayList<Node>();
		ArrayList<Vector3f> verticesList = new ArrayList<Vector3f>();
		
		verticesList.add(this.getP1());
		verticesList.add(this.getP2());
		verticesList.add(this.getP3());
		
		//Used check if the point is in between the 2 vertices 
		Vector3f a = new Vector3f();
		Vector3f b = new Vector3f();
		Vector3f point = new Vector3f();
		Vector3f crossProduct= new Vector3f();
		float dotProduct=0;
		Vector3f bMinusA = new Vector3f();
		Vector3f pointMinusA = new Vector3f();
		float epsilon = 0.0001f;
		
		
		this.setVisited(true);
		Node node;
		
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
		
		//Going through the vertices of this node
		for(int i=0; i<verticesList.size(); i++){
			point = verticesList.get(i);
				
			//Going through the nodes of the unordered list
			for(int j=0; j<nodesList.size(); j++){
					
				if(nodesList.get(j) == this)
					continue;
				
				Vector3f p1 = nodesList.get(j).getP1();
				Vector3f p2 = nodesList.get(j).getP2();
				Vector3f p3 = nodesList.get(j).getP3();
				
				edge1[0] = p1; edge1[1] = p2;
				edge2[0] = p1;  edge2[1] = p3;
				edge3[0] = p2;  edge3[1] = p3;
				
				triangleEdgesList.set(0,edge1);
				triangleEdgesList.set(1,edge2);
				triangleEdgesList.set(2,edge3);
				
				//Going through the edges of the node(the one coming from the list)
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
								double squaredDot = Math.pow( Vector3f.dot(node.getNormal(), new Vector3f(0,1,0)) , 2);
								if( 0.5<=squaredDot && squaredDot<=1 ){										
										if( !this.isConnected(node) && this != node ){
											distance = Operation.subtract(this.getPosition(), node.getPosition()).length();
											distance = Math.abs(distance);
											Edge edge = new Edge(distance);
											node.addEdge(edge);
											this.addEdge(edge);
										}
										if( !node.isVisited){
											connectedNodes.add(node);
											connectedNodes.addAll(node.getNeighbourNodes(nodesList));
										}
										break;
								}
							}
						}
					}
				}//Loop through the edges of the other node
			}//Loop through the nodes of the nodesList
		}//loop through the vertices of that node
	return connectedNodes;
	}
}
