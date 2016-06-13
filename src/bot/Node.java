package bot;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import geometry.Triangle;

public class Node extends Triangle {
	
	private float distance;
	private ArrayList<Edge> edgesList = new ArrayList<Edge>();
	
	public Node(){		
		super(new Vector3f(0,0,0),new Vector3f(0,0,0),new Vector3f(0,0,0));
	}
	
	public Node(Triangle triangle){
		super(triangle.getP1(), triangle.getP2(), triangle.getP3());
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
	

}
