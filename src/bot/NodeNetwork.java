package bot;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import geometry.Triangle;
import toolbox.Operation;

public class NodeNetwork {
	
	ArrayList<Node> nodesList = new ArrayList<Node>();
	private Node center;
	
	
	//for testing
	public NodeNetwork(ArrayList<Node> nodes){
		nodesList = nodes;
		center = nodes.get(0);
	}
	
	public ArrayList<Node> getNodesList(){
		return nodesList;
	}
	
	public void setNodesList(ArrayList<Node> nodesList){
		this.nodesList = nodesList;
	}
	
	public boolean areConnected(int i, int j){
		return nodesList.get(i).isConnected(nodesList.get(j));
	}
	
	public ArrayList<Node> getAdjacentNodes(Node node){
		ArrayList<Node> adjacentNodes = new ArrayList<Node>();
		for(int i=0; i < nodesList.size(); i++){
			if(nodesList.get(i) != node){
				if(node.isConnected(nodesList.get(i)))
					adjacentNodes.add(nodesList.get(i));
			}
		}
		return adjacentNodes;
	}
	
	public Edge getEdge(Node node1, Node node2){
		return node1.getConnectionEdge(node2);
	}
	public float getDistance(Node node1, Node node2){
		return Vector3f.sub(node1.getCentroid(), node2.getCentroid(), null).length();	
	}
	
	public Node getCenter(){
		return center;
	}
	
	public void addNode(Node node){
		nodesList.add(node);
	}
	public String toString(){
		String s =" ";
		for(Node node: nodesList){
		s += "node: "+node.getDistance()+"  node's edges distance: "+node.getEdgesList();
		s +="\n";
		}
		
		return s;
	}
}
