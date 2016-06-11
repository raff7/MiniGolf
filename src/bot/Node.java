package bot;

import java.util.ArrayList;
import geometry.Triangle;

public class Node {
	
	private int distance;
	private boolean hasBall = false;
	private boolean hasHole = false;
	private boolean isFree = false;
	private Triangle triangle;
	private ArrayList<Edge> edgesList = new ArrayList<Edge>();
	
	
	public Node(Triangle triangle){
		this.triangle = triangle;
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
	
	public Triangle getTriangle(){
		return triangle;
	}
	
	public int getDegree(){
		return edgesList.size();
	}
	
	public boolean isConnected(Node node2){
		for(int i=0; i<edgesList.size(); i++){
			for(int j=0; j<node2.getEdgesList().size(); j++){
				if( edgesList.get(i) == node2.getEdgesList().get(i) )
					return true;
			}
		}
		return false;
	}
	
	

	private void setDistance(int dist){
		distance = dist;
	}
	
	private int getDistance(){
		return distance;
	}
	
	private void setOccupied(){
		isFree = false;
	}
	
	private void setFree(){
		isFree = true;
	}
	
	private boolean isFree(){
		
		return isFree;
	}
	
	private void addHole(){	
		hasHole = true;
	}
	
	private void noHole(){	
		hasHole = false;
	}
	
	private boolean hasHole(){	
		return hasHole;
	}
	
	private void addBall(){	
		hasBall = true;
	}
	
	private void removeBall(){
		hasBall = false;
	}
	
	//backtracking ability.. incase you run in to dead end when adding fitness++ while expanding AWAY from hole.. (how the nodes will be constructed)
	

}
